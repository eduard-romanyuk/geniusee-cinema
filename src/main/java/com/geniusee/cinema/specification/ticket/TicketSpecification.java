package com.geniusee.cinema.specification.ticket;

import com.geniusee.cinema.domain.ticket.Ticket;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(path = "movieId", spec = Equal.class),
        @Spec(path = "hall", spec = Equal.class),
        @Spec(path = "place", params = "place:eq", spec = Equal.class),
        @Spec(path = "place", params = "place:gte", spec = GreaterThanOrEqual.class),
        @Spec(path = "place", params = "place:lte", spec = LessThanOrEqual.class),
        @Spec(path = "price", params = "price:gte", spec = GreaterThanOrEqual.class),
        @Spec(path = "price", params = "price:lte", spec = LessThanOrEqual.class),
})
public interface TicketSpecification extends Specification<Ticket> {
}
