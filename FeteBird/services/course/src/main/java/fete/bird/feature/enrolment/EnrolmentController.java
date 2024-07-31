package fete.bird.feature.enrolment;

import fete.bird.shared.IController;
import fete.bird.shared.IRepository;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("/enrolment")
@ApiResponse(responseCode = "400", description = "Bad request, invalid data")
@ApiResponse(responseCode = "404", description = "Course not found")
@Tag(name = "Enrolment")
public record EnrolmentController(IRepository<EnrolmentResponse, EnrolmentRequest, EnrolmentCriteria> iEnrolmentRepository) implements IController<EnrolmentResponse, EnrolmentRequest, EnrolmentCriteria> {
    @Override
    public Optional<EnrolmentResponse> get(UUID id) {
        return iEnrolmentRepository.get(id);
    }

    @Override
    public List<EnrolmentResponse> find(Optional<EnrolmentCriteria> criteria) {
        return iEnrolmentRepository.find(criteria);
    }

    @Override
    public Optional<EnrolmentResponse> create(EnrolmentRequest request) {
        return iEnrolmentRepository.create(request);
    }

    @Override
    public Optional<EnrolmentResponse> update(UUID id, EnrolmentRequest request) {
        return iEnrolmentRepository.update(id, request);
    }

    @Override
    public void delete(UUID id) {
        iEnrolmentRepository.delete(id);
    }
}
