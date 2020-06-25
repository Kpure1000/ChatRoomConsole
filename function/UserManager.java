package function;

import infomation.UserInfo;

/**
 * 用户管理器
 * @author Kpurek
 * @version 1.0
 */
public class UserManager {

    /** 单例 */
    private static UserManager instance;

    private UserManager() {
    }

    /**
     * 获取单例
     * 
     * @return 实例
     */
    public static UserManager getInstance() {
        if (instance == null) {
            return instance = new UserManager();
        }
        return instance;
    }

    /*--------------------------------------------*/

    /**
     * 用户信息（初始化为空）
     */
    UserInfo userInfo;

    /** 用户状态 */
    public enum UserState {
        OFFLINE, ONLINE
    }

    /** 状态实例 */
    UserState userState = UserState.OFFLINE;
}