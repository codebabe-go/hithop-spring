package me.codebabe.engine.service.impl;

import me.codebabe.common.exception.HithopException;
import me.codebabe.common.exception.define.ErrorCode;
import me.codebabe.dao.mysql.dao.UserDAO;
import me.codebabe.dao.mysql.model.User;
import me.codebabe.engine.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * author: code.babe
 * date: 2017-11-16 17:40
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public void insert(User user) {
        try {
            userDAO.insert(user);
        } catch (DataAccessException e) {
            logger.error("db insert error", e);
            throw new HithopException("db error", ErrorCode.MYSQL_DB_ERROR);
        }
    }
}
