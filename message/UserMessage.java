package message;

import java.io.Serializable;

import infomation.*;
/**
 * 用户信息类
 */
public class UserMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -201382262832780376L;

    public enum MessageType {
        /**
         * 请求上线
         */
        REQUIRE_ONLINE,
        /**
         * 请求下线
         */
        REQUIRE_OFFLINE,
        /**
         * 验证ID
         */
        CHECK_ID,
        /**
         * 验证密码
         */
        CHECK_PASS,
        /**
         * 设置用户名
         */
        SET_NAME,
        /**
         * 设置密码
         */
        SET_PASS,
        /**
         * 新建账户
         */
        CREATE_COUNT,
        /**
         * 私聊消息
         */
        MSG_PRIVATE,
        /**
         * 群聊消息
         */
        MSG_GROUP, 
        
        /**
         * 测试消息
         */
        MSG_TEST
    }

    /**
     * 消息类型
     */
    protected MessageType messageType;

    /**
     * 消息字符串内容
     */
    protected String message;

    /**
     * 发送者信息
     */
    protected InfoBase writerInfo;

    /**
     * 接收者信息
     */
    protected InfoBase targetInfo;

    /**
     * 构造
     */
    public UserMessage() {
        this.messageType = null;
        this.message = "";
    }
    
    public String getContent() {
        return messageType.toString() + ": " + message;
    }

}
