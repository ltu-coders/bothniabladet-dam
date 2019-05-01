package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.service.FileStorageService;

@RestController
public class ImageAPIController {

    private FileStorageService fileStorageService;
    private FileController fileController;

    @Autowired
    public ImageAPIController(FileStorageService fileStorageService, FileController fileController) {
        this.fileStorageService = fileStorageService;
        this.fileController = fileController;
    }

    // Controls names and content-types for files and store them in the repository
    @PostMapping("/images")
    public ResponseEntity uploadImages(@RequestParam("images") MultipartFile[] files,
                                       @RequestParam("tags") String tags,
                                       @RequestParam("author") String author,
                                       @RequestParam("licensetype") String licenseType) {
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            if (!fileController.controlName(fileName)) {
                return new ResponseEntity<>("Sorry! The file " + fileName +
                        " contains invalid character or " + "sequence of characters!",
                        HttpStatus.BAD_REQUEST);
            }
            if (!fileController.controlEmptySubmission(file.getContentType())) {
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

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            Image image = new Image();

//***********Users user = SomeObjectToTheDB.getAuthorByName(author);
//***********image.setAuthor(user);

            image.setFileName(fileName);
            image.setFilePath(fileDownloadUri);
            image.setLicenseType(licenseType);

            //****HERE SHOULD BE AN OBJECT WITH METHOD THAT STORES IMAGE INFORMATION TO THE DB***//
        }

        if (files.length > 1) {
            return new ResponseEntity<>("All images have been uploaded!", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("The image has been uploaded!", HttpStatus.CREATED);
    }

    // Changes information for an image
    @PutMapping("/image")
    public ResponseEntity test(@RequestBody Image image) {
//******SomeObjectToTheDB someObjectToTheDB = new SomeObjectToTheDB().getAuthorByName(author);
//******someObjectToTheDB.changeImage(image);
        return new ResponseEntity<>("Information about image has been changed!", HttpStatus.OK);
    }
}