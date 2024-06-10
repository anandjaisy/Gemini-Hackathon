package fete.bird.shared;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * This interface defines operations for a RESTful controller managing data.
 *
 * @param <R> The type of the response object returned by the controller methods.
 * @param <T> The type of the request object used for create and update operations.
 * @param <C> The type of the criteria object used for filtering data in find operations.
 */
public interface IController <R, T, C>{
    /**
     * Retrieves a single entity by its unique identifier.
     * This method maps to an HTTP GET request with the path "/{id}" where "{id}" is replaced by the actual entity ID.
     * @param id The unique identifier of the entity to retrieve.
     * @return An Optional containing the retrieved entity of type R, or Optional.empty() if not found.
     */
    @Get(uri = "/{id}")
    Optional<R> get(UUID id);
    /**
     * Finds a list of entities based on the provided optional criteria.
     * This method maps to an HTTP GET request without a specific path.
     *
     * @param criteria An Optional containing the criteria object used for filtering.
     *                 An empty Optional signifies retrieving all entities.
     * @return A list of entities of type R matching the specified criteria.
     */
    @Get
    List<R> find(Optional<C> criteria);
    /**
     * Creates a new entity based on the provided request object.
     * This method maps to an HTTP POST request.
     * The expected request body should contain the data for the new entity in the format specified by the `@Body` annotation.
     * The response status code is set to CREATED (201) upon successful creation.
     *
     * @param request The request object containing data for the new entity.
     * @return An Optional containing the created entity of type R, or throws a
     *         DuplicateEntityException if a duplicate entity already exists.
     */
    @Post
    @Status(HttpStatus.CREATED)
    Optional<R> create(@Body T request);
    /**
     * Updates an existing entity with data from the provided request object.
     * This method maps to an HTTP PATCH request with the path "/{id}" where "{id}" is replaced by the actual entity ID.
     * The expected request body should contain the data for the update in the format specified by the `@Body` annotation.
     * The response status code is set to ACCEPTED (202) upon successful update.
     *
     * @param id The unique identifier of the entity to update.
     * @param request The request object containing data for the update.
     * @return An Optional containing the updated entity of type R, or throws a
     *         runtime exception if the entity is not found.
     * @throws RuntimeException Thrown when the entity with the provided ID is not found.
     */
    @Patch("/{id}")
    @Status(HttpStatus.ACCEPTED)
    Optional<R> update(@NonNull UUID id, @Body T request);
    /**
     * Deletes an entity identified by its unique identifier.
     * This method likely maps to an HTTP DELETE request with the path "/{id}"
     * (specific implementation might depend on the chosen framework).
     *
     * @param id The unique identifier of the entity to delete.
     */
    @Status(HttpStatus.NO_CONTENT)
    @Delete("/{id}")
    void delete(@NonNull UUID id);
}
