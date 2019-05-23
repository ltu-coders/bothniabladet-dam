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
import se.ltucoders.bothniabladetdam.exception.FileNotFoundException;
import se.ltucoders.bothniabladetdam.service.DataParsingService;
import se.ltucoders.bothniabladetdam.service.FileStorageService;
import se.ltucoders.bothniabladetdam.service.ImageService;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.File;

@RestController
public class ImageAPIController {

    private final FileStorageService fileStorageService;
    private final FileController fileController;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final DataParsingService dataParsingService;

    @Autowired
    public ImageAPIController(FileStorageService fileStorageService, FileController fileController,
                              ImageRepository imageRepository, ImageService imageService,
                              DataParsingService dataParsingService) {
        this.fileStorageService = fileStorageService;
        this.fileController = fileController;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
        this.dataParsingService = dataParsingService;
    }

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
        searchCriteria.setTags(dataParsingService.getTagArray(tags));
        searchCriteria.setLicenseType(licenseType);
        searchCriteria.setAuthor(author);
        searchCriteria.setResolution(resolution);
        searchCriteria.setMaxWidth(dataParsingService.parseNumber(maxWidth));
        searchCriteria.setMinWidth(dataParsingService.parseNumber(minWidth));
        searchCriteria.setMaxHeight(dataParsingService.parseNumber(maxHeight));
        searchCriteria.setMinHeight(dataParsingService.parseNumber(minHeight));
        searchCriteria.setFromDate(dataParsingService.parseDateTime(fromDate));
        searchCriteria.setToDate(dataParsingService.parseDateTime(toDate));
        searchCriteria.setEditor(editor);
        searchCriteria.setLastModified(lastModified);
        searchCriteria.setMake(make);
        searchCriteria.setModel(model);

        return imageRepository.search(searchCriteria);
    }

    // Gets specific image from the database
    @GetMapping("images/{imageId:\\d+}")
    public Image search(@PathVariable int imageId) {
        return imageRepository.findById(imageId);
    }

    // Controls names and content-types for files and store them in the repository
    @PostMapping("/images")
    public ResponseEntity uploadImages(@RequestParam("images") MultipartFile[] files,
                                       @RequestParam("tags") String tags,
                                       @RequestParam("author") String author,
                                       @RequestParam("licensetype") String licenseType,
                                       @RequestParam("description") String description) {

        fileController.validateFiles(files);
        for (MultipartFile file : files) {
            File filePath = fileStorageService.storeFile(file);
            imageService.createImage(tags, author, licenseType, description, filePath);
        }

        if (files.length > 1) {
            return new ResponseEntity<>("All images have been saved!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The image has been saved!", HttpStatus.CREATED);
    }

    // Changes information for an image
    @PutMapping("/images")
    public ResponseEntity changeImageInformation(@RequestBody Image image) {
        // TODO: Control users input and then call change method
        //  imageRepository.change(image);
        return new ResponseEntity<>("Image's information has been changed!", HttpStatus.OK);
    }

    // Changes information for an image
    @DeleteMapping("/images/{imageId}")
    public ResponseEntity deleteImage(@PathVariable int imageId) {
        // TODO:
        //  imageRepository.delete(imageId);
        return new ResponseEntity<>("The image information was deleted!", HttpStatus.OK);
    }

    // Returns image
    @GetMapping("/images/{fileName:.+[.].+}")
    public ResponseEntity downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(request.getServletContext()
                            .getMimeType(resource.getFile()
                                    .getAbsolutePath())))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            throw new FileNotFoundException("File " + fileName + " not found!" );
        }
    }

    // Store image copy
    @PostMapping("/images/{imageId}/modified-copies")
    public ResponseEntity uploadModifiedCopy(@RequestParam("images") MultipartFile multipartFile,
                                        @PathVariable int imageId,
                                        @RequestParam("modifiedBy") String modifiedBy,
                                        @RequestParam("description") String description) {

        fileController.validateFile(multipartFile);
        File file = fileStorageService.storeFile(multipartFile);
        imageService.createImageCopy(imageId, modifiedBy, description, file);

        return new ResponseEntity<>("The modified copy of the image has been saved!",
                HttpStatus.OK);
    }
}