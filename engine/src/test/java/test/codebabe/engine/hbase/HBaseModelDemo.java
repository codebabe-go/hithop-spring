package test.codebabe.engine.hbase;

import me.codebabe.engine.hbase.AbsHBaseModel;

/**
 * author: code.babe
 * date: 2017-11-01 21:01
 */
public class HBaseModelDemo extends AbsHBaseModel {

    private String qual1;
    private String qual2;

    @Override
    public String rowkey() {
        return "row1";
    }

    @Override
    public String getTable() {
        return "testtable";
    }

    @Override
    public String getColumnFamily() {
        return "colfam1";
    }

    public String getQual1() {
        return qual1;
    }

    public void setQual1(String qual1) {
        this.qual1 = qual1;
    }

    public String getQual2() {
        return qual2;
    }

    public void setQual2(String qual2) {
        this.qual2 = qual2;
    }
}
