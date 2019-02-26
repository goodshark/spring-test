package test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DbHelper {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public JdbcTemplate genJdbcTemplate(@Value("${driver-class}") String driverName,
                                        @Value("${spring.datasource.url}") String jdbcUrl,
                                        @Value("${spring.datasource.username}") String jdbcUser,
                                        @Value("${spring.datasource.password}") String jdbcPass) {
        DataSource dataSource = new DriverManagerDataSource();
        ((DriverManagerDataSource) dataSource).setDriverClassName(driverName);
        ((DriverManagerDataSource) dataSource).setUrl(jdbcUrl);
        ((DriverManagerDataSource) dataSource).setUsername(jdbcUser);
        ((DriverManagerDataSource) dataSource).setPassword(jdbcPass);
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    public void test() {
        String sql = "insert into user values (123, 'good', 'good@c.com')";
        jdbcTemplate.update(sql);
    }

    public void addData(int id, String name, String email) {
        String insertSql = "insert into user (id, name, email) values (?, ?, ?)";
        int res = jdbcTemplate.update(insertSql, id, name, email);
        System.out.println("add data res: " + res);
    }

    public void delData(int id) {
        String delSql = "delete from user where id = ?";
        int res = jdbcTemplate.update(delSql, id);
        System.out.println("del data res: " + res);
    }

    public void updateData(int id, String email) {
        String updateSql = "update user set email = ? where id = ?";
        int res = jdbcTemplate.update(updateSql, email, id);
        System.out.println("update data res: " + res);
    }

    public void queryData(int id) {
        String querySql = "select * from user where id = ?";
        UserData res = jdbcTemplate.queryForObject(querySql, new UserRowMapper(), id);
        System.out.println("query name: " + res.getName());
    }

    public void queryAll() {
        String querySql = "select * from user";
        List<UserData> res = jdbcTemplate.query(querySql, new UserRowMapper());
        for (UserData user: res)
            System.out.println("query id: " + user.getId());
    }
}
