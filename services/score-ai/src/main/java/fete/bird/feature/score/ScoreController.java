package fete.bird.feature.score;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/scoring")
@ExecuteOn(TaskExecutors.BLOCKING)
public record ScoreController(IScoreService scoreService){
    @Post
    public ScoreResponse get( @Body ScoreRequest request) {
        return scoreService.get(request);
    }

}
