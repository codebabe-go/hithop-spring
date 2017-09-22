package test.codebabe.engine.zk;

import me.codebabe.engine.zk.CBZKHolder;
import org.apache.curator.utils.ZKPaths;
import org.junit.Test;

/**
 * author: code.babe
 * date: 2017-09-11 14:31
 */
public class ZKHolderTest {

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

    @Test
    public void testPathChildCache() {
        String path = "/parent";
        System.out.println(path);
        try {
            CBZKHolder.getInstance().getPathChildCache(path, (client, event) -> {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("[getPathChildCache]add " + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                    case INITIALIZED:
                        System.out.println("[getPathChildCache]init" + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                    case CHILD_REMOVED:
                        System.out.println("[getPathChildCache]remove" + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                    case CHILD_UPDATED:
                        System.out.println("[getPathChildCache]update" + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                    case CONNECTION_LOST:
                        System.out.println("[getPathChildCache]lost" + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                    case CONNECTION_SUSPENDED:
                        System.out.println("[getPathChildCache]suspend" + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                    case CONNECTION_RECONNECTED:
                        System.out.println("[getPathChildCache]reconnection" + String.format("path: %s, event: %s", path, event.toString()));
                        break;
                }
            });
            // 这里不需要再额外创建路径
//            CBZKHolder.getInstance().createPersistent(path);
            CBZKHolder.getInstance().setData(path, "parent");
            Thread.sleep(1000);
            CBZKHolder.getInstance().createPersistent(path.concat("/huangzi1"), "huangzi1"); // add
            Thread.sleep(1000);
            CBZKHolder.getInstance().deleteNode("/parent/huangzi1"); // delete
            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                CBZKHolder.getInstance().deleteRNode(path);
            } catch (Exception e) {
            }
        }
    }

    @Test
    public void testNodeCache() throws Exception {
        String path = ZKPaths.makePath("/path", null);
        System.out.println(path);
        CBZKHolder.getInstance().getNodeCache(path, () -> {
            System.out.println(String.format("path: %s data changed", path));
        });
        CBZKHolder.getInstance().createPersistent(path);
        CBZKHolder.getInstance().setData(path, "hello");
        CBZKHolder.getInstance().deleteNode(path);
    }

}
