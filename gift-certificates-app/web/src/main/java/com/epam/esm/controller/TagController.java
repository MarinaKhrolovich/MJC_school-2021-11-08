package com.epam.esm.controller;

import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Validated
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public TagDTO addTag(@Valid @RequestBody TagDTO tagDTO) {
        TagDTO addedTag = tagService.add(tagDTO);
        int id = addedTag.getId();
        addedTag.add(linkTo(methodOn(TagController.class).getTag(id)).withSelfRel());
        return addedTag;
    }

    @GetMapping("/{id}")
    public TagDTO getTag(@PathVariable @Min(1) int id) {
        TagDTO tagDTO = tagService.get(id);
        tagDTO.add(linkTo(methodOn(TagController.class).getTag(id)).withSelfRel());
        return tagDTO;
    }

    @GetMapping
    public List<TagDTO> getTags(PageDTO pageDTO) {
        List<TagDTO> tagDTOS = tagService.get(pageDTO);
        if (!CollectionUtils.isEmpty(tagDTOS)) {
            tagDTOS.forEach(tagDTO -> {
                int id = tagDTO.getId();
                tagDTO.add(linkTo(methodOn(TagController.class).getTag(id)).withSelfRel());
            });
        }
        return tagDTOS;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTag(@PathVariable @Min(1) int id) {
        tagService.delete(id);
    }

    @GetMapping("/most-popular")
    public TagDTO getTag() {
        return tagService.getMostPopular();
    }

}
