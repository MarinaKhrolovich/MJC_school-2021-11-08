package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public TagDTO addTag(@Valid @RequestBody TagDTO tagDTO) {
        tagService.add(tagDTO);
        return tagDTO;
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable int id) {
        return tagService.get(id);
    }

    @GetMapping
    public List<TagDTO> getTags() {
        return tagService.get();
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id) {
        tagService.delete(id);
    }

}
