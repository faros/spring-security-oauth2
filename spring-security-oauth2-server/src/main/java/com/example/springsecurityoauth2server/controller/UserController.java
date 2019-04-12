package com.example.springsecurityoauth2server.controller;

import com.example.springsecurityoauth2server.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/")
public class UserController {

    User user_1 = new User(1,"Jos Vermeulen", "Newtonstreet 5, 1000 Brussel","01/234.56.78" );
    User user_2 = new User(1,"Bram Vandeboomgaard", "HappyStreet 254, 9999 Bachtedekuppe","099/444.55.66" );

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public ResponseEntity getusers() {
        return new ResponseEntity(Arrays.asList(user_1, user_2), HttpStatus.OK);
    }

    @RequestMapping(value="/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable long userId) {
        return new ResponseEntity(user_1, HttpStatus.OK);
    }

    @RequestMapping(value="/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addUser(@RequestBody User user) {

    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable long userId,
                           @RequestBody User user) {
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable long userId) {
    }

}