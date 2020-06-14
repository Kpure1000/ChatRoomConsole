package service;

public class ClientNetwork {

    private boolean connected;
    public ClientNetwork(CallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 连接服务器
     * 
     * @param host 主机号
     * @param port 端口
     */
    public void connect(String host, int port) {

    }

    /**
     * 断开连接
     */
    public void disconnect() {

    }

    /**
     * 发消息
     * 
     * @param name 用户名
     * @param msg  消息
     */
    public void sendMessage(String name, String msg) {

    }

    /**
     * 已/未连接
     * 
     * @return 是否连接,true为连接，false为未连接
     */
    public boolean isConnected() {
        return this.connected;
    }

    public interface CallBack {

        void onConnected(String host, int port);

        void onConnectFailed(String host, int port);

        void onDisconnected();

        void onMessageSent();

        void onMessageReceived(String name, String msg);
    }

    private CallBack callBack;
}