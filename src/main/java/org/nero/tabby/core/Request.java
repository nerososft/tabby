package org.nero.tabby.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;


public class Request {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //输入流
    private InputStream input;

    private String uri;
    public Request(InputStream inputStream){
        this.input = inputStream;
    }

    public InputStream getInput() {
        return input;
    }
    public void setInput(InputStream input) {
        this.input = input;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    //从套接字中读取字符信息
    public void parse(){

        StringBuilder request = new StringBuilder(2048);
        int i = 0;
        byte[] buffer = new byte[2048];

        try {
            i = input.read(buffer);
        } catch (IOException e) {
            logger.error(e.getMessage(),e.getCause());
            i = -1;
        }
        for(int j = 0;j<i;j++){
            request.append((char)(buffer[j]));
        }
        logger.info(request.toString());
        uri = parseUri(request.toString());
    }
    //截取请求的url
    private String parseUri(String requestString){

        int index1 = 0;
        int index2 = 0;
        index1 = requestString.indexOf(' ');
        if(index1!=-1){
            index2 = requestString.indexOf(' ',index1+1);
            if(index2>index1){
                return requestString.substring(index1+1,index2);
            }
        }

        return null;
    }




}