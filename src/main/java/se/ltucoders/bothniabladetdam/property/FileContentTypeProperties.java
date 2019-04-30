package se.ltucoders.bothniabladetdam.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "content-type.properties")
public class FileContentTypeProperties {
    private String[] validContentTypes;
    private String emptyStream;

    public String[] getValidContentTypes() {
        return validContentTypes;
    }

    public void setValidContentTypes(String[] validContentTypes) {
        this.validContentTypes = validContentTypes;
    }

    public String getEmptyStream() {
        return emptyStream;
    }

    public void setEmptyStream(String emptyStream) {
        this.emptyStream = emptyStream;
    }
}

