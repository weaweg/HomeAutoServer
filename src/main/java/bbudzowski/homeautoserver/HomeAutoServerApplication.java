package bbudzowski.homeautoserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class HomeAutoServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HomeAutoServerApplication.class);
        Properties properties = new Properties();
        properties.setProperty("server.port", "4433");
        properties.setProperty("server.shutdown", "graceful");
        properties.setProperty("server.ssl.key-store-type", "PKCS12");
        properties.setProperty("server.ssl.key-store", "classpath:bbudzowski.p12");
        properties.setProperty("server.ssl.key-store-password", "tial2o3");
        properties.setProperty("server.ssl.key-alias", "bbudzowski");
        properties.setProperty("server.ssl.enabled", "true");
        app.setDefaultProperties(properties);
        app.run(args);
    }

    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.mariadb.jdbc.Driver")
                .url("jdbc:mariadb://localhost:3306/home_auto")
                .username("automation")
                .password("tial2o3")
                .build();
    }
}
