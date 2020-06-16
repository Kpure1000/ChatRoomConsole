package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 客户端网络类
 * 
 * @author Kpurek
 * @version 1.0
 */
public class ClientNetwork {

    /*--------------------------------------------*/

    // 套接字
    private Socket socket;
    // 输入
    private DataInputStream inputStream;
    // 输出
    private DataOutputStream outputStream;
    // 连接标志
    private boolean isConnected;

    /**
     * 连接服务器
     * 
     * @param host 主机号
     * @param port 端口
     */
    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            isConnected = true;
            if (callBack != null) {
                callBack.onConnectSuccess(host, port);
            }
            beginListening();
        } catch (IOException e) {
            isConnected = false;
            if (callBack != null) {
                callBack.onConnectFailed(host, port);
            }
            e.printStackTrace();
        }
    }

    /**
     * 监听来自服务器的消息
     */
    private void beginListening() {
        new Thread(() -> {
            try {
                inputStream = new DataInputStream(socket.getInputStream());
                while (inputStream!=null) {
                    var inputStr = inputStream.readUTF().split("#");
                    if (callBack != null) {
                        callBack.onMessageReceived(inputStr[0], inputStr[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            isConnected = false;
            if (callBack != null) {
                callBack.onDisconnected();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发消息
     * 
     * @param id 目标用户ID
     * @param msg  消息
     */
    public void sendMessage(String id, String msg) {
        try {
            if (msg == null || "".equals(msg) || socket == null) {
                return;
            }
            if (outputStream != null) {
                String outputStr = id + msg;
                outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(outputStr);
                outputStream.flush();
                if (callBack != null) {
                    callBack.onMessageSent(id, msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 已/未连接
     * 
     * @return 是否连接,true为连接，false为未连接
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /**
     * 验证用户ID
     * 
     * @param iDstr
     * @return true:ID已存在
     */
    public boolean checkID(String iDstr) {
        // TODO 服务器验证ID
        return false;
    }

    public interface CallBack {

        /**
         * 当连接成功时
         * 
         * @param host 主机号
         * @param port 端口号
         */
        void onConnectSuccess(String host, int port);

        /**
         * 当连接失败时
         * 
         * @param host 主机号
         * @param port 端口号
         */
        void onConnectFailed(String host, int port);

        /**
         * 当断开连接时
         */
        void onDisconnected();

        /**
         * 当发送消息时
         */
        void onMessageSent(String id, String msg);

        /**
         * @param id  消息源ID
         * @param msg 消息内容
         */
        void onMessageReceived(String id, String msg);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

}