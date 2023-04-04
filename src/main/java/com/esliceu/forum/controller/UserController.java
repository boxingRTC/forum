package com.esliceu.forum.controller;

import com.esliceu.forum.dto.CategoryDTO;
import com.esliceu.forum.dto.GetProfile;
import com.esliceu.forum.dto.TopicDTO;
import com.esliceu.forum.form.*;
import com.esliceu.forum.model.Category;
import com.esliceu.forum.model.Topic;
import com.esliceu.forum.model.User;
import com.esliceu.forum.service.CategoryService;
import com.esliceu.forum.service.TokenService;
import com.esliceu.forum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/login")
    @CrossOrigin
    public Map<String, Object> login(@RequestBody LoginForm loginForm, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        User user = userService.login(loginForm);
        if (user == null){
            map.put("message", "Incorrect email or password.");
            response.setStatus(400);
            return map;
        }
        String token = tokenService.newToken(user.getEmail());

        GetProfile getProfile = new GetProfile();
        getProfile.setRole(user.getRole());
        getProfile.set_id(user.getId());
        getProfile.setEmail(user.getEmail());
        getProfile.setName(user.getName());
        getProfile.set__v(0);
        getProfile.setAvatarUrl("");
        getProfile.setId(user.getId());
        Map<String, Object> profMap = new HashMap<>();
        String[] permissions = {
                "categories:write",
                "categories:delete",
                "own_topics:write",
                "own_topics:delete",
                "own_replies:write",
                "own_replies:delete"
        };
        profMap.put("root", permissions);
        getProfile.setPermissions(profMap);

        map.put("token", token);
        map.put("user", getProfile);
        return map;
    }

    @PostMapping("/register")
    @CrossOrigin
    public Map<String, String> register(@RequestBody RegisterForm registerForm, HttpServletResponse response){
        Map<String, String> map = new HashMap<>();
        if (userService.newUser(registerForm)) map.put("message", "Usuario creado");
        else{
            map.put("message", "Ya existe un usuario con ese correo");
            response.setStatus(400);
        }
        return map;
    }

    @GetMapping("/getprofile")
    @CrossOrigin
    public Object getprofile(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        String email = (String) request.getAttribute("username");
        User user = userService.getUserByEmail(email);

        GetProfile getProfile = new GetProfile();
        getProfile.setRole(user.getRole());
        getProfile.set_id(user.getId());
        getProfile.setEmail(user.getEmail());
        getProfile.setName(user.getName());
        getProfile.set__v(0);
        getProfile.setAvatarUrl("");
        getProfile.setId(user.getId());
        Map<String, Object> profMap = new HashMap<>();
        String[] permissions = {
                "categories:write",
                "categories:delete",
                "own_topics:write",
                "own_topics:delete",
                "own_replies:write",
                "own_replies:delete"
        };
        profMap.put("root", permissions);
        getProfile.setPermissions(profMap);

        return getProfile;
    }

    @PutMapping("/profile/password")
    @CrossOrigin
    public Map<String, Object> updatePassword(HttpServletRequest request, HttpServletResponse response,
                                              @RequestHeader("Authorization") String token, @RequestBody PasswordForm passwordForm){
        User user = userService.getUserByEmail(tokenService.getUser(token.replace("Bearer ","")));

        boolean oldPassValidation = userService.validate(user.getEmail(), passwordForm.getCurrentPassword());
        Map<String, Object> map = new HashMap<>();

        if (passwordForm.getNewPassword().equals(passwordForm.getCurrentPassword())){
            map.put("message", "Tu contraseña es igual que la anterior");
            response.setStatus(400);
        }else if (oldPassValidation){
            userService.changePass(user, passwordForm.getNewPassword());
            map.put("message", "ok");
        }else {
            map.put("message", "contraseña actual incorrecta");
            response.setStatus(403);
        }

        return map;
    }

    @PutMapping("/profile")
    @CrossOrigin
    public Map<String, Object> updateProfile(HttpServletRequest request, HttpServletResponse response,
                                              @RequestHeader("Authorization") String token, @RequestBody ProfileForm profileForm){
        User user = userService.getUserByEmail(tokenService.getUser(token.replace("Bearer ","")));
        userService.updateProfile(user.getId(), profileForm);
        GetProfile getProfile = userService.createProfile(user);
        Map<String, Object> map = new HashMap<>();

        String[] permissions = {
                "categories:write",
                "categories:delete",
                "own_topics:write",
                "own_topics:delete",
                "own_replies:write",
                "own_replies:delete"
        };
        Map<String, Object> perms = new HashMap<>();
        perms.put("root", permissions);
        getProfile.setPermissions(perms);

        map.put("user", getProfile);
        token = tokenService.newToken(profileForm.getEmail());
        map.put("token", token);

        return map;
    }


}
