package by.feodorkek.flights.hibernate.types;

public class LocalizedNameUserType extends JsonTypeAbstract<LocalizedName> {

    public LocalizedNameUserType() {
        super(LocalizedName.class);
    }

    @Override
    public LocalizedName deepCopy(LocalizedName value) {
        return new LocalizedName(value.getEn(), value.getRu());
    }

}