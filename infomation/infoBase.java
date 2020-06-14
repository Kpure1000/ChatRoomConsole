package infomation;

/**
 * userinfo
 */
public class infoBase {

    /**
     * ID (创建后只读)
     */
    private char[] ID;

    /**
     * 名称
     */
    String Name;

    public infoBase(char[] id, String name) {
        ID = id;
        Name = name;
    }
}