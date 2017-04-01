package com.test.application.domain.user;

import com.google.common.annotations.VisibleForTesting;
import org.spine3.server.BoundedContext;
import org.spine3.server.aggregate.AggregateRepository;
import org.spine3.users.UserId;

public class UserRepository extends AggregateRepository<UserId, UserAggregate> {
    /**
     * Creates a new repository instance.
     *
     * @param boundedContext the bounded context to which this repository belongs
     */
    @VisibleForTesting
    protected UserRepository(final BoundedContext boundedContext) {
        super(boundedContext);
    }
}
