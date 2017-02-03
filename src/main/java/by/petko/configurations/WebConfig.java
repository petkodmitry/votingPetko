package by.petko.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .ignoreFailedDrops(true)
                .setName("voting")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("scripts/runFirst.sql")
                .setScriptEncoding("UTF-8")
                .build();
    }

    /*@Bean
    public HibernateJpaSessionFactoryBean sessionFactoryBean() {
        return new HibernateJpaSessionFactoryBean();
    }*/

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
}
