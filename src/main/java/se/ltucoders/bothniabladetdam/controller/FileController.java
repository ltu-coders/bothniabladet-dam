package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import se.ltucoders.bothniabladetdam.exception.FileStorageException;
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
     void controlName(String fileName) {
        if(fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            throw new FileStorageException("Sorry! The file " + fileName + " contains invalid character or " +
                    "sequence of characters!");
        }
    }

    // Checks if file content type is an empty stream
    void controlEmptySubmission(String fileContentType) {
        if(fileContentType == null) {
            throw new FileStorageException("Sorry! You have to upload at least one image!");
        }
        if(fileContentType.equals(fileContentTypeProperties.getEmptyStream())) {
            throw new FileStorageException("Sorry! You have to upload at least one image!");
        }
    }

    // Checks if a file format corresponds to one of the predefined types
    void controlFileType(String fileContentType, String fileName) {
        if (fileContentType == null) {
            throw new FileStorageException("Sorry! You have to upload at least one image!");
        }
        if (!validContentType(fileContentType)) {
            throw new FileStorageException("Sorry! The file " + fileName +
                        " has wrong file format - " + fileContentType + "!");
        }
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
