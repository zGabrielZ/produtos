package br.com.gabrielferreira.produtos;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

@Testcontainers
public class RabbitMQTestContainer {

    @Container
    static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("rabbitmq:3.13.0-management")
            .withExposedPorts(5672) // Porta intera do rabbitmq docker
            .withEnv("RABBITMQ_DEFAULT_USER", "test-produtos")
            .withEnv("RABBITMQ_DEFAULT_PASS", "test-produtos");

    @DynamicPropertySource
    static void registerRabbitMQProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getFirstMappedPort().toString());
        registry.add("spring.rabbitmq.username", () -> "test-produtos");
        registry.add("spring.rabbitmq.password", () -> "test-produtos");
    }

    @BeforeAll
    static void setUp(){
        rabbitMQContainer.setPortBindings(Collections.singletonList("5673:5672")); // Mapeia a porta 5673 para a porta 5672 do container
    }
}
