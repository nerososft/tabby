package org.nero.tabby.http;

import org.nero.tabby.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HttpServer extends Thread {

    /**
     * @param args
     */

    //WEB_ROOT是服务器的根目录
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "www";

    //关闭的命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";



    public void await() {
        ServerSocket serverSocket = null;
        int port = 8080;
        System.out.println(WEB_ROOT);


        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Executor executor = Executors.newFixedThreadPool(new SystemUtils().getMaxThread());
        while(true){
            executor.execute(new HttpTask(serverSocket));
        }

    }


}