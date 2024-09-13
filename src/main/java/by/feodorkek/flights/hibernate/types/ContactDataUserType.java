package by.feodorkek.flights.hibernate.types;

public class ContactDataUserType extends JsonTypeAbstract<ContactData> {

    public ContactDataUserType() {
        super(ContactData.class);
    }

    @Override
    public ContactData deepCopy(ContactData value) {
        return new ContactData(value.getPhone(), value.getEmail());
    }

}