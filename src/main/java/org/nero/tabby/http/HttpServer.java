package org.nero.tabby.http;

import org.nero.tabby.conf.Configure;
import org.nero.tabby.utils.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class HttpServer{

    /**
     * @param args
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //WEB_ROOT是服务器的根目录
    public static String WEB_ROOT = System.getProperty("user.dir") + File.separator;

    //关闭的命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";


    private ServerSocket serverSocket = null;
    private int port = 8080;
    private String hostName = "127.0.0.1";

    public void init(){

        Configure configure = new Configure();
        configure.init();

        String confport = configure.getConfItem("port");
        if(!"".equals(confport)){
            this.port = Integer.parseInt(confport);
        }
        String hostname = configure.getConfItem("hostname");
        if(!"".equals(hostname)){
            this.hostName = hostname;
        }

        WEB_ROOT = WEB_ROOT.replaceAll("bin","www");

        System.out.println("server run at "+this.hostName+":"+this.port);
        System.out.println("webroot : "+ WEB_ROOT);
    }

    public void await() {
        init();
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(hostName));
        } catch (IOException e){
            logger.error(e.getMessage(),e.getCause());
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                new SystemUtils().getMaxThread(),
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(200),new ThreadPoolExecutor.DiscardPolicy());
        while(true){
            threadPoolExecutor.submit(new HttpTask(serverSocket));
        }


    }


}