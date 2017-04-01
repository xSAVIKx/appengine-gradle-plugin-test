package com.test.application.domain.user;

import com.google.common.annotations.VisibleForTesting;
import com.google.protobuf.Message;
import com.test.application.domain.user.command.CreateUser;
import com.test.application.domain.user.event.UserCreated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spine3.server.aggregate.Aggregate;
import org.spine3.server.aggregate.AggregateRepository;
import org.spine3.server.aggregate.Apply;
import org.spine3.server.command.Assign;
import org.spine3.users.UserId;

public class UserAggregate extends Aggregate<UserId, User, User.Builder> {
    /**
     * Creates a new instance.
     * <p>
     * <p>Constructors of derived classes should have package access level
     * because of the following reasons:
     * <ol>
     * <li>These constructors are not public API of an application.
     * Commands and aggregate IDs are.
     * <li>These constructors need to be accessible from tests in the same package.
     * </ol>
     * <p>
     * <p>Because of the last reason consider annotating constructors with
     * {@code @VisibleForTesting}. The package access is needed only for tests.
     * Otherwise aggregate constructors (that are invoked by {@link AggregateRepository}
     * via Reflection) may be left {@code private}.
     *
     * @param id the ID for the new aggregate
     */
    @VisibleForTesting
    protected UserAggregate(final UserId id) {
        super(id);
    }

    private static Logger log() {
        return LogSingleton.INSTANCE.value;
    }

    @Assign
    public Message handle(final CreateUser c) {
        log().debug("Received create user command with user ID: {}", c.getUserId());
        final UserCreated userCreated = UserCreated.newBuilder()
                                                   .setUserId(c.getUserId())
                                                   .setTenant(c.getTenant())
                                                   .setUserAccount(c.getUserAccount())
                                                   .build();
        return userCreated;
    }

    @Apply
    private void handle(final UserCreated e) {
        log().debug("Handling UserCreated event with user ID: {}", e.getUserId());
        getBuilder().setTenant(e.getTenant())
                    .setUserAccount(e.getUserAccount());
    }

    private enum LogSingleton {
        INSTANCE;
        @SuppressWarnings("NonSerializableFieldInSerializableClass")
        private final Logger value = LoggerFactory.getLogger(UserAggregate.class);
    }
}
