package com.esliceu.forum.service;

import com.esliceu.forum.dao.UserDAO;
import com.esliceu.forum.dto.GetProfile;
import com.esliceu.forum.form.LoginForm;
import com.esliceu.forum.form.ProfileForm;
import com.esliceu.forum.form.RegisterForm;
import com.esliceu.forum.model.User;
import com.esliceu.forum.utils.SHA256Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userdao;
    SHA256Encoder encoder;
    public boolean newUser(RegisterForm registerForm){
        User user = new User(
                registerForm.getName(),
                registerForm.getEmail(),
                encoder.encode(registerForm.getPassword()),
                registerForm.getRole()
        );
        //si ya existe un usuario con ese email no lo crea
        if (!userdao.findByEmail(user.getEmail()).isEmpty()) return false;
        //crea el usuario
        userdao.save(user);
        return true;
    }


    public User login(LoginForm loginForm) {
        if (userdao.findByEmail(loginForm.getEmail()).isEmpty()) return null;
        User u = userdao.findByEmail(loginForm.getEmail()).get(0);
        System.out.println(u.toString());
        String enPass = encoder.encode(loginForm.getPassword());
        System.out.println("enpass" + enPass);
        if (u.getPassword().equals(enPass)) return u;
        else return null;
    }

    public User getUserByEmail(String email){
        List<User> users = userdao.findByEmail(email);
        if (users.isEmpty()) return null;
        return users.get(0);
    }

    public GetProfile createProfile(User user) {
        GetProfile getProfile = new GetProfile();
        getProfile.setId(user.getId());
        getProfile.setRole(user.getRole());
        getProfile.set_id(user.getId());
        getProfile.setEmail(user.getEmail());
        getProfile.setName(user.getName());
        getProfile.set__v(0);
        getProfile.setAvatarUrl("");
        return getProfile;
    }

    public boolean validate(String email, String currentPassword) {
        return userdao.existsByEmailAndPassword(email, encoder.encode(currentPassword));
    }

    public void changePass(User user, String newPassword) {
        userdao.updatePass(user.getId(), encoder.encode(newPassword));
    }

    public void updateProfile(Long id, ProfileForm profileForm) {
        userdao.updateProfile(id, profileForm.getName(), profileForm.getEmail());
    }
}
