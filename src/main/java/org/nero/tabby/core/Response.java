package org.nero.tabby.core;

import org.nero.tabby.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;


public class Response {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;
    public Response(OutputStream output){
        this.output = output;
    }

    public void sendStaticResource() throws IOException{

        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;

        String uri = request.getUri();

        if(uri==null){
            uri = "index.html";
        }
        File file = new File(HttpServer.WEB_ROOT,uri);
        if(file.exists()){
            try {
                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0,BUFFER_SIZE);
                while(ch != -1){
                    output.write(bytes,0,ch);
                    ch = fis.read(bytes,0,BUFFER_SIZE);
                }

            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(),e.getCause());
            }catch(IOException e){
                logger.error(e.getMessage(),e.getCause());
            }finally{
                if(fis !=null){
                    fis.close();
                }
            }

        }else{
            //找不到文件
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    " File Not Found ";
            try {
                output.write(errorMessage.getBytes());
                output.flush();
            } catch (IOException e) {
                logger.error(e.getMessage(),e.getCause());
            }
        }
    }
    public Request getRequest() {
        return request;
    }
    public void setRequest(Request request) {
        this.request = request;
    }
    public OutputStream getOutput() {
        return output;
    }
    public void setOutput(OutputStream output) {
        this.output = output;
    }
    public static int getBUFFER_SIZE() {
        return BUFFER_SIZE;
    }



}