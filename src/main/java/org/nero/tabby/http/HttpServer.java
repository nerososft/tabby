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

import static java.util.concurrent.ThreadPoolExecutor.*;

public class HttpServer{

    /**
     * @param args
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //WEB_ROOT是服务器的根目录
    public String WEB_ROOT = System.getProperty("user.dir") + File.separator;

    //关闭的命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";


    private ServerSocket serverSocket = null;
    private int port = 8080;
    private String hostName = "127.0.0.1";

    /**
     * 线程池相关配置
     */
    private int corePoolSize = 1;//线程池维护线程的最少数量

    private int maximumPoolSize=new SystemUtils().getMaxThread();//线程池维护线程的最大数量

    private int keepAliveTime = 60;// 线程池维护线程所允许的空闲时间

    private TimeUnit unit = TimeUnit.SECONDS;// 线程池维护线程所允许的空闲时间的单位

    private int workQueueSize = 200;// 线程池所使用的缓冲队列大小

    private RejectedExecutionHandler handler;//线程池对拒绝任务的处理策略


    public void loadCongfig(){
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

        String corePoolSize = configure.getConfItem("corePoolSize");
        if(!"".equals(corePoolSize)){
            this.corePoolSize = Integer.parseInt(corePoolSize);
        }

        String maximumPoolSize = configure.getConfItem("maximumPoolSize");
        if (!"".equals(maximumPoolSize)){
            this.maximumPoolSize = Integer.parseInt(maximumPoolSize);
        }
        String keepAliveTime = configure.getConfItem("keepAliveTime");
        if(!"".equals(keepAliveTime)) {
            this.keepAliveTime = Integer.parseInt(keepAliveTime);
        }

        String workQueueSize = configure.getConfItem("workQueue");
        if(!"".equals(workQueueSize)){
            this.workQueueSize = Integer.parseInt(workQueueSize);
        }


        WEB_ROOT = WEB_ROOT.replaceAll("bin","www");

        System.out.println("server run at "+this.hostName+":"+this.port);
        System.out.println("webroot : "+ WEB_ROOT);
    }
    public void init(){
        loadCongfig();
    }

    public void await() {
        init();
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName(hostName));
        } catch (IOException e){
            logger.error(e.getMessage(),e.getCause());
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(this.corePoolSize,
                this.maximumPoolSize,
                this.keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(this.workQueueSize),new DiscardPolicy());
        while(true){
            threadPoolExecutor.submit(new HttpTask(serverSocket));
        }


    }


}