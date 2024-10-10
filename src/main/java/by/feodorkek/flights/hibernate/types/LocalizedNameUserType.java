package by.feodorkek.flights.hibernate.types;

import jakarta.persistence.Entity;

/**
 * {@link JsonTypeAbstract} realization for {@link LocalizedName}.
 * Needed for correct Hibernate {@link Entity @Entity} processing
 * with custom {@link LocalizedName} type
 */
public class LocalizedNameUserType extends JsonTypeAbstract<LocalizedName> {

    public LocalizedNameUserType() {
        super(LocalizedName.class);
    }

    @Override
    public LocalizedName deepCopy(LocalizedName value) {
        return new LocalizedName(value.getEn(), value.getRu());
    }

}