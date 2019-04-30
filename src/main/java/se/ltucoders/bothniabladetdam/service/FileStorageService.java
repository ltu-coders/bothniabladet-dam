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
            throw new FileStorageException("Could not create storage directory.", e);
        }
    }

    // Stores file in the repository
    public boolean storeFile(MultipartFile file) {
        // Normalize file name
        // https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Copy file to the target location. Replacing existing file with the same name.
            Path targetLocation = this.storageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Exception login can be here

            return false;
        }
        return true;
    }

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
} // end class