package by.feodorkek.flights.service;

import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Ticket;

import java.util.List;

public interface TicketService {

    /**
     * Getting tickets list by contact type (PHONE or EMAIL) and specified contact value
     * <p>
     * Getting by phone:
     * <blockquote><pre>
     * String phoneNumber = "+375295702036";
     * List tickets = getTicketsByContact(ContactData.Type.PHONE, phoneNumber);
     * </pre></blockquote>
     * Getting by email:
     * <blockquote><pre>
     * String email = "e.belova.07121974@postgrespro.ru";
     * List tickets = getTicketsByContact(ContactData.Type.EMAIL, email);
     * </pre></blockquote>
     *
     * @param contactType type of required contact (PHONE or EMAIL)
     * @param contact     specified contact value
     * @return tickets list by specified contact values
     */
    List<Ticket> getTicketsByContact(ContactData.Type contactType, String contact);

}