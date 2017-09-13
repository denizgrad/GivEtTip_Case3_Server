package cloud.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = "cloud")
@PropertySource("classpath:META-INF/app.properties")
public class AppConfig  {

}
