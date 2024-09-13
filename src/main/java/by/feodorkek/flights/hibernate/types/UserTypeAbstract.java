package by.feodorkek.flights.hibernate.types;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class UserTypeAbstract<T extends Serializable> implements UserType<T> {

    public static final int TYPE = Types.OTHER;
    private final Class<T> userTypeClass;

    public UserTypeAbstract(Class<T> userTypeClass) {
        this.userTypeClass = userTypeClass;
    }

    @Override
    public int getSqlType() {
        return TYPE;
    }

    @Override
    public Class<T> returnedClass() {
        return userTypeClass;
    }

    @Override
    public T nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Object value = rs.getObject(position);
        try {
            return value != null ? safeGetObject(value) : null;
        } catch (IOException exception) {
            throw new SQLException(exception);
        }
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public T assemble(Serializable cached, Object owner) {
        return userTypeClass.cast(cached);
    }

    @Override
    public Serializable disassemble(T value) {
        return value;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, T value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null) {
            st.setNull(index, TYPE);
        } else {
            st.setObject(index, value, TYPE);
        }
    }

    @Override
    public int hashCode(T x) {
        return x.hashCode();
    }

    @Override
    public boolean equals(T x, T y) {
        return x.equals(y);
    }

    public T safeGetObject(Object object) throws IOException {
        if (userTypeClass.isInstance(object)) {
            return userTypeClass.cast(object);
        }
        return null;
    }

}