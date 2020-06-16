package view;

import java.io.IOException;
import java.util.Scanner;

import service.ClientNetwork;
import service.ClientNetwork.CallBack;

/**
 * 客户端视图
 * 
 * @author Kpurek
 * @version 1.0
 */
public class clientView {

    Scanner sc = new Scanner(System.in);

    ClientNetwork clientNetwork;

    final String hostString = "127.0.0.1";

    final int portNum = 12834;

    public static void main(String[] args) {
        var view = new clientView();
        try {
            view.clientRun();
        } catch (IOException e) {
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
            public void onMessageSent(String id, String msg) {
                System.out.print("------>" + msg);
            }

            @Override
            public void onMessageReceived(String id, String msg) {
                System.out.print(id + ": \n" + msg + "\n");

            }

        });
        try {
            initLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化登录界面
     */
    private void initLoginView() throws IOException {
        // System.out.print("***登录**:\n");
        // System.out.print("*账号: ");
        // String IDstr = sc.nextLine();
        // // 验证用户ID是否存在
        // if (clientNetwork.checkID(IDstr) == true) {

        // }

        // System.out.println("尝试登录: " + hostString + ":" + portNum);
        clientNetwork.connect(hostString, portNum);
    }

    public void clientRun() throws IOException {
        try {
            while (clientNetwork != null && clientNetwork.isConnected()) {
                System.out.print("请输入需要执行的操作:\n1.send\n2.quit");
                int operation = sc.nextInt();
                switch (operation) {
                    case 1:
                        System.out.println("输入消息:");
                        clientNetwork.sendMessage("user", "msg: " + sc.nextLine());
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
            // TODO: handle exception
        }
    }
}