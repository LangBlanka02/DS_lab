package com.utcn.demo.service;

import com.utcn.demo.model.Tag;
import com.utcn.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> retrieveTag() {
        return (List<Tag>) this.tagRepository.findAll();
    }

    public Tag retrieveTagById(Long id) {
        Optional<Tag> tag = this.tagRepository.findById(id);
        if (tag.isPresent()) {
            return tag.get();
        } else {
            return null;
        }
    }

    public Tag insertTag(Tag tag) {
        return this.tagRepository.save(tag);
    }


    public String deleteById(Long tagId) {
        try {
            this.tagRepository.deleteById(tagId);
            return ("Tag successfully deleted!");
        } catch (Exception e) {
            return "Failed to delete tag with id:" + tagId;
        }
    }
}