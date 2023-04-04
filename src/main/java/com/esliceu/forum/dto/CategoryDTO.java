package com.esliceu.forum.dto;

import com.esliceu.forum.model.Category;
import com.esliceu.forum.utils.SHA256Encoder;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CategoryDTO {

    String slug;
    String title;
    String description;
    String color;
    String[] moderators;

    int __v;
    String _id;

    @JsonIgnore
    SHA256Encoder sha256Encoder;

    public CategoryDTO(){

    }

    public CategoryDTO(Category c) {
        this.slug = c.getSlug();
        this.title = c.getTitle();
        this.description = c.getDescription();
        this.color = c.getColor();
        this.moderators = new String[0];
        this.__v = 0;
        this._id = sha256Encoder.encode(c.getId() + "");


    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String[] getModerators() {
        return moderators;
    }

    public void setModerators(String[] moderators) {
        this.moderators = moderators;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public SHA256Encoder getSha256Encoder() {
        return sha256Encoder;
    }

    public void setSha256Encoder(SHA256Encoder sha256Encoder) {
        this.sha256Encoder = sha256Encoder;
    }
}
