package ru.shakurov.file_hosting_service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import ru.shakurov.file_hosting_service.aspects.EmailAdvice;
import ru.shakurov.file_hosting_service.services.FilesService;
import ru.shakurov.file_hosting_service.services.MailSender;
import ru.shakurov.file_hosting_service.services.SignUpService;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationContextConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(hikariDataSource());
    }

    @Bean
    public DataSource hikariDataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public freemarker.template.Configuration configuration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File("D:\\ITIS\\myProjects\\javalab-4sem\\file-hosting-service\\src\\main\\webapp\\WEB-INF\\templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }

    @Bean
    public FreeMarkerConfig freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/templates/");
        return freeMarkerConfigurer;
    }

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftlh");
        return viewResolver;
    }

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setUsername(environment.getProperty("db.user"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName(environment.getProperty("db.driver"));
        return hikariConfig;
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.setProperty("storage.path", environment.getProperty("storage.path"));
        return properties;
    }

    @Autowired
    private EmailAdvice emailAdvice;

    @Qualifier("default_SignUpService")
    @Autowired
    public SignUpService signUpService;

    @Bean("proxied_SignUpService")
    public SignUpService getSignUpService() throws IOException {
        ProxyFactory proxyFactory = new ProxyFactory(signUpService);
        proxyFactory.addAdvice(this.emailAdvice);
        return (SignUpService) proxyFactory.getProxy();
    }

    @Qualifier("default_FilesService")
    @Autowired
    public FilesService filesService;

    @Bean("proxied_FilesService")
    public FilesService getFilesService() throws IOException {
        ProxyFactory proxyFactory = new ProxyFactory(filesService);
        proxyFactory.addAdvice(this.emailAdvice);
        return (FilesService) proxyFactory.getProxy();
    }
}
