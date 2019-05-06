package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.ltucoders.bothniabladetdam.db.ImageRepository;
import se.ltucoders.bothniabladetdam.db.SearchCriteria;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.service.FileStorageService;
import se.ltucoders.bothniabladetdam.service.ImageService;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class ImageAPIController {

    private final FileStorageService fileStorageService;
    private final FileController fileController;
    private final ImageRepository imageRepository;
    private final ImageService imageService;


    @Autowired
    public ImageAPIController(FileStorageService fileStorageService, FileController fileController,
                              ImageRepository imageRepository, ImageService imageService) {
        this.fileStorageService = fileStorageService;
        this.fileController = fileController;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    // TODO: Are we going to need this method?
    // Gets alla available images form the database
//    @GetMapping("/images")
//    public List<Image> search() {
//        return imageRepository.findAll();
//    }

    // Get all images that match the specified criteria. Each criteria is optional
    @GetMapping(value = "/images")
    public List<Image> search(@RequestParam(value = "tags", required = false) String tags,
                              @RequestParam(value = "licenseType", required = false) String licenseType,
                              @RequestParam(value = "author", required = false) String author,
                              @RequestParam(value = "resolution", required = false) String resolution,
                              @RequestParam(value = "maxWidth", required = false) String maxWidth,
                              @RequestParam(value = "minWidth", required = false) String minWidth,
                              @RequestParam(value = "maxHeight", required = false) String maxHeight,
                              @RequestParam(value = "minHeight", required = false) String minHeight,
                              @RequestParam(value = "fromDate", required = false) String fromDate,
                              @RequestParam(value = "toDate", required = false) String toDate,
                              @RequestParam(value = "editor", required = false) String editor,
                              @RequestParam(value = "lastModified", required = false) String lastModified,
                              @RequestParam(value = "make", required = false) String make,
                              @RequestParam(value = "model", required = false) String model) {

        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setTags(getTagArray(tags));
        searchCriteria.setLicenseType(licenseType);
        searchCriteria.setAuthor(author);
        searchCriteria.setResolution(resolution);
        searchCriteria.setMaxWidth(parseStringNumber(maxWidth));
        searchCriteria.setMinWidth(parseStringNumber(minWidth));
        searchCriteria.setMaxHeight(parseStringNumber(maxHeight));
        searchCriteria.setMinHeight(parseStringNumber(minHeight));
        searchCriteria.setFromDate(parseStringDate(fromDate));
        searchCriteria.setToDate(parseStringDate(toDate));
        searchCriteria.setEditor(editor);
        searchCriteria.setLastModified(lastModified);
        searchCriteria.setMake(make);
        searchCriteria.setModel(model);

        return imageRepository.search(searchCriteria);
    }


    // Converts string with search tags to array with tags. Uses comma as tag separator.
    private String[] getTagArray(String tagString) {
        String[] tags;
        if (tagString != null) {
            tags = tagString.split(",");
            for (int i = 0; i < tags.length; i++) {
                tags[i] = tags[i].trim().replaceAll("[^ 0-9a-zåäöA-ZÅÄÖ]", "");
            }
            return tags;
        }
        return new String[0];
    }


    // Converts string date (format: 2018-09-22) to LocalDateTime.
    private LocalDateTime parseStringDate(String dateString) {
        LocalDateTime date = null;
        try {
            if (dateString != null) {
                // Makes HH:mm:ss and adds default values
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .appendPattern("yyyy-MM-dd[HH:mm:ss]")
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();
                date = LocalDateTime.parse(dateString, formatter);
                System.out.println("The date is: " + date);
            }
        } catch (DateTimeParseException ex) {
            System.out.println("The date can not be parsed. Enter valid date!");
        }
        return date;
    }


    // TODO: not a good idea to change user input. Better to use String
    // Converts string number to integer.
    private int parseStringNumber(String stringNumber) {
        int number;
        try {
            number = Integer.parseInt(stringNumber);
        } catch (NumberFormatException ex) {
            number = 0;
        }
        return number;
    }

    // TODO: In which situations do we use this method? Do we need it?
    // Gets specific image from the database
    @GetMapping("images/{imageId}")
    public Image search(@PathVariable int imageId) {
        return imageRepository.findById(imageId);
    }

    // Controls names and content-types for files and store them in the repository
    @PostMapping("/images")
    public ResponseEntity uploadImages(@RequestParam("images") MultipartFile[] files,
                                       @RequestParam("tags") String tags,
                                       @RequestParam("author") String author,
                                       @RequestParam("licensetype") String licenseType) {
        for (MultipartFile file : files) {
            fileController.validateFile(file);
            fileStorageService.storeFile(file);
            imageService.createImage(tags, author, licenseType, file);
        }

        if (files.length > 1) {
            return new ResponseEntity<>("All images have been saved!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The image has been saved!", HttpStatus.CREATED);
    }

    // Changes information for an image
    @PutMapping("/image")
    public ResponseEntity changeImageInformation(@RequestBody Image image) {
        // TODO: Control users input and then call change method
        //  imageRepository.change(image);
        return new ResponseEntity<>("Image's information has been changed!", HttpStatus.OK);
    }

    // Changes information for an image
    @DeleteMapping("/image/{imageId}")
    public ResponseEntity deleteImage(@PathVariable int imageId) {
        // TODO: Write method that deletes file
        //  imageRepository.delete(imageId);
        return new ResponseEntity<>("The image information was deleted!", HttpStatus.OK);
    }

    // Returns image
    @GetMapping("/image/{fileName:.+}")
    public ResponseEntity downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(request.getServletContext()
                        .getMimeType(resource.getFile()
                        .getAbsolutePath())))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}