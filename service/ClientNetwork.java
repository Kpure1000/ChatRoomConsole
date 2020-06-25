package service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.*;

/**
 * 客户端网络类
 * <p>
 * 网络层,自己的异常自己解决
 * 
 * @author Kpurek
 * @version 1.0
 */
public class ClientNetwork {

    /**
     * 单例
     */
    private static ClientNetwork instance;

    private ClientNetwork() {
    }

    /**
     * 获取单例
     * 
     * @return 实例
     */
    public static ClientNetwork getInstance() {
        if (instance == null) {
            instance = new ClientNetwork();
            return instance;
        }
        return instance;
    }

    /*--------------------------------------------*/

    /**
     * 客户端套接字
     */
    private Socket socket;

    /**
     * 连接标志
     */
    private boolean isConnected = false;

    /**
     * 对象输出流，只能实例化一次，不然多次输出会导致文件头不一致的AC异常（接收端）
     */
    ObjectOutputStream objOut;

    private String curHost;
    private int curPort;

    /**
     * 连接服务器
     * 
     * @param host 主机号
     * @param port 端口
     * @return 返回是否连接成功
     */
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            // 暂存目前可用服务器信息
            curHost = host;
            curPort = port;
            // 标志连接
            isConnected = true;
            if (callBack != null) {
                callBack.onConnectSuccess(host, port);
            }
            beginListening();
            return true;
        } catch (IOException e) {
            isConnected = false;
            if (callBack != null) { // 连接失败
                callBack.onConnectFailed(host, port);
            }
            return false;
        }
    }

    /**
     * 监听来自服务器的消息
     */
    private void beginListening() {
        new Thread(() -> {
            try {
                socket.getOutputStream();
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
     * @param msg 消息
     */
    public void sendMessage(UserMessage msg) {
        try {
            if (msg == null) {
                System.out.println("消息传入错误");
                return;
            }
            if (objOut == null) // 加载对象输出流
                objOut = new ObjectOutputStream(socket.getOutputStream());
            if (objOut != null) {
                objOut.writeObject(msg);
                objOut.flush();
                if (callBack != null) {
                    callBack.onMessageSent(msg);
                }
            }
        } catch (IOException e) {
            // TODO 如果断开连接，broken pipe异常如何处理？
            if (socket.isConnected()) {
                while (isConnected == false) {
                    System.out.println("连接断开，发送失败;正在尝试重新连接...");
                    // TODO 这里其实
                    connect(curHost, curPort);
                    try {
                        //等待400ms
                        Thread.sleep(400);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 是否客户端已经连接服务器
     * 
     * @return 是否连接,true为连接，false为未连接
     */
    public boolean isConnected() {
        return this.isConnected;
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
        void onMessageSent(UserMessage msg);

        /**
         * @param id  消息源ID
         * @param msg 消息内容
         */
        void onMessageReceived(UserMessage msg);
    }

    /**
     * 回调接口
     */
    private CallBack callBack;

    /**
     * 设置回调
     * 
     * @param callBack 外部实现接口
     */
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

}