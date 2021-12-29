package com.epam.esm.controller;

import com.epam.esm.bean.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Tag addTag(@RequestBody Tag tag) {
        tagService.add(tag);
        return tag;
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable int id) {
        return tagService.get(id);
    }

    @GetMapping
    public List<Tag> getTags() {
        return tagService.get();
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable int id) {
        tagService.delete(id);
    }

}
