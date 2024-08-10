package fete.bird.feature.assessmentEvaluation;

import fete.bird.common.IController;
import fete.bird.common.IRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/scoring/assessment")
@ApiResponse(responseCode = "400", description = "Bad request, invalid data")
@ApiResponse(responseCode = "404", description = "Course not found")
@Tag(name = "Assessment")
@ExecuteOn(TaskExecutors.BLOCKING)
public record AssessmentScoreController(
        IRepository<AssessmentScoreResponse, AssessmentScoreRequest, AssessmentScoreCriteria> iRepository)
        implements IController<AssessmentScoreResponse, AssessmentScoreRequest, AssessmentScoreCriteria> {

    @Override
    public Optional<AssessmentScoreResponse> get(UUID id) {
        return iRepository.get(id);
    }

    @Override
    @Get("/{?criteria*}")
    public List<AssessmentScoreResponse> find(Optional<AssessmentScoreCriteria> criteria) {
        return iRepository.find(criteria);
    }

    @Override
    public Optional<AssessmentScoreResponse> create(AssessmentScoreRequest request) {
        return iRepository.create(request);
    }

    @Override
    public Optional<AssessmentScoreResponse> update(UUID id, AssessmentScoreRequest request) {
        return iRepository.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        iRepository.delete(id);
    }
}
