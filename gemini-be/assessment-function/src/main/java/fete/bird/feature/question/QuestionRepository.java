package fete.bird.feature.question;

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
public class QuestionRepository implements IRepository<QuestionResponse, QuestionRequest, QuestionCriteria> {
    private final Map<UUID, Question> questionData;
    private final QuestionResponseMapper questionResponseMapper;
    private final RequestQuestionMapper requestQuestionMapper;
    private final QuestionSpecification specification;
    public QuestionRepository(RootProvider<Root> rootProvider,
                              QuestionResponseMapper questionResponseMapper,
                              RequestQuestionMapper requestQuestionMapper,
                              QuestionSpecification specification) {
        this.questionData = rootProvider.root().getQuestion();
        this.questionResponseMapper = questionResponseMapper;
        this.requestQuestionMapper = requestQuestionMapper;
        this.specification = specification;
    }

    @Override
    public Optional<QuestionResponse> get(UUID id) {
        return Optional.ofNullable(questionData.get(id)).map(questionResponseMapper);
    }

    @Override
    public List<QuestionResponse> find(Optional<QuestionCriteria> criteria) {
        specification.setCriteria(criteria);
        return questionData.values().stream()
                .filter(specification)
                .map(questionResponseMapper).toList();
    }

    @Override
    public Optional<QuestionResponse> create(QuestionRequest request) throws DuplicateException {
        if (questionData.values().stream().anyMatch(x -> x.question().equals(request.question())))
            throw new DuplicateException(String.format("%s %s",request.question(), "already exists !"));
        var assessment = requestQuestionMapper.apply(Optional.empty(),request);
        this.save(assessment);
        return Optional.of(questionResponseMapper.apply(assessment));
    }

    @Override
    public Optional<QuestionResponse> update(UUID id, QuestionRequest request) {
        Question assessment = questionData.get(id);
        if (assessment == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        var updateAssessment =  requestQuestionMapper.apply(Optional.of(assessment.id()),request);
        save(updateAssessment);
        return Optional.of(questionResponseMapper.apply(updateAssessment));
    }

    @Override
    public void delete(UUID id) {
        deleteCourseById(id);
    }
    @StoreParams("Course")
    protected void save(@NonNull Question question) {
        questionData.put(question.id(), question);
    }
    @StoreParams("Course")
    protected void deleteCourseById(@NonNull UUID id) {
        if (questionData.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        questionData.remove(id);
    }
}
