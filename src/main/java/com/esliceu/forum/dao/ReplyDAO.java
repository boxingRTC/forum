package com.esliceu.forum.dao;

import com.esliceu.forum.model.Reply;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

@Transactional
public interface ReplyDAO extends JpaRepository<Reply, Long> {

    List<Reply> findAllReplyByTopicId(long id);

    @Modifying
    @Query("update Reply r set r.content = :content, r.updatedAt = :updatedAt where r.id = :replyId")
    void changeReply(@Param("content") String content, @Param("updatedAt") Instant now, @Param("replyId")long replyId);
}
