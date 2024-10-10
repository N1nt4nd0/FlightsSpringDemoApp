package by.feodorkek.flights.hibernate.types;

import jakarta.persistence.Entity;

/**
 * {@link JsonTypeAbstract} realization for {@link ContactData}.
 * Needed for correct Hibernate {@link Entity @Entity} processing
 * with custom {@link ContactData} type
 */
public class ContactDataUserType extends JsonTypeAbstract<ContactData> {

    public ContactDataUserType() {
        super(ContactData.class);
    }

    @Override
    public ContactData deepCopy(ContactData value) {
        return new ContactData(value.getPhone(), value.getEmail());
    }

}