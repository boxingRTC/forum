package com.esliceu.forum.service;

import com.esliceu.forum.dao.TopicDAO;
import com.esliceu.forum.model.Category;
import com.esliceu.forum.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TopicService {
    @Autowired
    CategoryService categoryService;
    @Autowired

    UserService userService;
    @Autowired
    TokenService tokenService;

    @Autowired
    TopicDAO topicdao;

    public Topic createTopic(String category, String title, String content, String bearer_) {
        Topic topic = new Topic();
        topic.setContent(content);
        topic.setCreatedAt(Instant.now());
        topic.setTitle(title);
        topic.setUpdatedAt(Instant.now());
        topic.setViews(0);
        topic.setCategory(categoryService.getCategory(category));
        topic.setUser(userService.getUserByEmail(tokenService.getUser(bearer_)));
        topicdao.save(topic);
        return topic;
    }

    public List<Topic> getTopicsByCategory(long id) {
        return topicdao.findTopicsByCatId(id);
    }

    public Topic getTopicById(String id) {
        return topicdao.findById(Long.parseLong(id)).get();
    }

    public void updateTopic(String id, String title, String content, long category) {
        topicdao.updateTopic(title, content, category, Long.parseLong(id));
    }

    public void deleteTopicById(String id) {
        //borrar replies
        topicdao.deleteByTopicId(Long.parseLong(id));

        //borrar topic
        topicdao.deleteById(Long.parseLong(id));

    }
}
