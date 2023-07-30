package com.jvmaware.tcsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MariaDBContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersDemoApplication {

    /**
     * The following will inject a MariaDB container in the
     * application context that the application can use.
     *
     * <ol>
     *     <li>ServiceConnection: </lil> indicates a connection that the app can use. It replaces the original {@code @DynamicPropertySource}</li>
     *     <li>RestartScope: </li> do not restart DB container, even if application restarts
     * </ol>
     *
     * @return mariadb:10.6.13 container
     * @author gaurs
     */
    @Bean
    @ServiceConnection(name = "mariadb")
    @RestartScope
    MariaDBContainer<?> mariaDbContainer() {
        try (MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:10.6.13")) {
            mariaDBContainer.withUsername("sbtc")
                    .withPassword("password")
                    .withDatabaseName("sbtc");
            return mariaDBContainer;
        }
    }

    public static void main(String[] args) {
        SpringApplication.from(TestContainersDemo::main).with(TestContainersDemoApplication.class).run(args);
    }

}
