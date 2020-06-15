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

    public static void main(String[] args) {
        new clientView();
    }

    /**
     * 构造
     */
    public clientView() {

        try {
            clientNetwork = new ClientNetwork();
            clientNetwork.setCallBack(new CallBack(){

				@Override
				public void onConnectSuccess(String host, int port) {
                    System.out.println("连接服务器成功");
					
				}

				@Override
				public void onConnectFailed(String host, int port) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onDisconnected() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onMessageSent(String id, String msg) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onMessageReceived(String id, String msg) {
					// TODO Auto-generated method stub
					
				}
                
            });
            initLoginView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化登录界面
     */
    private void initLoginView() throws IOException {
        System.out.print("***登录**:\n");
        System.out.print("*账号: ");
        String IDstr = sc.nextLine();
        //验证用户ID是否存在
        if(clientNetwork.checkID(IDstr)==true){
            
        }
    }
}