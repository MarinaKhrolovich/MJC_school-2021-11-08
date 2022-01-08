package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/tags")
@Validated
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public TagDTO addTag(@Valid @RequestBody TagDTO tagDTO) {
        return tagService.add(tagDTO);
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable @Min(1) int id) {
        return tagService.get(id);
    }

    @GetMapping
    public List<TagDTO> getTags() {
        return tagService.get();
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable @Min(1) int id) {
        tagService.delete(id);
    }

}
