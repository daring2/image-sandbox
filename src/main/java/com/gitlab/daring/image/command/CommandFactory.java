package com.gitlab.daring.image.command;

@FunctionalInterface
public interface CommandFactory {

    Command create(String[] params);

}
