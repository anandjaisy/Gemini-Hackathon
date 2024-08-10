package fete.bird.feature.score;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import fete.bird.common.ConfigResource;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public record ScoreService(ConfigResource configResource) implements IScoreService {
    @Override
    public ScoreResponse get(ScoreRequest request) {
        ChatLanguageModel model = VertexAiGeminiChatModel.builder()
                .project(configResource.name())
                .location(configResource.location())
                .responseMimeType("application/json")
                .modelName(configResource.model())
                .maxRetries(1)
//                .logRequests(true)
//                .logResponses(true)
                .build();
        // Define the prompt template
        PromptTemplate promptTemplate = PromptTemplate.from("""
                As a university professor, your task is to evaluate student assignment submissions.
                Please grade the student's answer based on the provided base question and answer, using the following criteria:
                
                A+ (90-100%): The student's answer demonstrates a complete understanding of the topic, accurately reflecting the context and meaning of the base answer.
                A (80-89%): The student's answer shows a strong grasp of the topic, with minor inaccuracies or omissions in conveying the context and meaning.
                B (70-79%): The student's answer displays a good understanding of the key concepts, but there are some noticeable gaps or misunderstandings in the context and meaning.
                B+ (50-70%): The student's answer displays a good understanding of the key concepts, but there are some noticeable gaps or misunderstandings in the context and meaning.
                C (30-50%): The student's answer demonstrates a basic understanding of the topic, but lacks depth and may contain significant inaccuracies or omissions in conveying the context and meaning.
                D (Below 30%): The student's answer shows limited understanding of the topic and fails to adequately address the context and meaning of the base answer.
                
                If the student answer is 100% matched with the base answer, please don't mark it as copied. It is a correct answer. You should grade that as A+
                
                Reference Information:
                BASE QUESTION: {{baseQuestion}}
                BASE ANSWER: {{baseAnswer}}
                """
        );

        Map<String, Object> variables = new HashMap<>();
        variables.put("baseQuestion", request.baseQuestion());
        variables.put("baseAnswer", request.baseAnswer());

        Prompt prompt = promptTemplate.apply(variables);

        IScoringAnalyzer analyzer = AiServices.builder(IScoringAnalyzer.class)
                .chatLanguageModel(model)
                .systemMessageProvider(chatMemoryId -> prompt.text())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
        return analyzer.score(request.answer());
    }
}
