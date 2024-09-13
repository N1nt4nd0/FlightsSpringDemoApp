package by.feodorkek.flights.service;

import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> getTicketsByContact(ContactData.Type contactType, String contact);

}