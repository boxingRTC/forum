package com.esliceu.forum.controller;

import com.esliceu.forum.dao.ReplyDAO;
import com.esliceu.forum.dto.CategoryDTO;
import com.esliceu.forum.dto.GetProfile;
import com.esliceu.forum.dto.ReplyDTO;
import com.esliceu.forum.dto.TopicDTO;
import com.esliceu.forum.form.ReplyForm;
import com.esliceu.forum.form.TopicForm;
import com.esliceu.forum.model.Category;
import com.esliceu.forum.model.Reply;
import com.esliceu.forum.model.Topic;
import com.esliceu.forum.model.User;
import com.esliceu.forum.service.ReplyService;
import com.esliceu.forum.service.TokenService;
import com.esliceu.forum.service.TopicService;
import com.esliceu.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @Autowired
    UserService userService;

    @Autowired
    TopicService topicService;


    @Autowired
    TokenService tokenService;


    @PostMapping("/topics/{id}/replies")
    @CrossOrigin
    public ReplyDTO createReply(@PathVariable String id, @RequestHeader("Authorization") String token, @RequestBody ReplyForm replyForm){
        User user = userService.getUserByEmail(tokenService.getUser(token.replace("Bearer ", "")));
        Reply reply = replyService.newReply(replyForm.getContent(), user, id);


        GetProfile getProfile = userService.createProfile(user);
        ReplyDTO replyDTO = replyService.createReplyDTO(replyForm, getProfile, reply, id);

        return replyDTO;
    }

    @PutMapping("/topics/{topicId}/replies/{replyId}")
    @CrossOrigin
    public ReplyDTO changeReply(@PathVariable("topicId") long topicId, @PathVariable("replyId") long replyId, @RequestHeader("Authorization") String token, @RequestBody ReplyForm replyForm){
        User user = userService.getUserByEmail(tokenService.getUser(token.replace("Bearer ", "")));

        GetProfile getProfile = userService.createProfile(user);

        replyService.changeReply(replyId, replyForm.getContent());
        Topic topic = topicService.getTopicById(String.valueOf(topicId));
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setTopic(String.valueOf(topicId));
        replyDTO.setContent(replyForm.getContent());
        replyDTO.setCreatedAt(topic.getCreatedAt()  + "");
        replyDTO.setUpdatedAt(Instant.now()+ "");
        replyDTO.setUser(getProfile);
        replyDTO.set__v(0);
        replyDTO.set_id(replyId + "");

        return replyDTO;
    }

    @DeleteMapping("/topics/{topicId}/replies/{replyId}")
    @CrossOrigin
    public boolean deleteReply(@PathVariable("topicId") long topicId, @PathVariable("replyId") long replyId, @RequestHeader("Authorization") String token){
        replyService.deleteReply(replyId);

        return true;
    }

}
