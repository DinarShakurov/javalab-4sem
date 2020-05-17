package ru.shakurov.jlmq.jlmq_server.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("ru.shakurov.jlmq.jlmq_server.repository")
public class JpaConfiguration {
}
