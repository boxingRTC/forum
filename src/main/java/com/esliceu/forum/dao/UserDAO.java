package com.esliceu.forum.dao;

import com.esliceu.forum.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Transactional

public interface UserDAO extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);


    boolean existsByEmailAndPassword(String email, String encode);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePass(@Param("id") long id, @Param("password") String password);

    @Modifying
    @Query("update User u set u.name = :name, u.email = :email where u.id = :id")
    void updateProfile(@Param("id") long id,@Param("name") String name,@Param("email") String email);
}
