package fete.bird.feature.course;

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
public class CourseRepository implements IRepository<CourseResponse,CourseRequest, CourseCriteria> {
    private final CourseToResponseMapper courseToResponseMapper;
    private final RequestToCourseMapper requestToCourseMapper;
    private final  CourseSpecification courseSpecification;
    private final Map<UUID, Course> courseData;

    public CourseRepository(RootProvider<Root> rootProvider,
                            CourseToResponseMapper courseToResponseMapper,
                            RequestToCourseMapper requestToCourseMapper,
                            CourseSpecification courseSpecification) {
        this.courseData = rootProvider.root().getCourse();
        this.courseToResponseMapper = courseToResponseMapper;
        this.requestToCourseMapper = requestToCourseMapper;
        this.courseSpecification = courseSpecification;
    }

    @Override
    public Optional<CourseResponse> get(UUID id) {
        return Optional.ofNullable(courseData.get(id)).map(courseToResponseMapper);
    }

    @Override
    public List<CourseResponse> find(Optional<CourseCriteria> criteria) {
        courseSpecification.setCriteria(criteria);
        return courseData.values().stream()
                .filter(courseSpecification)
                .map(courseToResponseMapper).toList();
    }

    @Override
    public Optional<CourseResponse> create(CourseRequest request) {
        if (courseData.values().stream().anyMatch(x -> x.name().equals(request.name())))
            throw new DuplicateException(request.name());
        Course course = requestToCourseMapper.apply(Optional.empty(), request);
        this.save(this.courseData, course);
        return Optional.of(courseToResponseMapper.apply(course));
    }

    @Override
    public Optional<CourseResponse> update(UUID id, CourseRequest request) {
        Course course = courseData.get(id);
        if (course == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        Course updateCourse =  requestToCourseMapper.apply(Optional.of(course.id()), request);
        save(this.courseData,updateCourse);
        return Optional.of(this.courseToResponseMapper.apply(updateCourse));
    }

    @Override
    public void delete(UUID id) {
        deleteCourseById(this.courseData,id);
    }

    @StoreParams("course")
    protected void save(Map<UUID, Course> course, @NonNull Course request) {
        course.put(request.id(), request);
    }

    @StoreParams("course")
    protected void deleteCourseById(Map<UUID, Course> course,@NonNull UUID id) {
        if (course.get(id) == null)
            throw new NotFoundException(Constants.NO_Record_Found);
        course.remove(id);
    }
}
