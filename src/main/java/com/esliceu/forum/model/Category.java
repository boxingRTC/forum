package com.esliceu.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Arrays;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    long id;
    String slug;
    String title;
    String description;
    String color;
    String[] moderators;

    public Category(String slug, String title, String description, String color) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.color = color;
        this.moderators = null;
    }

    public Category(String title, String description, String color, String[] moderators) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.moderators = moderators;
    }

    public Category(long id, String slug, String title, String description, String color) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.color = color;
        this.moderators = null;
    }

    public Category(){

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "slug='" + slug + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", moderators=" + Arrays.toString(moderators) +
                '}';
    }
}
