package me.codebabe.dao.mysql.dao;

import me.codebabe.dao.DAO;
import me.codebabe.dao.mysql.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * author: code.babe
 * date: 2017-11-15 16:52
 */
public interface UserDAO extends DAO {

    @Insert("insert into cb_user(name, email) values(#{name}, #{email})")
    void insert(User user) throws DataAccessException;

    @Select("select * form db_user limit 500")
    List<User> queryAll() throws DataAccessException;

}
