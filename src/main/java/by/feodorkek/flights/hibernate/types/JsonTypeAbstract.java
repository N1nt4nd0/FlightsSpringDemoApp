package by.feodorkek.flights.hibernate.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract JsonType realization of {@link UserTypeAbstract} for working
 * with jsonb ({@link PGobject}) types from database
 *
 * @param <T> Custom user type Class
 */
public abstract class JsonTypeAbstract<T extends Serializable> extends UserTypeAbstract<T> {

    /**
     * Constructor accepts custom type {@code Class} which works with jsonb ({@link PGobject}) type in database
     *
     * @param userTypeClass T user type {@code Class}
     */
    public JsonTypeAbstract(Class<T> userTypeClass) {
        super(userTypeClass);
    }

    /**
     * Mapping jsonb ({@link PGobject}) parameter from database to T custom type
     *
     * @param object object to map in T type
     * @return T mapped object
     * @throws IOException if mapping is down
     */
    @Override
    public T safeGetObject(Object object) throws IOException {
        if (object instanceof PGobject pg) {
            if ("jsonb".equals(pg.getType())) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    return mapper.readValue(pg.getValue(), returnedClass());
                } catch (Exception exception) {
                    throw new IOException(exception);
                }
            }
        }
        return null;
    }

}