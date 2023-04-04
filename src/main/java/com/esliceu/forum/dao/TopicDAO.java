package com.esliceu.forum.dao;

import com.esliceu.forum.model.Category;
import com.esliceu.forum.model.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
@Transactional
public interface TopicDAO extends JpaRepository<Topic, Long> {

    @Query( nativeQuery = true, value = "select topic.id,topic.content,topic.created_at,topic.title,topic.updated_at,topic.views,topic.category_id,topic.user_id from topic inner join category on topic.category_id = category.id where category.id = :id")
    List<Topic> findTopicsByCatId(long id);

    @Modifying
    @Query("update Topic t set t.title = :title, t.content = :content, t.category.id = :categoryid where t.id = :topicid")
    void updateTopic(@Param("title") String title, @Param("content")String content, @Param("categoryid") long categoryid, @Param("topicid") long topicid);

    @Modifying
    @Query("delete from Reply reply where reply.topic.id = :topicid")
    void deleteByTopicId(@Param("topicid") long parseLong);
}
