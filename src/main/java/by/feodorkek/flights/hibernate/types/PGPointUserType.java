package by.feodorkek.flights.hibernate.types;

import jakarta.persistence.Entity;
import org.postgresql.geometric.PGpoint;

/**
 * {@link JsonTypeAbstract} realization for {@link PGpoint}.
 * Needed for correct Hibernate {@link Entity @Entity} processing
 * with custom {@link PGpoint} type
 */
public class PGPointUserType extends UserTypeAbstract<PGpoint> {

    public PGPointUserType() {
        super(PGpoint.class);
    }

    @Override
    public PGpoint deepCopy(PGpoint value) {
        return new PGpoint(value.x, value.y);
    }

}