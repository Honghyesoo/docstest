package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User create(User user){
        user.setCreateAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User read(Long id){
        return userRepository.findById(id)
                .orElse(null);
    }
    public  User update(User requesrUser){
        Optional<User> optionalUser = userRepository.findById(requesrUser.getId());

        return optionalUser.map(dbUser -> {
            dbUser.setAccount(requesrUser.getAccount());
            dbUser.setEmail(requesrUser.getEmail());
            dbUser.setPhoneNumber(requesrUser.getPhoneNumber());
            dbUser.setUpdateAt(LocalDateTime.now());
            return dbUser;
        })
                .map(userRepository::save)
                .orElse(null);
    }
    public boolean delete(Long id){
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return true;
                }).orElse(false);
    }

}
