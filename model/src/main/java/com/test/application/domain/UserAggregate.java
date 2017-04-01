package com.test.application.domain;

import com.google.common.annotations.VisibleForTesting;
import org.spine3.server.aggregate.Aggregate;
import org.spine3.server.aggregate.AggregateRepository;
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

}
