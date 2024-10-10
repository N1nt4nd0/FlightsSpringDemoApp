package by.feodorkek.flights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * Main application boot {@code Class}. Here is important annotations:
 * <p>
 * {@link SpringBootApplication @SpringBootApplication} - required for starting application and components scanning
 * {@link EnableTransactionManagement @EnableTransactionManagement} - activates transaction usage for automatic
 * operation of transactions in service methods marked with {@link Transactional @Transactional} annotation
 */
@SpringBootApplication
@EnableTransactionManagement
public class FlightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightsApplication.class, args);
    }

}