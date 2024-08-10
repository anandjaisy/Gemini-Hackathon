package fete.bird.feature.assessmentEvaluation;

import fete.bird.common.Constants;
import fete.bird.common.DuplicateException;
import fete.bird.common.IRepository;
import fete.bird.common.NotFoundException;
import fete.bird.persistence.Root;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.eclipsestore.RootProvider;
import io.micronaut.eclipsestore.annotations.StoreParams;
import jakarta.inject.Singleton;

import java.util.*;

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
        var assessmentScoreResponses = specification.setCriteria(criteria).apply(new ArrayList<>(assessmentScoreData.values()));
        return assessmentScoreResponses.stream()
                .map(assessmentScoreResponseMapper).toList();
    }

    @Override
    public Optional<AssessmentScoreResponse> create(AssessmentScoreRequest request) throws DuplicateException {
        if (assessmentScoreData.values().stream().anyMatch(x -> (x.studentId().equals(request.studentId()) && x.questionId().equals(request.questionId()))))
            throw new DuplicateException(String.format("%s %s", request.studentId(), "already exists !"));
        var assessment = requestAssessmentScoreMapper.apply(Optional.empty(), request);
        this.save(this.assessmentScoreData, assessment);
        return Optional.of(assessmentScoreResponseMapper.apply(assessment));
    }

    @Override
    public Optional<AssessmentScoreResponse> update(UUID id, AssessmentScoreRequest request) {
        AssessmentScore studentAssessment = assessmentScoreData.get(id);
        if (studentAssessment == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        var updateAssessment = requestAssessmentScoreMapper.apply(Optional.of(studentAssessment.id()), request);
        save(this.assessmentScoreData, updateAssessment);
        return Optional.of(assessmentScoreResponseMapper.apply(updateAssessment));
    }

    @Override
    public void delete(UUID id) {
        deleteById(this.assessmentScoreData, id);
    }

    @StoreParams("assessmentScore")
    protected void save(Map<UUID, AssessmentScore> assessmentScore, @NonNull AssessmentScore request) {
        assessmentScore.put(request.id(), request);
    }

    @StoreParams("assessmentScore")
    protected void deleteById(Map<UUID, AssessmentScore> assessmentScore, @NonNull UUID id) {
        if (assessmentScore.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        assessmentScore.remove(id);
    }
}
