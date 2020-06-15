package view;

import java.io.IOException;
import java.util.Scanner;

import javax.security.auth.callback.Callback;

import service.ClientNetwork;

/**
 * 客户端视图
 * 
 * @author Kpurek
 * @version 1.0
 */
public class clientView {

    Scanner sc;

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

    }
}