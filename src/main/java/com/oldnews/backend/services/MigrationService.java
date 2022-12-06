package com.oldnews.backend.services;

import com.oldnews.backend.common.migrations.Migration;
import com.oldnews.backend.common.repositories.MigrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class MigrationService {

    @Value("${app.run.migrations: false}")
    private boolean migrate;

    private final Logger log =
            LoggerFactory.getLogger(MigrationService.class);

    private final List<Migration> migrations;

    private final MigrationRepository migrationRepository;

    public MigrationService(
            List<Migration> migrations,
            MigrationRepository migrationRepository
    ) {
        this.migrations = migrations;
        this.migrationRepository = migrationRepository;
    }

    private com.oldnews.backend.common.models.Migration createMigration(int number, String description){
        com.oldnews.backend.common.models.Migration migration = new com.oldnews.backend.common.models.Migration();

        migration.setId(number);
        migration.setDescription(description);

        return migrationRepository.save(migration);
    }

    private Mono<Migration> runMigration(Migration migration){
        return Mono.defer(() -> {
            com.oldnews.backend.common.models.Migration existingMigration =
                    migrationRepository
                            .findById(migration.getMigrationNumber())
                            .orElse(null);

            if (existingMigration == null)
                existingMigration = createMigration(migration.getMigrationNumber(), migration.getMigrationDescription());

            if (!existingMigration.isCompleted()) {
                log.info(String.format("Migration %d starting", migration.getMigrationNumber()));
                return migration.run();
            }

            return Mono.empty();
        }).then(Mono.fromCallable(() -> migration));
    }

    private Consumer<Migration> finishMigration(){
        return migration -> {
            com.oldnews.backend.common.models.Migration migrationModel =
                    migrationRepository.findById(migration.getMigrationNumber())
                            .orElse(null);

            assert migrationModel != null;

            if (!migrationModel.isCompleted())
                log.info(String.format("Migration %d completed", migration.getMigrationNumber()));

            migrationModel.setCompleted(true);
            migrationRepository.save(migrationModel);
        };
    }

    private BiConsumer<Throwable, Object> onError(){
        return (throwable, o) -> {
            log.error("Error while processing migration {}. Cause: {}", o, throwable.getMessage());
        };
    }

    @PostConstruct
    public void run(){
        if (migrate)
            Flux
                    .fromIterable(migrations)
                    .flatMap(this::runMigration)
                    .onErrorContinue(onError())
                    .subscribe(finishMigration());
    }
}
