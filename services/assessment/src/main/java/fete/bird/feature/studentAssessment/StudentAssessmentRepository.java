package fete.bird.feature.studentAssessment;

import fete.bird.persistence.Root;
import fete.bird.shared.Constants;
import fete.bird.shared.DuplicateException;
import fete.bird.shared.IRepository;
import fete.bird.shared.NotFoundException;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.eclipsestore.RootProvider;
import io.micronaut.eclipsestore.annotations.StoreParams;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class StudentAssessmentRepository
        implements IRepository<StudentAssessmentResponse, StudentAssessmentRequest, StudentAssessmentCriteria> {
    private final Map<UUID, StudentAssessment> studentAssessmentData;
    private final StudentAssessmentResponseMapper studentAssessmentResponseMapper;
    private final RequestStudentAssessmentMapper requestStudentAssessmentMapper;
    private final StudentAssessmentSpecification specification;
    public StudentAssessmentRepository(RootProvider<Root> rootProvider,
                                       StudentAssessmentResponseMapper studentAssessmentResponseMapper,
                                       RequestStudentAssessmentMapper requestStudentAssessmentMapper,
                                       StudentAssessmentSpecification specification) {
        this.studentAssessmentResponseMapper = studentAssessmentResponseMapper;
        this.studentAssessmentData = rootProvider.root().getStudentAssessment();
        this.requestStudentAssessmentMapper = requestStudentAssessmentMapper;
        this.specification = specification;
    }
    @Override
    public Optional<StudentAssessmentResponse> get(UUID id) {
        return Optional.ofNullable(studentAssessmentData.get(id)).map(studentAssessmentResponseMapper);
    }

    @Override
    public List<StudentAssessmentResponse> find(Optional<StudentAssessmentCriteria> criteria) {
        var  studentAssessmentResponses = specification.setCriteria(criteria).apply(new ArrayList<>(studentAssessmentData.values()));
        return studentAssessmentResponses.stream()
                .map(studentAssessmentResponseMapper).toList();
    }

    @Override
    public Optional<StudentAssessmentResponse> create(StudentAssessmentRequest request) throws DuplicateException {
        if (studentAssessmentData.values().stream().anyMatch(x -> (x.studentId().equals(request.studentId()) && x.questionId().equals(request.questionId()))))
            throw new DuplicateException(String.format("%s %s",request.studentId(), "already exists !"));
        var assessment = requestStudentAssessmentMapper.apply(Optional.empty(),request);
        this.save(this.studentAssessmentData,assessment);
        return Optional.of(studentAssessmentResponseMapper.apply(assessment));
    }

    @Override
    public Optional<StudentAssessmentResponse> update(UUID id, StudentAssessmentRequest request) {
        StudentAssessment studentAssessment = studentAssessmentData.get(id);
        if (studentAssessment == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        var updateAssessment =  requestStudentAssessmentMapper.apply(Optional.of(studentAssessment.id()),request);
        save(this.studentAssessmentData,updateAssessment);
        return Optional.of(studentAssessmentResponseMapper.apply(updateAssessment));
    }

    @Override
    public void delete(UUID id) {
        deleteById(this.studentAssessmentData,id);
    }

    @StoreParams("studentAssessment")
    protected void save(Map<UUID, StudentAssessment> studentAssessment, @NonNull StudentAssessment request) {
        studentAssessment.put(request.id(), request);
    }

    @StoreParams("studentAssessment")
    protected void deleteById(Map<UUID, StudentAssessment> studentAssessment, @NonNull UUID id) {
        if (studentAssessment.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        studentAssessment.remove(id);
    }
}
