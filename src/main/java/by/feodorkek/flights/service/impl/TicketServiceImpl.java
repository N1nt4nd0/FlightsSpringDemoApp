package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Ticket;
import by.feodorkek.flights.repository.TicketRepository;
import by.feodorkek.flights.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public List<Ticket> getTicketsByContact(ContactData.Type contactType, String contact) {
        return switch (contactType) {
            case PHONE -> ticketRepository.getByPhone(contact);
            case EMAIL -> ticketRepository.getByEmail(contact);
        };
    }

}