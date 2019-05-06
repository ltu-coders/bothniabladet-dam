package se.ltucoders.bothniabladetdam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import se.ltucoders.bothniabladetdam.property.FileContentTypeProperties;
import se.ltucoders.bothniabladetdam.property.FileStorageProperties;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class)
@EnableConfigurationProperties({
        FileContentTypeProperties.class,
        FileStorageProperties.class
})
public class BothniabladetDamApplication {

    public static void main(String[] args) {
        SpringApplication.run(BothniabladetDamApplication.class, args);
    }
}
