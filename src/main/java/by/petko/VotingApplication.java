package by.petko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class VotingApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VotingApplication.class, args)/*.registerShutdownHook()*/;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(VotingApplication.class);
    }
}
