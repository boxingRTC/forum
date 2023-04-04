package com.esliceu.forum.controller;

import com.esliceu.forum.dto.CategoryDTO;
import com.esliceu.forum.dto.GetProfile;
import com.esliceu.forum.dto.ReplyDTO;
import com.esliceu.forum.dto.TopicDTO;
import com.esliceu.forum.form.TopicForm;
import com.esliceu.forum.model.Category;
import com.esliceu.forum.model.Reply;
import com.esliceu.forum.model.Topic;
import com.esliceu.forum.model.User;
import com.esliceu.forum.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@RestController
public class TopicController {
    @Autowired
    TopicService topicService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ReplyService replyService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;


    @GetMapping("/categories/{slug}/topics")
    @CrossOrigin
    public List<TopicDTO> topics(@PathVariable String slug, @RequestHeader("Authorization") String token){
        Category c = categoryService.getCategory(slug);

        List<Topic> topics = topicService.getTopicsByCategory(c.getId());
        List<TopicDTO> topicDTOS = new ArrayList<>();

        for (int i = 0; i < topics.size(); i++) {
            String email = topics.get(i).getUser().getEmail();
            User user = userService.getUserByEmail(email);
            GetProfile getProfile = new GetProfile();
            getProfile.setId(user.getId());
            getProfile.setRole(user.getRole());
            getProfile.set_id(user.getId());
            getProfile.setEmail(user.getEmail());
            getProfile.setName(user.getName());
            getProfile.set__v(0);
            getProfile.setAvatarUrl("");
            TopicDTO dto = new TopicDTO();
            dto.setUser(getProfile);
            dto.setId(topics.get(i).getId() + "");
            dto.setCreatedAt(topics.get(i).getCreatedAt() + "");
            dto.setUpdatedAt(topics.get(i).getUpdatedAt() + "");
            dto.set__v(0);
            dto.setReplies(null);
            dto.setNumberOfReplies(replyService.getRepliesByTopic(topics.get(i).getId()).size());
            dto.setTitle(topics.get(i).getTitle());
            dto.set_id(topics.get(i).getId() + "");
            dto.setContent(topics.get(i).getContent());

            topicDTOS.add(dto);
        }
        return topicDTOS;
    }


    @GetMapping("/topics/{id}")
    @CrossOrigin
    public TopicDTO topic(@PathVariable String id, @RequestHeader("Authorization") String token){
        Topic topic = topicService.getTopicById(id);
        Category c = topic.getCategory();
        CategoryDTO categoryDTO = new CategoryDTO(c);
        String email = topic.getUser().getEmail();
        User user = userService.getUserByEmail(email);

        GetProfile getProfile = new GetProfile();
        getProfile.setId(user.getId());
        getProfile.setRole(user.getRole());
        getProfile.set_id(user.getId());
        getProfile.setEmail(user.getEmail());
        getProfile.setName(user.getName());
        getProfile.set__v(0);
        getProfile.setAvatarUrl("");

        List<Reply> replies = replyService.getRepliesByTopic(topic.getId());
        List<ReplyDTO> replyDTOS = new ArrayList<>();
        for (int i = 0; i < replies.size(); i++) {
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setTopic(String.valueOf(topic.getId()));
            replyDTO.setContent(replies.get(i).getContent());
            replyDTO.setCreatedAt(topic.getCreatedAt()  + "");
            replyDTO.setUpdatedAt(Instant.now()+ "");
            replyDTO.setUser(getProfile);
            replyDTO.set__v(0);
            replyDTO.set_id(replies.get(i).getId() + "");
            replyDTOS.add(replyDTO);
        }

        TopicDTO dto = new TopicDTO();
        dto.setUser(getProfile);
        dto.setId(topic.getId() + "");
        dto.setCreatedAt(topic.getCreatedAt() + "");
        dto.setUpdatedAt(topic.getUpdatedAt() + "");
        dto.set__v(0);
        dto.setReplies(replyDTOS);
        dto.setNumberOfReplies(replies.size());
        dto.setTitle(topic.getTitle());
        dto.set_id(topic.getId() + "");
        dto.setContent(topic.getContent());
        dto.setCategory(categoryDTO);

        return dto;
    }




    @PostMapping("/topics")
    @CrossOrigin
    public Map<String, Object> createTopics(@RequestHeader("Authorization") String token, @RequestBody TopicForm topicDTO){
        Map<String, Object> map = new HashMap<>();
        Topic topic = topicService.createTopic(topicDTO.getCategory(),topicDTO.getTitle(),topicDTO.getContent(),
                token.replace("Bearer ",""));

        map.put("views",0);
        map.put("_id",topic.getId());
        long userId = userService.getUserByEmail(tokenService.getUser(token.replace("Bearer ",""))).getId();
        map.put("title",topicDTO.getTitle());
        map.put("content",topicDTO.getContent());
        map.put("category",categoryService.getCategory(topicDTO.getCategory()).getId());
        map.put("user",userId);
        map.put("replies",null);
        map.put("createdAt", topic.getCreatedAt());
        map.put("updatedAt",topic.getUpdatedAt());
        map.put("__v",0);


        map.put("numberOfReplies",0);
        map.put("id",topic.getId());

        return map;
    }


    @PutMapping("/topics/{id}")
    @CrossOrigin
    public TopicDTO updateTopic(@PathVariable String id, @RequestBody TopicForm topicForm){
        Topic topic = topicService.getTopicById(id);

        topicService.updateTopic(id, topicForm.getTitle(), topicForm.getContent(), categoryService.getCategory(topicForm.getCategory()).getId());
        Category c = topic.getCategory();
        CategoryDTO categoryDTO = new CategoryDTO(c);

        String email = topic.getUser().getEmail();
        User user = userService.getUserByEmail(email);
        GetProfile getProfile = new GetProfile();
        getProfile.setId(user.getId());
        getProfile.setRole(user.getRole());
        getProfile.set_id(user.getId());
        getProfile.setEmail(user.getEmail());
        getProfile.setName(user.getName());
        getProfile.set__v(0);
        getProfile.setAvatarUrl("");

        TopicDTO dto = new TopicDTO();


        dto.setUser(getProfile);
        dto.setId(topic.getId() + "");
        dto.setCreatedAt(topic.getCreatedAt() + "");
        dto.setUpdatedAt(topic.getUpdatedAt() + "");
        dto.set__v(0);
        dto.setReplies(null);
        dto.setNumberOfReplies(0);
        dto.setTitle(topic.getTitle());
        dto.set_id(topic.getId() + "");
        dto.setContent(topic.getContent());
        dto.setCategory(categoryDTO);

        return dto;
    }


    @DeleteMapping("/topics/{id}")
    @CrossOrigin
    public Map<String, String> deleteTopics(@RequestHeader("Authorization") String token, @PathVariable String id, HttpServletResponse response){
        Map<String,String> map = new HashMap<>();
        Topic topic = topicService.getTopicById(id);
        long userTopic = topic.getUser().getId();
        long userId = userService.getUserByEmail(tokenService.getUser(token.replace("Bearer ",""))).getId();

        System.out.println(userTopic);
        System.out.println(userId);
        if (userTopic == userId){
            topicService.deleteTopicById(id);
            map.put("message", "Topic borrado correctamente");
        }else {
            map.put("message", "No tienes permiso para borrar el topic");
            response.setStatus(403);
        }
        return map;
    }



}
