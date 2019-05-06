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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final UsersRepository usersRepository;
    private final ImageRepository imageRepository;
    private final MetadataService metadataService;


    @Autowired
    public ImageService(UsersRepository usersRepository,
                        ImageRepository imageRepository,
                        MetadataService metadataService) {
        this.usersRepository = usersRepository;
        this.imageRepository = imageRepository;
        this.metadataService = metadataService;
    }

    public void createImage(String tags, String author, String licenseType, MultipartFile file) {
        Image image = new Image();
        image.setAuthor(usersRepository.getUserByUsername(author));
        image.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        image.setFilePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/")
                .path(StringUtils.cleanPath(file.getOriginalFilename()))
                .toUriString());
        image.setLicenseType(licenseType);

        // TODO: Here should be a method that extracts metadata from the
        //  image and assigns it to the right image property.

        // TODO:Bellow is data for testing,
        //  which have to be removed when extracting method is ready:
        image.setDescription("Description");
        image.setResolution("Resolution");
        image.setWidth(0);
        image.setHeight(0);
        image.setFileSize("File size");
        image.setDateTime(LocalDateTime.now());
        image.setMake("Canon");
        image.setModel("700p");
        image.setLocation("Luleå");
        image.setLicenseType("License type");
        image.setNoOfAllowedUses(12);
        image.setPrice(new BigDecimal(222));

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
