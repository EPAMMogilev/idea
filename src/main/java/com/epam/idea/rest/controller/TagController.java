package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Tag;
import com.epam.idea.core.service.TagService;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api/v1/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
    public HttpEntity<TagResource> showTag(@PathVariable final long tagId) {
        Tag foundTag = tagService.findOne(tagId);
        TagResource tagResource = new TagResourceAsm().toResource(foundTag);
        return new ResponseEntity<>(tagResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<List<TagResource>> showAllTags() {
        List<Tag> foundTags = tagService.findAll();
        List<TagResource> tagResources = new TagResourceAsm().toResources(foundTags);
        return new ResponseEntity<> (tagResources , HttpStatus.OK);
    }
}
