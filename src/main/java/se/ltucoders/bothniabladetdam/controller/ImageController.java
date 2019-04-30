package se.ltucoders.bothniabladetdam.controller;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.ltucoders.bothniabladetdam.db.ImageRepository;
import se.ltucoders.bothniabladetdam.db.entity.Image;
import se.ltucoders.bothniabladetdam.util.SearchCriteria;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @GetMapping
    public List<Image> search() {
        return imageRepository.findAll(); // Missing database on my machine
    }

    @GetMapping("/{imageId}")
    public Image search(@PathVariable int imageId) {
        return imageRepository.findById(imageId); // Missing database on my machine
    }

    @GetMapping(params = "searchCriteria")
    public List<Image> search(@RequestParam(value = "searchCriteria") SearchCriteria searchCriteria) {
        // TODO: Not implemented
        return new ArrayList<>();
    }







}

