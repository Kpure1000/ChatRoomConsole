package infomation;

/**
 * userInfo
 * @author Kpurek
 * @version 1.0
 */
public class UserInfo extends InfoBase {

    /**
     *
     */
    private static final long serialVersionUID = -4159540016928697934L;

    public String email = "";

    public String phoneNumber = "";

    public UserInfo(String id, String name) {
        super(id, name);
    }

}