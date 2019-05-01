package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import se.ltucoders.bothniabladetdam.property.FileContentTypeProperties;

@Controller
public class FileController {
    private final FileContentTypeProperties fileContentTypeProperties;

    @Autowired
    public FileController(FileContentTypeProperties fileContentTypeProperties) {
        this.fileContentTypeProperties = fileContentTypeProperties;
    }

    // Checks if the file's name contains invalid characters
    //***If we will change names of the images after they***//
    //***being uploaded this method can be removed!***//
     boolean controlName(String fileName) {
         return (!fileName.contains("..") && !fileName.contains("/") && !fileName.contains("\\"));
    }

    // Checks if file content type is an empty stream
    boolean controlEmptySubmission(String fileContentType) {
        if(fileContentType == null) {
            return false;
        }
        return (!fileContentType.equals(fileContentTypeProperties.getEmptyStream()));
    }

    // Checks if a file format corresponds to one of the predefined types
    boolean controlFileType(String fileContentType) {
        return (validContentType(fileContentType));
    }

    // Compares that content type is equal to one of the predefined types
    private boolean validContentType(String contentType) {
        for (String validContentType : fileContentTypeProperties.getValidContentTypes()) {
            if (contentType.equals(validContentType)) {
                return true;
            }
        }
        return false;
    }
}
