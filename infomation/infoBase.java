package infomation;

/**
 * userinfo
 * @author Kpurek
 * @version 1.0
 */
public class infoBase {

    /**
     * ID (创建后只读)
     */
    private String ID;

    /**
     * 名称
     */
    String Name;

    public infoBase(String id, String name) {
        ID = id;
        Name = name;
    }

    public String getID() {
        return ID;
    }
}