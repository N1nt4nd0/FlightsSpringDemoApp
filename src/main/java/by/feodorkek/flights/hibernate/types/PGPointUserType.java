package by.feodorkek.flights.hibernate.types;

import org.postgresql.geometric.PGpoint;

public class PGPointUserType extends UserTypeAbstract<PGpoint> {

    public PGPointUserType() {
        super(PGpoint.class);
    }

    @Override
    public PGpoint deepCopy(PGpoint value) {
        return new PGpoint(value.x, value.y);
    }

}