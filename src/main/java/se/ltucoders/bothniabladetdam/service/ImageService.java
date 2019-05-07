package se.ltucoders.bothniabladetdam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.ltucoders.bothniabladetdam.db.ImageRepository;
import se.ltucoders.bothniabladetdam.db.TagRepository;
import se.ltucoders.bothniabladetdam.db.UsersRepository;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.db.entity.Tag;
import se.ltucoders.bothniabladetdam.exception.DataStorageException;
import se.ltucoders.bothniabladetdam.exception.FileValidationException;
import se.ltucoders.bothniabladetdam.property.FileStorageProperties;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ImageService {

    private final UsersRepository usersRepository;
    private final ImageRepository imageRepository;
    private final MetadataService metadataService;
    private final TagRepository tagRepository;
    private final Path storageLocation;


    @Autowired
    public ImageService(UsersRepository usersRepository, ImageRepository imageRepository,
                        MetadataService metadataService, TagRepository tagRepository, 
                        FileStorageProperties fileStorageProperties) {
        this.usersRepository = usersRepository;
        this.imageRepository = imageRepository;
        this.metadataService = metadataService;
        this.tagRepository = tagRepository;
        this.storageLocation = Paths.get(fileStorageProperties.getLocation()).toAbsolutePath().normalize();
    }


    public void createImage(String tags, String author, String licenseType, File file) {
        Image image = new Image();
        image.setAuthor(usersRepository.getUserByUsername(author));
        image.setFileName(file.getName());
        image.setFilePath(file.toURI().toString());


        image.setLicenseType(licenseType);

        // TODO: Here should be a method that extracts metadata from the
        //  image and assigns it to the right image property.

        // TODO:Bellow is data for testing,
        //  which have to be removed when extracting method is ready:
        image.setDescription("Description"); //TODO: get description input
        image.setResolution(metadataService.extractResolution(file));
        image.setWidth(metadataService.extractWidth(file));
        image.setHeight(metadataService.extractHeight(file));
        image.setFileSize(metadataService.extractSize(file));
        image.setDateTime(metadataService.extractDateTime(file));
        image.setMake(metadataService.extractCameraManufacturer(file));
        image.setModel(metadataService.extractCameraModelName(file));
        image.setLocation(metadataService.extractLocation(file));
        image.setPuid(metadataService.extrcatPronom(file));
        image.setLicenseType(licenseType);
        image.setNoOfAllowedUses(12); //TODO: get allowed uses input
        image.setPrice(new BigDecimal(222)); //TODO: get price input
        image.setTags(createTagSet(tags.trim().split("\\s*,\\s*")));

        try {
            imageRepository.save(image);
        } catch (JpaSystemException ex) {
            // TODO: If there was a problem to store data in the DB we have to delete image
            //  that was already stored in the upload directory.
            throw new DataStorageException("Sorry! Could not store data in the database! " +
                    "Make sure that all required fields are filled in and contain correct information!");
        }
    }
       
    /*
    Needs to be rewritten completely. This is a quickfix.
     */
    private Set<Tag> createTagSet(String[] inputTags) {
        /*
        Gets all tags from db that already exists.
         */
        Set<Tag> tagSet = tagRepository.getTagByString(inputTags);

        /*
        Tries to persist the new tag but if it already exists exception is thrown and tag not added to set.
         */
        for (String tag : inputTags) {
            Tag newTag = new Tag(tag);
            try {
                tagRepository.save(newTag);
                tagSet.add(newTag);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return tagSet;
    }
    
}
