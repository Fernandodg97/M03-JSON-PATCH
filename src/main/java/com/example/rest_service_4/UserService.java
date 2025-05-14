package com.example.rest_service_4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDAO userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserbyId(Integer id) {
        Optional<User> op = userRepository.findById(id);
        if(op.isPresent()) return op.get();
        else return null;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Integer id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public User patchUser(Integer id, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
            User patchedUser = objectMapper.treeToValue(patched, User.class);
            patchedUser.setId(id);
            return userRepository.save(patchedUser);
        }
        return null;
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
