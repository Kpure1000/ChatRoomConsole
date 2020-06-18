package view;

import java.io.IOException;
import java.util.Scanner;

import infomation.UserMessage;
import infomation.UserMessage.MessageType;
import service.ClientNetwork;
import service.ClientNetwork.CallBack;

/**
 * 客户端视图
 * 
 * @author Kpurek
 * @version 1.0
 */
public class clientView {

    Scanner sc = new Scanner(System.in, "UTF-8");

    ClientNetwork clientNetwork;

    final String hostString = "127.0.0.1";

    final int portNum = 12834;

    public static void main(String[] args) {
        try {
            var view = new clientView();
            view.clientRun();
        } catch (IOException e) {
            System.out.println("退出主程序");
            e.printStackTrace();
        }
    }

    /**
     * 构造
     */
    public clientView() {
        clientNetwork = new ClientNetwork();

        /**
         * 实现回调接口
         */
        clientNetwork.setCallBack(new CallBack() {

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
            public void onMessageSent(String id, UserMessage msg) {
                System.out.print("发送消息---->" + msg.getType().toString() + ": " + msg.getMessage());
            }

            @Override
            public void onMessageReceived(String id, UserMessage msg) {
                System.out.print("接收消息<----" + msg.getType().toString() + ": " + msg.getMessage() + "\n");

            }

        });

        try {
            initCilentView();
            initLoginView();
        } catch (IOException e) {
            e.printStackTrace();
            // 由于服务器错误
            System.out.println("退出程序");
            return;
        }
    }

    /**
     * 初始化界面
     */
    private void initCilentView() throws IOException {
        System.out.println("****欢迎来到QuickChat!****");
        System.out.println("服务器信息: " + hostString + ":" + portNum);
        System.out.println("尝试连接服务器...");
        try {
            clientNetwork.connect(hostString, portNum);
        } catch (IOException e) {
            System.out.println("退出程序");
            e.printStackTrace();
        }

    }

    /**
     * 初始化登录界面
     */
    private void initLoginView() throws IOException {
    }

    public void clientRun() throws IOException {
        try {
            while (clientNetwork != null && clientNetwork.isConnected()) {
                System.out.println("请输入需要执行的操作:\n1.send\n2.quit");
                int operation = 0;
                if (sc.hasNext())
                    operation = sc.nextInt();
                    sc.nextLine();
                switch (operation) {
                    case 1:
                        System.out.println("输入消息:");
                        String msgStr="";
                        if(sc.hasNextLine()){
                            msgStr = sc.nextLine();
                        }
                        UserMessage msg = new UserMessage(MessageType.MSG_PRIVATE, msgStr);
                        clientNetwork.sendMessage("user", msg);
                        break;
                    case 2:
                        clientNetwork.disconnect();
                        break;
                    default:
                        System.out.println("Error Input. Again.");
                        break;
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}