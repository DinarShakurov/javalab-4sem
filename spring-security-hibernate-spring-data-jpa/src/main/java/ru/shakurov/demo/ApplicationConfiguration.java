package ru.shakurov.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.shakurov.demo.configs.AspectJConfiguration;
import ru.shakurov.demo.configs.JpaConfiguration;
/*
@Configuration
@Import(value = {AspectJConfiguration.class, JpaConfiguration.class})*/
public class ApplicationConfiguration {
}
