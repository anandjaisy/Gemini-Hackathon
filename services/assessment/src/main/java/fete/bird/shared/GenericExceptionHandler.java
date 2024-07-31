package fete.bird.shared;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.server.exceptions.response.ErrorContext;
import io.micronaut.http.server.exceptions.response.ErrorResponseProcessor;
import jakarta.inject.Singleton;

@Produces // Ensure the response content-type is set to application/json with the @Produces annotation.
@Singleton
public class GenericExceptionHandler<E extends RuntimeException> implements ExceptionHandler<E, HttpResponse<?>> {
    private final ErrorResponseProcessor<?> errorResponseProcessor;

    public GenericExceptionHandler(ErrorResponseProcessor<?> errorResponseProcessor) {
        this.errorResponseProcessor = errorResponseProcessor;
    }

    /**
     * Handles the specified exception and returns an appropriate HTTP response.
     *
     * @param request   the HTTP request
     * @param exception the exception to handle
     * @return an HTTP response representing the error
     */
    @Override
    public HttpResponse<?> handle(HttpRequest request, E exception) {
        ErrorContext errorContext = ErrorContext.builder(request)
                .cause(exception)
                .errorMessage(exception.getMessage())
                .build();
        if (exception instanceof DuplicateException)
            return errorResponseProcessor.processResponse(errorContext, HttpResponse.badRequest());
        else if (exception instanceof NotFoundException)
            return errorResponseProcessor.processResponse(errorContext, HttpResponse.notFound());
        else
            return errorResponseProcessor.processResponse(errorContext, HttpResponse.serverError());
    }
}