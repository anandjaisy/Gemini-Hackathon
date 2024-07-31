package fete.bird.feature.assessment;

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
public class AssessmentRepository implements IRepository<AssessmentResponse,AssessmentRequest,AssessmentCriteria> {
    private final Map<UUID, Assessment> assessmentData;
    private final AssessmentResponseMapper assessmentResponseMapper;
    private final RequestAssessmentMapper requestAssessmentMapper;
    private final AssessmentSpecification specification;

    public AssessmentRepository(RootProvider<Root> rootProvider,
                                AssessmentResponseMapper assessmentResponseMapper,
                                RequestAssessmentMapper requestAssessmentMapper,
                                AssessmentSpecification specification) {
        this.assessmentData = rootProvider.root().getAssessment();
        this.assessmentResponseMapper = assessmentResponseMapper;
        this.requestAssessmentMapper = requestAssessmentMapper;
        this.specification = specification;
    }
    @Override
    public Optional<AssessmentResponse> get(UUID id) {
        return Optional.ofNullable(assessmentData.get(id)).map(assessmentResponseMapper);
    }

    @Override
    public List<AssessmentResponse> find(Optional<AssessmentCriteria> criteria) {
        specification.setCriteria(criteria);
        return assessmentData.values().stream()
                .filter(specification)
                .map(assessmentResponseMapper).toList();
    }

    @Override
    public Optional<AssessmentResponse> create(AssessmentRequest request) throws DuplicateException {
        if (assessmentData.values().stream().anyMatch(x -> x.name().equals(request.name())))
            throw new DuplicateException(String.format("%s %s", request.name(), Constants.ITEM_EXIST));
        var assessment = requestAssessmentMapper.apply(Optional.empty(),request);
        this.save(assessment);
        return Optional.of(assessmentResponseMapper.apply(assessment));
    }

    @Override
    public Optional<AssessmentResponse> update(UUID id, AssessmentRequest request) {
        Assessment assessment = assessmentData.get(id);
        if (assessment == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        var updateAssessment =  requestAssessmentMapper.apply(Optional.of(assessment.id()),request);
        save(updateAssessment);
        return Optional.of(assessmentResponseMapper.apply(updateAssessment));
    }

    @Override
    public void delete(UUID id) {
        deleteCourseById(id);
    }
    @StoreParams("Course")
    protected void save(@NonNull Assessment assessment) {
        assessmentData.put(assessment.id(), assessment);
    }

    @StoreParams("Course")
    protected void deleteCourseById(@NonNull UUID id) {
        if (assessmentData.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        assessmentData.remove(id);
    }
}
