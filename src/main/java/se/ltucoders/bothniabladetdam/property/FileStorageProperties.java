package se.ltucoders.bothniabladetdam.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//http://code.scottshipp.com/2017/08/30/spring-boot-what-to-do-when-autowired-stops-working/
//Spring Component annotation is used to denote a class as Component. It means that Spring
// framework will autodetect these classes for dependency injection when annotation-based
// configuration and classpath scanning is used.
@Component
// Enables setting the storage directory via the .properties file
@ConfigurationProperties("storage")
public class FileStorageProperties {
    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}