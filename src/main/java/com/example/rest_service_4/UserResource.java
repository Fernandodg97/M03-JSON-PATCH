package com.example.rest_service_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/api/v0/users";

    @Autowired
    UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userController.getUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) {
        UserDto u = userController.getUserById(id);
        return ResponseEntity.ok().body(u);
    }

    @PostMapping
    public ResponseEntity<UserDto> newUser(@RequestBody UserDto user) {
        UserDto u = userController.newUser(user);
        return ResponseEntity.ok().body(u);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto user) {
        UserDto u = userController.updateUser(id, user);
        return ResponseEntity.ok().body(u);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            UserDto u = userController.patchUser(id, patch);
            return ResponseEntity.ok().body(u);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        userController.remove(id);
        return ResponseEntity.ok().body(null);
    }
}
