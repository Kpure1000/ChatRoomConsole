package infomation;

import java.io.Serializable;

/**
 * userinfo
 * @author Kpurek
 * @version 1.0
 */
public class InfoBase implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 609116983101639345L;

    /**
     * ID (创建后只读)
     */
    private String ID;

    /**
     * 名称
     */
    String Name;

    public InfoBase(String id, String name) {
        ID = id;
        Name = name;
    }

    public String getID() {
        return ID;
    }
}