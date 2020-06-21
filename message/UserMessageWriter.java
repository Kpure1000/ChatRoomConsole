package message;

import infomation.*;
import message.UserMessage.MessageType;

/**
 * 用户消息编辑器
 * <p>
 * 这是用户行为，用户仅拥有编辑权，没有解析权
 * 
 * @author Kpurek
 * @version 1.0
 */
public class UserMessageWriter {

    private UserMessage userMessage;

    public UserMessageWriter() {
        this.userMessage = new UserMessage();
    }

    /**
     * 编辑私聊消息
     * 
     * @param targetInfo 目标用户
     * @param message    消息内容
     */
    public UserMessage editAsPrivate(UserInfo targetInfo, String message) {
        userMessage.messageType = MessageType.MSG_PRIVATE;
        userMessage.targetInfo = targetInfo;
        userMessage.message = message;
        return userMessage;
    }

    /**
     * 编辑群聊消息
     * 
     * @param target  目标群聊
     * @param message 消息内容
     */
    public UserMessage editAsGroup(GroupInfo target, String message) {
        userMessage.messageType = MessageType.MSG_GROUP;
        userMessage.targetInfo = target;
        userMessage.message = message;
        return userMessage;
    }

    /**
     * 验证ID
     * 
     * @param writeInfo 发送者信息
     * @param id        ID号
     */
    public UserMessage checkID(UserInfo writeInfo, String id) {
        userMessage.messageType = MessageType.CHECK_ID;
        userMessage.message = id;
        userMessage.writerInfo = writeInfo;
        return userMessage;
    }

    /**
     * 验证密码
     * 
     * @param writer
     * @param pass
     */
    public UserMessage checkPass(UserInfo writer, String pass) {
        userMessage.messageType = MessageType.CHECK_PASS;
        userMessage.message = pass;
        userMessage.writerInfo = writer;
        return userMessage;
    }

    public UserMessage requireOffLine(UserInfo writer) {
        userMessage.messageType = MessageType.REQUIRE_OFFLINE;
        userMessage.writerInfo = writer;
        userMessage.message = "Require OffLine";
        return userMessage;
    }

    /**
     * 测试连接用的消息
     * 
     * @param message
     * @return
     */
    public UserMessage testMessage(String message) {
        userMessage.messageType = MessageType.MSG_TEST;
        userMessage.message = message;
        return userMessage;
    }

}