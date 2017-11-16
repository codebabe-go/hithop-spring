package me.codebabe.outspace.facade.impl;

import me.codebabe.dao.mysql.model.User;
import me.codebabe.engine.service.UserService;
import me.codebabe.outspace.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: code.babe
 * date: 2017-11-16 17:32
 */
@Service("userFacade")
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Override
    public void insert(User user) {
        userService.insert(user);
    }
}
