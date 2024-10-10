package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Ticket;
import by.feodorkek.flights.repository.TicketRepository;
import by.feodorkek.flights.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link TicketService} implementation for work with tickets data
 */
@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    /**
     * {@link TicketRepository} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final TicketRepository ticketRepository;

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
    @Override
    @Transactional
    public List<Ticket> getTicketsByContact(ContactData.Type contactType, String contact) {
        return switch (contactType) {
            case PHONE -> ticketRepository.getByPhone(contact);
            case EMAIL -> ticketRepository.getByEmail(contact);
        };
    }

}