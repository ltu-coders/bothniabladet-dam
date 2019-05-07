package se.ltucoders.bothniabladetdam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.ltucoders.bothniabladetdam.db.ImageRepository;
import se.ltucoders.bothniabladetdam.db.UsersRepository;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.exception.DataStorageException;
import se.ltucoders.bothniabladetdam.exception.FileValidationException;
import se.ltucoders.bothniabladetdam.property.FileStorageProperties;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final UsersRepository usersRepository;
    private final ImageRepository imageRepository;
    private final MetadataService metadataService;
    private final Path storageLocation;


    @Autowired
    public ImageService(UsersRepository usersRepository, ImageRepository imageRepository,
                        MetadataService metadataService, FileStorageProperties fileStorageProperties) {
        this.usersRepository = usersRepository;
        this.imageRepository = imageRepository;
        this.metadataService = metadataService;
        this.storageLocation = Paths.get(fileStorageProperties.getLocation()).toAbsolutePath().normalize();
    }

    public void createImage(String tags, String author, String licenseType, MultipartFile file) {
        File imageFile;
        Image image = new Image();

        image.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        imageFile = new File(storageLocation.resolve(image.getFileName()).normalize().toString());

        image.setAuthor(usersRepository.getUserByUsername(author));
        image.setFilePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(StringUtils.cleanPath(file.getOriginalFilename()))
                .toUriString());
        image.setLicenseType(licenseType);

        image.setResolution(metadataService.extractResolution(imageFile));
        image.setWidth(metadataService.extractWidth(imageFile));
        image.setHeight(metadataService.extractHeight(imageFile));
        image.setFileSize(metadataService.extractSize(imageFile));
        image.setMake(metadataService.extractCameraManufacturer(imageFile));
        image.setModel(metadataService.extractCameraModelName(imageFile));
        image.setLocation(metadataService.extractLocation(imageFile));
        image.setDateTime(metadataService.extractDateTime(imageFile));

        // TODO: Here should be a method that extracts metadata from the
        //  image and assigns it to the right image property.

        // TODO:Bellow is data for testing,
        //  which have to be removed when extracting method is ready:
        image.setTags(createTagSet(tags.trim().split("\\s*,\\s*")));

        image.setDescription("Description");
//        *******image.setPrice(new BigDecimal(222));
//        image.setDateTime(LocalDateTime.now());
//        *******image.setNoOfAllowedUses(12);

        try {
            imageRepository.save(image);
        } catch (JpaSystemException ex) {
            // TODO: If there was a problem to store data in the DB we have to delete image
            //  that was already stored in the upload directory.
            throw new DataStorageException("Sorry! Could not store data in the database! " +
                    "Make sure that all required fields are filled in and contain correct information!");
        }
    }

}
