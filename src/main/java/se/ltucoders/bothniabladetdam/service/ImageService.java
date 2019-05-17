package se.ltucoders.bothniabladetdam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.ltucoders.bothniabladetdam.db.ImageCopyRepository;
import se.ltucoders.bothniabladetdam.db.ImageRepository;
import se.ltucoders.bothniabladetdam.db.TagRepository;
import se.ltucoders.bothniabladetdam.db.UsersRepository;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.db.entity.ImageCopy;
import se.ltucoders.bothniabladetdam.db.entity.Tag;
import se.ltucoders.bothniabladetdam.exception.DataStorageException;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class ImageService {

    private final UsersRepository usersRepository;
    private final ImageRepository imageRepository;
    private final ImageCopyRepository imageCopyRepository;
    private final MetadataService metadataService;
    private final TagRepository tagRepository;
    private final FileStorageService fileStorageService;


    @Autowired
    public ImageService(UsersRepository usersRepository, ImageRepository imageRepository,
                        MetadataService metadataService, TagRepository tagRepository,
                        FileStorageService fileStorageService, ImageCopyRepository imageCopyRepository) {
        this.usersRepository = usersRepository;
        this.imageRepository = imageRepository;
        this.metadataService = metadataService;
        this.tagRepository = tagRepository;
        this.fileStorageService = fileStorageService;
        this.imageCopyRepository = imageCopyRepository;
    }


    public void createImage(String tags, String author, String licenseType,
                            String description, File file) {
        Image image = new Image();
        image.setAuthor(usersRepository.getUserByUsername(author));
        image.setFileName(file.getName());
        image.setFilePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")
                .path(StringUtils.cleanPath(file.getName()))
                .toUriString());
        image.setLicenseType(licenseType);
        image.setDescription(description);
        //setMetadata(image, file);
        metadataService.extractMetadata(image, file);
        image.setNoOfAllowedUses(12); //TODO: get allowed uses input
        image.setPrice(new BigDecimal(222)); //TODO: get price input
        image.setTags(createTagSet(tags.trim().split("\\s*,\\s*")));
        System.out.println(" ");
        try {
            imageRepository.save(image);
        } catch (JpaSystemException ex) {
            fileStorageService.deleteFile(file);
            throw new DataStorageException("Sorry! Could not store data in the database! " +
                    "Make sure that all required fields are filled in and contain correct information!");
        }
    }

    private Image createImageFromCopy(ImageCopy imageCopy, File file) {
        Image image = new Image();
        image.setAuthor(imageCopy.getImage().getAuthor());
        image.setFileName(imageCopy.getFileName());
        image.setFilePath(imageCopy.getFilePath());
        image.setLicenseType(imageCopy.getImage().getLicenseType());
        image.setDescription(imageCopy.getDescription());
        setMetadata(image, file);

        image.setNoOfAllowedUses(imageCopy.getImage().getNoOfAllowedUses());
        image.setPrice(imageCopy.getImage().getPrice());
        image.setTags(imageCopy.getImage().getTags());

        try {
            imageRepository.save(image);
        } catch (JpaSystemException ex) {
            fileStorageService.deleteFile(file);
            throw new DataStorageException("Sorry! Could not store data about modified image in the database! " +
                    "Make sure that all required fields are filled in and contain correct information! " +
                    "If this did not help, make sure that original picture has all required " +
                    "fields and contain correct information!");
        }
        return image;
    }

    public void createImageCopy(int originalImageID, String modifiedBy,
                                     String description, File file) {
        Image originalImage = imageRepository.findById(originalImageID);
        ImageCopy imageCopy = new ImageCopy();
        imageCopy.setFileName(file.getName());
        imageCopy.setFilePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/modified-copies/")
                .path(StringUtils.cleanPath(file.getName()))
                .toUriString());
        imageCopy.setModifiedBy(usersRepository.getUserByUsername(modifiedBy));
        imageCopy.setDateTime(LocalDateTime.now());
        imageCopy.setDescription(description);
        imageCopy.setImage(originalImage);
        imageCopy.setOriginalImageId(originalImage.getImageId()); //?
        imageCopy.setModifiedById(imageCopy.getModifiedBy().getUserId());
        imageCopy.setNewImage(createImageFromCopy(imageCopy, file));

        try {
            imageCopyRepository.save(imageCopy);
        } catch (JpaSystemException ex) {
            fileStorageService.deleteFile(file);
            throw new DataStorageException("Sorry! Could not store data about modified image as an image in the database! " +
                    "Make sure that all required fields are filled in and contain correct information! " +
                    "If this did not help, make sure that original picture has all required " +
                    "fields and contain correct information!");
        }
    }

    private void setMetadata(Image image, File file) {
        image.setResolution(metadataService.extractResolution(file));
        image.setWidth(metadataService.extractWidth(file));
        image.setHeight(metadataService.extractHeight(file));
        image.setFileSize(metadataService.extractSize(file));
        image.setDateTime(metadataService.extractDateTime(file));
        image.setMake(metadataService.extractCameraManufacturer(file));
        image.setModel(metadataService.extractCameraModelName(file));
        image.setLocation(metadataService.extractLocation(file));
        image.setPuid(metadataService.extrcatPronom(file));
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
