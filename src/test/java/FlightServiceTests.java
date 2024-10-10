import by.feodorkek.flights.FlightsApplication;
import by.feodorkek.flights.dto.FlightScheduleDto;
import by.feodorkek.flights.repository.FlightsRepository;
import by.feodorkek.flights.service.FlightsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@SpringBootTest(classes = FlightsApplication.class)
public class FlightServiceTests {

    @MockBean
    private FlightsRepository flightsRepository;

    @Autowired
    private FlightsService flightsService;

    @Test
    public void testGetScheduleEndDateMustBeAfterStartDate() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        FlightScheduleDto scheduleDto = FlightScheduleDto.builder().startDate(startDate).endDate(endDate).build();
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                flightsService.getSchedule(scheduleDto, PageRequest.ofSize(1))
        );
    }

}