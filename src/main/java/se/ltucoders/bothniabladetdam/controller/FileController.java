package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import se.ltucoders.bothniabladetdam.property.FileContentTypeProperties;

import java.io.IOException;

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
    boolean controlEmptySubmission(MultipartFile file) {
        return (file.getContentType() != null ||
                !file.isEmpty() ||
                !file.getContentType().equals(fileContentTypeProperties.getEmptyStream()));
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
