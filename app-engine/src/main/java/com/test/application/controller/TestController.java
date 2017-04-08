package com.test.application.controller;

import com.test.application.domain.user.UserBoundedContext;
import com.test.application.domain.user.command.CreateUser;
import io.grpc.stub.StreamObserver;
import org.spine3.base.Command;
import org.spine3.base.CommandContext;
import org.spine3.base.Commands;
import org.spine3.base.Response;
import org.spine3.people.PersonName;
import org.spine3.time.ZoneOffset;
import org.spine3.users.TenantId;
import org.spine3.users.UserAccount;
import org.spine3.users.UserId;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Random;

@RestController
public class TestController {

    private UserBoundedContext userBoundedContext;

    @RequestMapping(method = {RequestMethod.GET}, path = "/user/create")
    public UserId createUser() {
        UserId userId = UserId.newBuilder()
                              .setValue("newUser" + new Random().nextLong())
                              .build();
        PersonName personName = PersonName.newBuilder()
                                          .setGivenName("Valeera")
                                          .setFamilyName("OOlldd")
                                          .build();
        UserAccount userAccount = UserAccount.newBuilder()
                                             .setUserId(userId)
                                             .setName(personName)
                                             .build();
        CreateUser createUser = CreateUser.newBuilder()
                                          .setUserId(userId)
                                          .setUserAccount(userAccount)
                                          .build();
        CommandContext commandContext = Commands.createContext(TenantId.getDefaultInstance(), UserId.getDefaultInstance(), ZoneOffset.getDefaultInstance());
        Command command = Commands.createCommand(createUser, commandContext);
        userBoundedContext.getCommandService()
                          .post(command, observer());
        return userId;
    }

    private StreamObserver<Response> observer() {
        return new StreamObserver<Response>() {
            @Override
            public void onNext(Response value) {
                System.out.println("next");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error");
            }

            @Override
            public void onCompleted() {
                System.out.println("complete");
            }
        };
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
        this.userBoundedContext = userBoundedContext;
    }
}
