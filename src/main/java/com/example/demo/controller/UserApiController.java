package com.example.demo.controller;

import com.example.demo.model.Header;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApiController {

    private final UserService userService;

    @PostMapping("")
    public Header<User> create(@RequestBody User requestUser) {
        User user = userService.create(requestUser);
        if (user != null) {
            return Header.OK(user);
        }
        return Header.ERROR();
    }

    @GetMapping("/{id}")
    public Header<User> read(@PathVariable(name = "id") Long id) {
        User user = userService.read(id);
        if (user != null) {
            return Header.OK(user);
        }
        return Header.ERROR();
    }

    @PutMapping("")
    public Header<User> update(@RequestBody User requestUser) {
        User user = userService.update(requestUser);
        if (user != null) {
            return Header.OK(user);
        }
        return Header.ERROR();
    }

    @DeleteMapping("/{id}")
    public Header delete(@PathVariable(name = "id") Long id) {
        boolean isDelete = userService.delete(id);
        if (isDelete) {
            return Header.OK();
        }
        return Header.ERROR();
    }
}
