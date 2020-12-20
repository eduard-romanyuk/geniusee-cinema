package com.geniusee.cinema.specification;

import com.geniusee.cinema.domain.Movie;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "name", spec = LikeIgnoreCase.class),
        @Spec(path = "description", spec = LikeIgnoreCase.class),
        @Spec(path = "durationMinutes", params = "durationMinutes:gte", spec = GreaterThanOrEqual.class),
        @Spec(path = "durationMinutes", params = "durationMinutes:lte", spec = LessThanOrEqual.class),
})
public interface MovieSpecification extends Specification<Movie> {
}
