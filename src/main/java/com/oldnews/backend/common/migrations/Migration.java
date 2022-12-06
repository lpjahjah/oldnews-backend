package com.oldnews.backend.common.migrations;

import reactor.core.publisher.Mono;

public interface Migration {

    int getMigrationNumber();

    String getMigrationDescription();
    Mono<?> run();
}
