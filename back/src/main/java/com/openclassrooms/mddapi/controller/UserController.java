package com.openclassrooms.mddapi.controller;


import com.openclassrooms.mddapi.domain.dto.UserDto;
import com.openclassrooms.mddapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }


    @GetMapping("{id}/subscriptions")
    public ResponseEntity<?> getSubscriptions(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getSubscriptions(id));
    }

    @PutMapping("{id}/subscribe/{idTopic}")
    public ResponseEntity<?> subscribe(@PathVariable("id") Long id, @PathVariable("idTopic") Long idTopic){
        userService.subscribe(id,idTopic);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/subscribe/{idTopic}")
    public ResponseEntity<?> unSubscribe(@PathVariable("id") Long id, @PathVariable("idTopic") Long idTopic){
        userService.unSubscribe(id,idTopic);
        return ResponseEntity.ok().build();
    }
}
