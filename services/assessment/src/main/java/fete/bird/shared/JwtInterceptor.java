package fete.bird.shared;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

@Filter("/**")
@Singleton
public class JwtInterceptor implements HttpServerFilter {

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        String authorization = request.getHeaders().get("authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            // Decode JWT and extract user ID here
            DecodedJWT decodedJWT = JWT.decode(token);
            String userId = decodedJWT.getSubject();
            request.setAttribute("userId", userId);
        }
        return chain.proceed(request);
    }
}
