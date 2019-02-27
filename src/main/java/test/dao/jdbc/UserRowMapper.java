package test.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserData> {
    @Override
    public UserData mapRow(ResultSet resultSet, int i) throws SQLException {
        return new UserData(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"));
    }
}
