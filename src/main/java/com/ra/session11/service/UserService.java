package com.ra.session11.service;

import com.ra.session11.model.dto.UserLogin;
import com.ra.session11.model.dto.UserRegisterDTO;
import com.ra.session11.model.entity.User;
import com.ra.session11.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public boolean checkUsernameExisted(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    @Transactional
    public User login(UserLogin userLogin) {
        return userRepository.login(userLogin.getUsername(), userLogin.getPassword());
    }

    @Transactional
    public User register(UserRegisterDTO userRegisterDTO) {
        User user = User
                .builder()
                .email(userRegisterDTO.getEmail())
                .username(userRegisterDTO.getUsername())
                .password(userRegisterDTO.getPassword())
                .build();
        if (userRegisterDTO.getAvatar() != null && !userRegisterDTO.getAvatar().isEmpty()) {
            String urlImage = cloudinaryService.upload(userRegisterDTO.getAvatar());
            user.setAvatar(urlImage);
        }
        return userRepository.register(user);
    }
}
