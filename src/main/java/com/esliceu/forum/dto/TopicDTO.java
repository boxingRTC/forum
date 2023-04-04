package com.esliceu.forum.dto;

import com.esliceu.forum.model.Reply;
import com.esliceu.forum.model.User;

import java.util.List;

public class TopicDTO {
    int views;
    String _id;
    String title;
    String content;
    CategoryDTO category;
    String id;
    String createdAt;
    int numberOfReplies;
    List<ReplyDTO> replies;
    String updatedAt;
    GetProfile user;
    int __v = 0;


    public TopicDTO(){

    }



    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getNumberOfReplies() {
        return numberOfReplies;
    }

    public void setNumberOfReplies(int numberOfReplies) {
        this.numberOfReplies = numberOfReplies;
    }

    public List<ReplyDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyDTO> replies) {
        this.replies = replies;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public GetProfile getUser() {
        return user;
    }

    public void setUser(GetProfile user) {
        this.user = user;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

}
