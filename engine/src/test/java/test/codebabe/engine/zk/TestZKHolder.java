package test.codebabe.engine.zk;

import me.codebabe.engine.zk.CBZKHolder;
import org.junit.Test;

/**
 * author: code.babe
 * date: 2017-09-11 14:31
 */
public class TestZKHolder {

    @Test
    public void testCRUD() {
        try {
            if (!CBZKHolder.getInstance().isExist("/persistent")) {
                CBZKHolder.getInstance().createPersistent("/persistent", "directory");
            }
            System.out.println(CBZKHolder.getInstance().getData("/persistent", String.class));

            // 设值
            if (CBZKHolder.getInstance().isExist("/persistent")) {
                CBZKHolder.getInstance().setData("/persistent", "data");
            }
            System.out.println(CBZKHolder.getInstance().getData("/persistent", String.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
