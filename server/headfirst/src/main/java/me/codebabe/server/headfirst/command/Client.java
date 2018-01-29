package me.codebabe.server.headfirst.command;

/**
 * author: code.babe
 * date: 2017-12-22 14:56
 */
public interface Client {

    Command createCommand();

    void setCommand(Command cmd);

}
