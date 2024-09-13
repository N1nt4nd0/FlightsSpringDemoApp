package by.feodorkek.flights.hibernate.types;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;

public abstract class JsonTypeAbstract<T extends Serializable> extends UserTypeAbstract<T> {

    public JsonTypeAbstract(Class<T> userTypeClass) {
        super(userTypeClass);
    }

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