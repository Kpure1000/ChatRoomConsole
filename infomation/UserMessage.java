package infomation;

import java.io.Serializable;

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
         * 私聊消息
         */
        MSG_PRIVATE,
        /**
         * 群聊消息
         */
        MSG_GROUP
    }

    private MessageType messageType;

    private String message;

    public UserMessage(MessageType messageType, String msg) {
        this.messageType = messageType;
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return messageType;
    }

    public void setType(MessageType messageType) {
        this.messageType = messageType;
    }
}
