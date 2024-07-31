package fete.bird.shared;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A generic interface for repository operations.
 *
 * @param <R> the type of the response object
 * @param <T> the type of the request object
 * @param <C> the type of the criteria object
 */
public interface IRepository<R, T, C> {
    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity
     * @return an {@code Optional} containing the entity if found, otherwise empty
     */
    Optional<R> get(UUID id);
    /**
     * Finds entities based on given criteria.
     *
     * @param criteria the criteria for finding entities
     * @return a list of entities matching the criteria
     */
    List<R> find(Optional<C> criteria);
    /**
     * Creates a new entity based on the given request.
     *
     * @param request the request object containing the details of the entity to be created
     * @return an {@code Optional} containing the created entity if successful, otherwise empty
     * @throws DuplicateException if an error occurs during the creation process
     */
    Optional<R> create(T request);
    /**
     * Updates an existing entity identified by its unique identifier.
     *
     * @param id the unique identifier of the entity to be updated
     * @param request the request object containing the updated details of the entity
     * @return an {@code Optional} containing the updated entity if successful, otherwise empty
     */
    Optional<R> update(UUID id, T request);
    /**
     * Deletes an entity identified by its unique identifier.
     *
     * @param id the unique identifier of the entity to be deleted
     */
    void delete(UUID id);
}
