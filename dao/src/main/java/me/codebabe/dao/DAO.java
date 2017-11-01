package me.codebabe.dao;

/**
 * author: code.babe
 * date: 2017-10-31 20:10
 */
public interface DAO<T> {

    int insert(T t);

    int deleteById(Object id);

    T queryById(Object id);

    T update(T t);

}
