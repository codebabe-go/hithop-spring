package me.codebabe.engine.service;

import me.codebabe.common.exception.HithopException;
import me.codebabe.dao.mysql.model.User;

/**
 * author: code.babe
 * date: 2017-11-16 17:40
 */
public interface UserService {

    void insert(User user) throws HithopException;

}
