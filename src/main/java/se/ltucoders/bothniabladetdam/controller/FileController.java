package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import se.ltucoders.bothniabladetdam.exception.FileStorageException;
import se.ltucoders.bothniabladetdam.property.FileContentTypeProperties;

@Controller
public class FileController {
    private final FileContentTypeProperties fileContentTypeProperties;

    @Autowired
    public FileController(FileContentTypeProperties fileContentTypeProperties) {
        this.fileContentTypeProperties = fileContentTypeProperties;
    }

    // Validates an image
    void validateFile(MultipartFile file) {
        controlName(file);
        controlEmptySubmission(file);
        controlFileType(file);
    }

    // Validates several images
    void validateFiles(MultipartFile[] files) {
        controlEmptySubmission(files);
        for(MultipartFile file : files) {
            controlName(file);
            controlFileType(file);
        }
    }

    // Checks if the file's name contains invalid characters
    //***If we will change names of the images after they***//
    //***being uploaded this method can be removed!***//
    private void controlName(MultipartFile file) {
         String fileName = StringUtils.cleanPath(file.getOriginalFilename());
         if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
             throw new FileStorageException("Sorry! The file " + fileName +
                     " contains invalid character or " + "sequence of characters!");
         }
    }

    private void controlEmptySubmission(MultipartFile[] files) {
        if(files.length <= 0) {
            throw new FileStorageException("Sorry! You have to upload at least one image!");
        }
        for (MultipartFile file : files) {
            controlEmptySubmission(file);
        }
    }

    // Checks if there is no uploaded file
    private void controlEmptySubmission(MultipartFile file) {
        if (file.getContentType() == null ||
                file.isEmpty() ||
                file.getContentType().equals(fileContentTypeProperties.getEmptyStream())) {
            throw new FileStorageException("Sorry! You have to upload at least one image!");
        }

    }

    // Checks if a file format corresponds to one of the predefined types
    private void controlFileType(MultipartFile file) {
        if (!validContentType(file.getContentType())) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            throw new FileStorageException("Sorry! The file " + fileName +
                    " has wrong file format - " + file.getContentType() + "!");
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
