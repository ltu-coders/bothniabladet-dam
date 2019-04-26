package se.ltucoders.bothniabladetdam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.ltucoders.bothniabladetdam.entity.Image;
import se.ltucoders.bothniabladetdam.service.FileStorageService;

@RestController
public class FileUploadController {

    private FileStorageService fileStorageService;
    private FileController fileController;

    @Autowired
    public FileUploadController(FileStorageService fileStorageService, FileController fileController) {
        this.fileStorageService = fileStorageService;
        this.fileController = fileController;
    }

    // Controls names and content-types for files and store them in the repository
    @PostMapping("/images")
    public ResponseEntity uploadMultipleFiles(@RequestParam("images") MultipartFile[] files) {
        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileController.controlName(fileName);
            fileController.controlEmptySubmission(file.getContentType());
            fileController.controlFileType(file.getContentType(), fileName);
            fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            Image image = new Image();
            image.setFileName(fileName);
            image.setFilePath(fileDownloadUri);
            //****HERE SHOULD BE AN OBJECT WITH METHOD THAT STORES IMAGE INFORMATION TO THE DB***//
        }

        if (files.length > 1) {
            return new ResponseEntity<>("All images have been uploaded!", HttpStatus.OK);
        }
        return new ResponseEntity<>("The image has been uploaded!", HttpStatus.OK);
    }
}
