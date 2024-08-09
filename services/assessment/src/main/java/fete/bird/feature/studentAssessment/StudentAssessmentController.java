package fete.bird.feature.studentAssessment;

import fete.bird.feature.question.QuestionCriteria;
import fete.bird.feature.question.QuestionRequest;
import fete.bird.feature.question.QuestionResponse;
import fete.bird.shared.IController;
import fete.bird.shared.IRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/assessment/student")
@ApiResponse(responseCode = "400", description = "Bad request, invalid data")
@ApiResponse(responseCode = "404", description = "Course not found")
@Tag(name = "Assessment")
@ExecuteOn(TaskExecutors.BLOCKING)
public record StudentAssessmentController(IRepository<StudentAssessmentResponse, StudentAssessmentRequest, StudentAssessmentCriteria> iRepository,
                                          IRepository<QuestionResponse, QuestionRequest, QuestionCriteria> questionRepository)
        implements IController<StudentAssessmentResponse, StudentAssessmentRequest, StudentAssessmentCriteria> {
    @Override
    public Optional<StudentAssessmentResponse> get(UUID id) {
        return iRepository.get(id);
    }

    @Override
    @Get("/{?criteria*}")
    public List<StudentAssessmentResponse> find(Optional<StudentAssessmentCriteria> criteria) {
        return iRepository.find(criteria);
    }

    @Override
    public Optional<StudentAssessmentResponse> create(StudentAssessmentRequest request) {
        return iRepository.create(request);
    }

    @Override
    public Optional<StudentAssessmentResponse> update(UUID id, StudentAssessmentRequest request) {
        Optional<StudentAssessmentResponse> result = iRepository.update(id, request);
        questionRepository.update(id,new QuestionRequest(null, null, null, Optional.of(true), Optional.of(false)));
        return result;
    }

    @Override
    public void delete(UUID id) {
        iRepository.delete(id);
    }
}
