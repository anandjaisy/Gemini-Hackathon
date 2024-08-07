package fete.bird.feature.assessment;

import fete.bird.shared.IController;
import fete.bird.shared.IRepository;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/assessment")
@ApiResponse(responseCode = "400", description = "Bad request, invalid data")
@ApiResponse(responseCode = "404", description = "Course not found")
@Tag(name = "Assessment")
@ExecuteOn(TaskExecutors.BLOCKING)
public record AssessmentController(IRepository<AssessmentResponse, AssessmentRequest, AssessmentCriteria> iAssessmentRepository) implements IController<AssessmentResponse, AssessmentRequest, AssessmentCriteria> {
    @Override
    public Optional<AssessmentResponse> get(UUID id) {
        return iAssessmentRepository.get(id);
    }

    @Override
    public List<AssessmentResponse> find(Optional<AssessmentCriteria> criteria) {
        return iAssessmentRepository.find(criteria);
    }

    @Override
    public Optional<AssessmentResponse> create(AssessmentRequest request) {
        return iAssessmentRepository.create(request);
    }

    @Override
    public Optional<AssessmentResponse> update(@PathVariable UUID id, @Body AssessmentRequest request) {
        return iAssessmentRepository.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        iAssessmentRepository.delete(id);
    }
}
