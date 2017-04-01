package com.test.application.controller;

import com.test.application.domain.user.UserBoundedContext;
import org.spine3.users.UserId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
public class TestController {

    private UserBoundedContext userBoundedContext;

    @RequestMapping(method = {RequestMethod.GET}, path = "/user/create")
    public UserId createUser() {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public String getUsers() {
        return "Hello, World";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String helloWorld() {
        return "Hello, World";
    }

    @Inject
    public void setUserBoundedContext(final UserBoundedContext userBoundedContext) {

    }
}
