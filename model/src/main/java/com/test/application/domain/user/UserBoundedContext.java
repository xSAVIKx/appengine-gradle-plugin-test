package com.test.application.domain.user;

import com.google.appengine.api.utils.SystemProperty;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.common.base.Supplier;
import com.google.protobuf.GeneratedMessageV3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spine3.server.BoundedContext;
import org.spine3.server.CommandService;
import org.spine3.server.QueryService;
import org.spine3.server.entity.Repository;
import org.spine3.server.event.EventBus;
import org.spine3.server.storage.StorageFactory;
import org.spine3.server.storage.datastore.DatastoreStorageFactory;
import org.spine3.users.UserId;

public class UserBoundedContext {
    private static final String LOCAL_DATASTORE_HOST = "localhost:8081";
    private static final String LOCAL_DATASTORE_PROJECT_ID = "appengine-test";
    private static final String BOUNDED_CONTEXT_NAME = "USER_BOUNDED_CONTEXT";
    private static final String DEFAULT_NAMESPACE = "TEST_APPLICATION";
    private final BoundedContext boundedContext;
    private final CommandService commandService;
    private final QueryService queryService;

    private UserBoundedContext() {
        boundedContext = createBoundedContext();
        commandService = createCommandService(boundedContext);
        queryService = createQueryService(boundedContext);
    }

    private static QueryService createQueryService(final BoundedContext boundedContext) {
        final QueryService queryService = QueryService.newBuilder()
                                                      .add(boundedContext)
                                                      .build();
        return queryService;
    }

    private static CommandService createCommandService(final BoundedContext boundedContext) {
        final CommandService commandService = CommandService.newBuilder()
                                                            .add(boundedContext)
                                                            .build();
        return commandService;
    }

    private static BoundedContext createBoundedContext() {
        final DatastoreOptions datastoreOptions = datastoreOptions();
        final Datastore datastore = datastoreOptions.getService();
        final DatastoreStorageFactory gaeStorage = new DatastoreStorageFactory(datastore);
        final EventBus eventBus = EventBus.newBuilder()
                                          .setStorageFactory(gaeStorage)
                                          .build();
        final Supplier<StorageFactory> storageSupplier = supplier(gaeStorage);
        final BoundedContext boundedContext = BoundedContext.newBuilder()
                                                            .setName(BOUNDED_CONTEXT_NAME)
                                                            .setStorageFactorySupplier(storageSupplier)
                                                            .setEventBus(eventBus)
                                                            .build();
        registerAggregateRepositories(boundedContext);
        return boundedContext;
    }

    private static void registerAggregateRepositories(final BoundedContext boundedContext) {
        registerRepository(boundedContext, new UserRepository(boundedContext), UserId.getDefaultInstance());
    }

    private static Supplier<StorageFactory> supplier(final StorageFactory storageFactory) {
        final Supplier<StorageFactory> storageSupplier = new Supplier<StorageFactory>() {
            @Override
            public StorageFactory get() {
                return storageFactory;
            }
        };
        return storageSupplier;
    }

    private static DatastoreOptions datastoreOptions() {
        final DatastoreOptions.Builder datastoreOptions = DatastoreOptions.getDefaultInstance()
                                                                          .toBuilder()
                                                                          .setNamespace(DEFAULT_NAMESPACE);
        if (isRunningLocally()) {
            datastoreOptions.setHost(LOCAL_DATASTORE_HOST)
                            .setProjectId(LOCAL_DATASTORE_PROJECT_ID);
        }
        return datastoreOptions.build();
    }

    private static boolean isRunningLocally() {
        return SystemProperty.environment.value() != SystemProperty.Environment.Value.Production;
    }

    private static <ID extends GeneratedMessageV3> void registerRepository(final BoundedContext boundedContext,
                                                                           final Repository<ID, ?> repository,
                                                                           final ID id) {
        log().info("Registering {}", repository.getClass()
                                               .getSimpleName());
        boundedContext.register(repository);
        repository.load(id);
    }

    public static UserBoundedContext getInstance() {
        return UserBoundedContextSingleton.INSTANCE.value;
    }

    private static Logger log() {
        return LogSingleton.INSTANCE.value;
    }

    public BoundedContext getBoundedContext() {
        return boundedContext;
    }

    public CommandService getCommandService() {
        return commandService;
    }

    public QueryService getQueryService() {
        return queryService;
    }

    private enum UserBoundedContextSingleton {
        INSTANCE;
        @SuppressWarnings("NonSerializableFieldInSerializableClass")
        private final UserBoundedContext value = new UserBoundedContext();
    }

    private enum LogSingleton {
        INSTANCE;
        @SuppressWarnings("NonSerializableFieldInSerializableClass")
        private final Logger value = LoggerFactory.getLogger(UserBoundedContext.class);
    }
}
