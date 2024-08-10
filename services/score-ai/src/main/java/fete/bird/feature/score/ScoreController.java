package fete.bird.feature.score;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/scoring")
@ExecuteOn(TaskExecutors.BLOCKING)
public record ScoreController(IScoreService scoreService){

    @Get("/{?request*}")
    public ScoreResponse getMessage(ScoreRequest request) {
        return scoreService.get(request);
    }
}
