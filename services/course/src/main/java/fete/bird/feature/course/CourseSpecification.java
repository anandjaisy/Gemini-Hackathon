package fete.bird.feature.course;

import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class CourseSpecification implements Predicate<Course> {
    private Optional<CourseCriteria> criteria;
    public CourseSpecification() {
        this.criteria = Optional.empty();
    }

    public void setCriteria(Optional<CourseCriteria> criteria) {
        this.criteria = criteria;
    }

    @Override
    public boolean test(Course courseEntity) {
        return criteria.map(cc -> {
            Predicate<Course> predicate = course -> true;
            if (cc.name().isPresent())
                predicate = predicate.and(course -> course.name().equalsIgnoreCase(cc.name().get()));
            return predicate.test(courseEntity);
        }).orElse(true);
    }
}
