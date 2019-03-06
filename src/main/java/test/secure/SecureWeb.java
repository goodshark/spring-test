package test.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecureWeb  extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    private String userSql = "select name, pass, enable from auth where name = ?";

    private String authSql = "select name, role from auth where name = ?";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // memory
        /*auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("u1").password(new BCryptPasswordEncoder().encode("123")).roles("USER")
                .and()
                .withUser("u2").password(new BCryptPasswordEncoder().encode("123456")).roles("USER", "ADMIN");*/
        // jdbc
        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(userSql)
                .authoritiesByUsernameQuery(authSql).passwordEncoder(new OwnPassEncoderImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin()
                .and()
                .rememberMe().and().httpBasic();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/db/**").authenticated()
                .antMatchers("/greeting").authenticated().anyRequest().permitAll();
    }
}
