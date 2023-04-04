package com.esliceu.forum.controller;

import com.esliceu.forum.dao.CategoryDAO;
import com.esliceu.forum.dto.CategoryDTO;
import com.esliceu.forum.form.CategoryForm;
import com.esliceu.forum.form.LoginForm;
import com.esliceu.forum.model.Category;
import com.esliceu.forum.model.Reply;
import com.esliceu.forum.model.Topic;
import com.esliceu.forum.model.User;
import com.esliceu.forum.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicService topicService;

    @Autowired
    ReplyService replyService;

    @GetMapping("/categories")
    @CrossOrigin
    public List<Category> categories(){
        List<Category> categories = categoryService.getCategories();
        return categories;
    }

    @PostMapping("/categories")
    @CrossOrigin
    public CategoryDTO categories(@RequestBody CategoryForm categoryForm){
        Category c = categoryService.newCategory(categoryForm);
        CategoryDTO categoryDTO = new CategoryDTO(c);
        return categoryDTO;
    }

    @GetMapping("/categories/{slug}")
    @CrossOrigin
    public CategoryDTO seeCategory(@PathVariable String slug){
        Category c = categoryService.getCategory(slug);
        if (c == null) return null;
        return new CategoryDTO(c);
    }

    @DeleteMapping("/categories/{slug}")
    @CrossOrigin
    public void deleteCategory(@PathVariable String slug){
        Long catId = categoryService.getCategory(slug).getId();
        List<Topic> topics = topicService.getTopicsByCategory(catId);

        for (int i = 0; i < topics.size(); i++) {
            List<Reply> deleteReplies = replyService.getRepliesByTopic(topics.get(i).getId());
            for (int j = 0; j < deleteReplies.size(); j++) {
                replyService.deleteReply(deleteReplies.get(j).getId());
            }
            topicService.deleteTopicById(topics.get(i).getId() + "");
        }
       categoryService.deleteCategory(slug);
    }


    @PutMapping("/categories/{slug}")
    @CrossOrigin
    public void updateCategory(@PathVariable String slug, @RequestBody CategoryForm categoryForm){
        categoryService.updateCategory(categoryForm, slug);
    }
}
