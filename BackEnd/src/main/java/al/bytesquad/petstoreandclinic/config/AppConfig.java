package al.bytesquad.petstoreandclinic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "al.bytesquad.petstoreandclinic")
@PropertySource("classpath:application.properties")
public class AppConfig {
}
