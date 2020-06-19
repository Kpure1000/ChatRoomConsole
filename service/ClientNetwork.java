package service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.*;

/**
 * 客户端网络类
 * 
 * @author Kpurek
 * @version 1.0
 */
public class ClientNetwork {

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

    /**
     * 连接服务器
     * 
     * @param host 主机号
     * @param port 端口
     */
    public void connect(String host, int port) throws IOException {
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
     * // TODO 这里还要考虑同步问题，应该在socket关闭前，结束线程（下线时关闭线程）
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
     * <p>
     * // TODO 应该改为protect，要加一个行为层
     */
    public void disconnect() {
        try {
            if (socket != null) {
                // TODO 这个地方只是目前这么写，disconnect之外还需要加一个行为层
                sendMessage(new UserMessageWriter().requireOffLine(null));
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
            if (objOut == null)
                objOut = new ObjectOutputStream(socket.getOutputStream());
            if (objOut != null) {
                objOut.writeObject(msg);
                objOut.flush();
                if (callBack != null) {
                    callBack.onMessageSent(msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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