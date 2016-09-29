package org.nero.tabby.http;

import org.nero.tabby.conf.Configure;
import org.nero.tabby.conf.Confkey;
import org.nero.tabby.conf.Confvalue;
import org.nero.tabby.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class HttpServer{

    /**
     * @param args
     */
    private Executor executor;
    //WEB_ROOT是服务器的根目录
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "www";

    //关闭的命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";


    private ServerSocket serverSocket = null;
    private int port = 8080;
    private String hostName = "127.0.0.1";
    private Configure configure;
    public void init(){

        configure = new Configure();
        configure.init();

        String confport = configure.getConfItem("port");
        if(!confport.equals("")){
            this.port = Integer.valueOf(confport).intValue();
        }
        String hostName = configure.getConfItem("hostname");
        if(!hostName.equals("")){
            this.hostName = hostName;
        }

        System.out.println("server run at "+this.hostName+":"+this.port);
    }

    public void await() {
        init();
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(hostName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor = Executors.newFixedThreadPool(new SystemUtils().getMaxThread());
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                new SystemUtils().getMaxThread(),
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(200),new ThreadPoolExecutor.DiscardPolicy());
        while(true){
            threadPoolExecutor.submit(new HttpTask(serverSocket));
        }


    }


}