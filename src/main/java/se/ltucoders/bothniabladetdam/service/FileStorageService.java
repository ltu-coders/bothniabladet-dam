package se.ltucoders.bothniabladetdam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import se.ltucoders.bothniabladetdam.exception.FileNotFoundException;
import se.ltucoders.bothniabladetdam.exception.FileStorageException;
import se.ltucoders.bothniabladetdam.property.FileStorageProperties;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {

    private final Path storageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.storageLocation = Paths.get(fileStorageProperties.getLocation()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storageLocation);
        } catch (IOException e){
            throw new FileStorageException("Sorry! The server has problem to create storage directory!");
        }
    }

    // Stores file in the repository
    public File storeFile(MultipartFile file) {
        // Normalize file name
        // https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Copy file to the target location. Replacing existing file with the same name.
            Path targetLocation = this.storageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.toFile();
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!");
        }
    }

    // Uploads file to the client
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.storageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    // Deletes a file
    void deleteFile(File file) {
        if(!file.delete()) {
            throw new FileStorageException("Could not store file " + file.getName() + ". Please try again!");
        }
    }
} // end class