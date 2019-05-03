package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.ltucoders.bothniabladetdam.db.ImageRepository;
import se.ltucoders.bothniabladetdam.db.UsersRepository;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class ImageAPIController {

    private FileStorageService fileStorageService;
    private FileController fileController;
    private ImageRepository imageRepository;
    private UsersRepository usersRepository;

    @Autowired
    public ImageAPIController(FileStorageService fileStorageService, FileController fileController,
                              ImageRepository imageRepository, UsersRepository usersRepository) {
        this.fileStorageService = fileStorageService;
        this.fileController = fileController;
        this.imageRepository = imageRepository;
        this.usersRepository = usersRepository;
    }

    // Controls names and content-types for files and store them in the repository
    @PostMapping("/images")
    public ResponseEntity uploadImages(@RequestParam("images") MultipartFile[] files,
                                       @RequestParam("tags") String tags,
                                       @RequestParam("author") String author,
                                       @RequestParam("licensetype") String licenseType) {
        for (MultipartFile file : files) {

            Image image;
//**********MetaDataService metadataExtractor;

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if (!fileController.controlName(fileName)) {
                return new ResponseEntity<>("Sorry! The file " + fileName +
                        " contains invalid character or " + "sequence of characters!",
                        HttpStatus.BAD_REQUEST);
            }
            if (!fileController.controlEmptySubmission(file)) {
                return new ResponseEntity<>("Sorry! You have to upload at least one image!",
                        HttpStatus.BAD_REQUEST);
            }
            if (!fileController.controlFileType(file.getContentType())) {
                return new ResponseEntity<>("Sorry! The file " + fileName +
                        " has wrong file format - " + file.getContentType() + "!",
                        HttpStatus.BAD_REQUEST);
            }
            if (!fileStorageService.storeFile(file)) {
                return new ResponseEntity<>("Could not store file " + fileName + ". Please try again!",
                        HttpStatus.BAD_REQUEST);
            }

            image = new Image();
//**********metadataExtractor = new MetaDataService(image, file);

//            image.setAuthor(usersRepository.getUserByUsername(author));
//            image.setFileName(fileName);
//            image.setFilePath(ServletUriComponentsBuilder.fromCurrentContextPath()
//                    .path("/image/")
//                    .path(fileName)
//                    .toUriString());
//            image.setLicenseType(licenseType);
//            imageRepository.save(image);

            //Test object
            image.setAuthor(usersRepository.getUserByUsername(author));
            image.setFileName(fileName);
            image.setFilePath(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/image/")
                    .path(fileName)
                    .toUriString());
            image.setLicenseType(licenseType);
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

            imageRepository.save(image);

        }

        if (files.length > 1) {
            return new ResponseEntity<>("All images have been saved!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The image has been saved!", HttpStatus.CREATED);
    }

    // Changes information for an image
    @PutMapping("/image")
    public ResponseEntity changeImageInformation(@RequestBody Image image) {
//        imageRepository.change(image);
        return new ResponseEntity<>("Image's information has been changed!", HttpStatus.OK);
    }

    // Changes information for an image
    @DeleteMapping("/image")
    public ResponseEntity deleteImage(@RequestBody Image image) {
//        imageRepository.delete(image);
        return new ResponseEntity<>("Image's information has been changed!", HttpStatus.OK);
    }

    //
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