package fete.bird.feature.question;

import fete.bird.shared.IController;
import fete.bird.shared.IRepository;
import fete.bird.shared.UserContext;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/question")
@ApiResponse(responseCode = "400", description = "Bad request, invalid data")
@ApiResponse(responseCode = "404", description = "Course not found")
@Tag(name = "Question")
@ExecuteOn(TaskExecutors.BLOCKING)
public record QuestionController(IRepository<QuestionResponse, QuestionRequest, QuestionCriteria> iQuestionRepository,
UserContext userContext)
        implements IController<QuestionResponse, QuestionRequest, QuestionCriteria> {
    @Override
    public Optional<QuestionResponse> get(UUID id) {
        return iQuestionRepository.get(id);
    }

    @Override
    @Get("/{?criteria*}")
    public List<QuestionResponse> find(Optional<QuestionCriteria> criteria) {
        System.out.println(this.userContext.getCurrentUserId());
        return iQuestionRepository.find(criteria);
    }

    @Override
    public Optional<QuestionResponse> create(QuestionRequest request) {
        return iQuestionRepository.create(request);
    }

    @Override
    public Optional<QuestionResponse> update(UUID id, QuestionRequest request) {
        return iQuestionRepository.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        iQuestionRepository.delete(id);
    }
}
