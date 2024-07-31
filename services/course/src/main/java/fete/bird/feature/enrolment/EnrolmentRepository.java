package fete.bird.feature.enrolment;

import fete.bird.persistence.Root;
import fete.bird.shared.Constants;
import fete.bird.shared.DuplicateException;
import fete.bird.shared.IRepository;
import fete.bird.shared.NotFoundException;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.eclipsestore.RootProvider;
import io.micronaut.eclipsestore.annotations.StoreParams;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class EnrolmentRepository implements IRepository<EnrolmentResponse, EnrolmentRequest, EnrolmentCriteria> {
    private final Map<UUID, Enrolment> enrolmentData;
    private final EnrolmentSpecification specification;
    private final EnrolmentResponseMapper enrolmentToResponseMapper;
    private final RequestEnrolmentMapper requestToProfessorMapper;

    public EnrolmentRepository(RootProvider<Root> rootProvider,
                               EnrolmentSpecification specification,
                               EnrolmentResponseMapper enrolmentToResponseMapper,
                               RequestEnrolmentMapper requestToEnrolmentMapper) {
        this.enrolmentData = rootProvider.root().getEnrolment();
        this.specification = specification;
        this.enrolmentToResponseMapper = enrolmentToResponseMapper;
        this.requestToProfessorMapper = requestToEnrolmentMapper;
    }

    @Override
    public Optional<EnrolmentResponse> get(UUID id) {
        return Optional.ofNullable(enrolmentData.get(id)).map(enrolmentToResponseMapper);
    }

    @Override
    public List<EnrolmentResponse> find(Optional<EnrolmentCriteria> criteria) {
        specification.setCriteria(criteria);
        return enrolmentData.values().stream()
                .filter(specification)
                .map(enrolmentToResponseMapper).toList();
    }

    @Override
    public Optional<EnrolmentResponse> create(EnrolmentRequest request){
        if (enrolmentData.values().stream()
                .anyMatch(x -> (x.studentId().equals(request.studentId()) || (x.professorId().equals(request.professorId())))
                        && x.courseId().equals(request.courseId())))
            throw new DuplicateException(request.professorId().isPresent() ? request.professorId().get().toString() : request.studentId().get().toString());
        var enrolment = requestToProfessorMapper.apply(request);
        this.save(enrolment);
        return Optional.of(enrolmentToResponseMapper.apply(enrolment));
    }

    @Override
    public Optional<EnrolmentResponse> update(UUID id, EnrolmentRequest request) {
        Enrolment enrolment = enrolmentData.get(id);
        if (enrolment == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        var updateCourse = new Enrolment(enrolment.id(), request.courseId(), request.professorId(), request.studentId());
        save(updateCourse);
        return Optional.of(this.enrolmentToResponseMapper.apply(updateCourse));
    }

    @Override
    public void delete(UUID id) {
        deleteCourseById(id);
    }

    @StoreParams("Course")
    protected void deleteCourseById(@NonNull UUID id) {
        if (enrolmentData.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        enrolmentData.remove(id);
    }

    @StoreParams("Course")
    protected void save(@NonNull Enrolment enrolment) {
        enrolmentData.put(enrolment.id(), enrolment);
    }
}
