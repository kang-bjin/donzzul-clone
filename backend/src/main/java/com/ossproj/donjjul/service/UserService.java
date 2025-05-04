package com.ossproj.donjjul.service;

import com.ossproj.donjjul.domain.User;
import com.ossproj.donjjul.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> login(String username, String rawPassword) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()));
    }

}
