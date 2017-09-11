package me.codebabe.common.dataparam;

import java.io.Serializable;

/**
 * author: code.babe
 * date: 2017-09-11 19:10
 */
public class LongPair implements Serializable {

    public LongPair() {
    }

    public LongPair(Long key, Long value) {
        this.key = key;
        this.value = value;
    }

    private Long key;
    private Long value;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongPair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
