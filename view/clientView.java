package view;

import java.io.IOException;
import java.util.Scanner;

import message.UserMessage;
import message.UserMessageWriter;
import service.ClientNetwork;
import service.ClientNetwork.CallBack;

/**
 * 客户端视图
 * 
 * @author Kpurek
 * @version 1.0
 */
public class clientView {

    /**
     * 选择扫描器
     */
    Scanner selectSc = new Scanner(System.in, "UTF-8");

    /**
     * 输入扫描器
     */
    Scanner inputSc = new Scanner(System.in, "UTF-8");

    /**
     * 主机号 本地
     */
    final String hostString = "127.0.0.1";

    /**
     * 本地监听端口
     */
    final int portNum = 12834;

    /**
     * 入口
     * 
     * @param args
     */
    public static void main(String[] args) {
        var view = new clientView();
        view.clientRun();
    }

    /**
     * 构造
     */
    public clientView() {

        /**
         * 实现回调接口
         */
        ClientNetwork.getInstance().setCallBack(new CallBack() {

            @Override
            public void onConnectSuccess(String host, int port) {
                System.out.println("连接服务器成功");
            }

            @Override
            public void onConnectFailed(String host, int port) {
                System.out.println("连接失败");
            }

            @Override
            public void onDisconnected() {
                System.out.println("断开连接");
            }

            @Override
            public void onMessageSent(UserMessage msg) {
                System.out.println("发送消息---->" + msg.getContent());
            }

            @Override
            public void onMessageReceived(UserMessage msg) {
                System.out.println("接收消息<----" + msg.getContent());

            }

        });

        // 网络异常在网络层解决
        initCilentView();
        initLoginView();

    }

    /**
     * 初始化界面
     */
    private void initCilentView() {
        System.out.println("****欢迎来到QuickChat!****");
        System.out.println("服务器信息: " + hostString + ":" + portNum);
        System.out.println("尝试连接服务器...");
        // 不断尝试连接服务器
        while (true) {
            try {
                if (ClientNetwork.getInstance().connect(hostString, portNum)) {
                    break;
                }
                // 间隔500ms重试连接
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 初始化登录界面
     */
    private void initLoginView() {
    }

    /**
     * 客户端控制
     * <p>
     * 这是向控制台的妥协
     */
    public void clientRun() {

        while (ClientNetwork.getInstance() != null && ClientNetwork.getInstance().isConnected()) {
            System.out.println("请输入需要执行的操作:\n1.send\n2.quit");
            // 选项
            int operation = 0;
            if (selectSc.hasNext()) {
                operation = selectSc.nextInt();
            }
            selectSc.nextLine();
            switch (operation) {
                case 1: // 消息输入
                    System.out.println("输入消息:");
                    String msgStr = "";
                    if (selectSc.hasNextLine()) {
                        msgStr = selectSc.nextLine();
                    }
                    var msg = new UserMessageWriter();
                    ClientNetwork.getInstance().sendMessage(msg.testMessage(msgStr));
                    break;
                case 2: // 下线
                    // TODO 这里需要加入下线操作
                    // 目前只是断开连接，但这并不是下线
                    ClientNetwork.getInstance().disconnect();
                    break;
                default:
                    System.out.println("Error Input. Again.");
                    break;
            }
            System.out.println();
        }

    }
}