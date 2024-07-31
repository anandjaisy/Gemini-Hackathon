package fete.bird.feature.assessmentScore;

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
public class AssessmentScoreRepository
        implements IRepository<AssessmentScoreResponse, AssessmentScoreRequest, AssessmentScoreCriteria> {
    private final Map<UUID, AssessmentScore> assessmentScoreData;
    private final AssessmentScoreResponseMapper assessmentScoreResponseMapper;
    private final RequestAssessmentScoreMapper requestAssessmentScoreMapper;
    private final AssessmentScoreSpecification specification;

    public AssessmentScoreRepository(RootProvider<Root> rootProvider,
                                     AssessmentScoreResponseMapper assessmentScoreResponseMapper,
                                     RequestAssessmentScoreMapper requestAssessmentScoreMapper,
                                     AssessmentScoreSpecification specification) {
        this.assessmentScoreResponseMapper = assessmentScoreResponseMapper;
        this.requestAssessmentScoreMapper = requestAssessmentScoreMapper;
        this.specification = specification;
        this.assessmentScoreData = rootProvider.root().getAssessmentScore();
    }

    @Override
    public Optional<AssessmentScoreResponse> get(UUID id) {
        return Optional.ofNullable(assessmentScoreData.get(id)).map(assessmentScoreResponseMapper);
    }

    @Override
    public List<AssessmentScoreResponse> find(Optional<AssessmentScoreCriteria> criteria) {
        specification.setCriteria(criteria);
        return assessmentScoreData.values().stream()
                .filter(specification)
                .map(assessmentScoreResponseMapper).toList();
    }

    @Override
    public Optional<AssessmentScoreResponse> create(AssessmentScoreRequest request) throws DuplicateException {
        if (assessmentScoreData.values().stream().anyMatch(x -> (x.studentId().equals(request.studentId()) && x.studentAssessmentId().equals(request.studentAssessmentId()))))
            throw new DuplicateException(String.format("%s %s",request.studentId(), "already exists !"));
        var assessment = requestAssessmentScoreMapper.apply(Optional.empty(),request);
        this.save(assessment);
        return Optional.of(assessmentScoreResponseMapper.apply(assessment));
    }

    @Override
    public Optional<AssessmentScoreResponse> update(UUID id, AssessmentScoreRequest request) {
        AssessmentScore studentAssessment = assessmentScoreData.get(id);
        if (studentAssessment == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        var updateAssessment =  requestAssessmentScoreMapper.apply(Optional.of(studentAssessment.id()),request);
        save(updateAssessment);
        return Optional.of(assessmentScoreResponseMapper.apply(updateAssessment));
    }

    @Override
    public void delete(UUID id) {
        deleteCourseById(id);
    }

    @StoreParams("Course")
    protected void save(@NonNull AssessmentScore assessmentScore) {
        assessmentScoreData.put(assessmentScore.id(), assessmentScore);
    }
    @StoreParams("Course")
    protected void deleteCourseById(@NonNull UUID id) {
        if (assessmentScoreData.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        assessmentScoreData.remove(id);
    }
}
