package com.esliceu.forum.dto;

import com.esliceu.forum.utils.SHA256Encoder;

import java.util.Map;

public class GetProfile {

    String role;
    String id;
    String _id;
    String avatarUrl;
    String email;
    int __v;
    String name;
    Map<String, Object> permissions;

    SHA256Encoder sha256Encoder;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(long id){
        this.id = sha256Encoder.encode(id + "");
    }

    public String get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = sha256Encoder.encode(_id + "");
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Object> permissions) {
        this.permissions = permissions;
    }
}
