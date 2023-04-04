package com.esliceu.forum.service;

import com.esliceu.forum.dao.ReplyDAO;
import com.esliceu.forum.dto.GetProfile;
import com.esliceu.forum.dto.ReplyDTO;
import com.esliceu.forum.form.ReplyForm;
import com.esliceu.forum.model.Reply;
import com.esliceu.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class ReplyService {

    @Autowired
    TopicService topicService;

    @Autowired
    ReplyDAO replyDAO;

    public Reply newReply(String content, User u, String id) {
        Reply reply = new Reply(
                content,
                Instant.now(),
                Instant.now(),
                u,
                topicService.getTopicById(String.valueOf(id))
        );
        return replyDAO.save(reply);
    }

    public List<Reply> getRepliesByTopic(long id) {
        return replyDAO.findAllReplyByTopicId(id);
    }

    public void changeReply(long replyId, String content) {
        replyDAO.changeReply(content, Instant.now(), replyId);
    }

    public void deleteReply(long replyId) {
        replyDAO.deleteById(replyId);
    }

    public ReplyDTO createReplyDTO(ReplyForm replyForm, GetProfile getProfile, Reply reply, String id) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent(replyForm.getContent());
        replyDTO.setUser(getProfile);
        replyDTO.setCreatedAt(Instant.now() + "");
        replyDTO.setUpdatedAt(Instant.now() + "");
        replyDTO.set__v(0);
        replyDTO.set_id(String.valueOf(reply.getId()));
        replyDTO.setTopic(id);
        return replyDTO;
    }
}
