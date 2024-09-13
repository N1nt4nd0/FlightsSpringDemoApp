import by.feodorkek.flights.FlightsApplication;
import by.feodorkek.flights.dto.FlightsUserDto;
import by.feodorkek.flights.model.FlightsUser;
import by.feodorkek.flights.repository.FlightsUserRepository;
import by.feodorkek.flights.service.FlightsUserService;
import jakarta.persistence.EntityExistsException;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest(classes = FlightsApplication.class)
class FlightsUserServiceTests {

    @MockBean
    private FlightsUserRepository userRepository;

    @Autowired
    private FlightsUserService userService;

    @Test
    public void testCreateNewFlightsUserWithEmptyRoles() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(new FlightsUserDto("TestUser", "123", ArrayUtils.EMPTY_STRING_ARRAY));
        });
    }

    @Test
    public void testCreateNewFlightsUserAlreadyExist() {
        Mockito.when(userRepository.findById("TestUser")).thenThrow(new EntityExistsException("User already exist"));
        Assertions.assertThrows(EntityExistsException.class, () -> {
            userService.createUser(new FlightsUserDto("TestUser", "123", new String[]{"USER"}));
        });
    }

    @Test
    public void testGetAllUsersWithPageableUse() {
        FlightsUser flightsUser = FlightsUser.builder().username("TestUser").build();
        PageRequest pageRequest = PageRequest.ofSize(Integer.MAX_VALUE);
        Mockito.when(userRepository.findAll(pageRequest)).thenReturn(new PageImpl<FlightsUser>(List.of(flightsUser)));
        Page<FlightsUser> usersPage = userService.getAllUsers(pageRequest);
        Assertions.assertEquals(1, usersPage.getTotalElements());
        Assertions.assertEquals(flightsUser, usersPage.iterator().next());
    }

}