package org.nero.tabby.http;

import org.nero.tabby.core.Request;
import org.nero.tabby.core.Response;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * author： nero
 * email: nerosoft@outlook.com
 * data: 16-9-28
 * time: 下午11:34.
 */
public class HttpTask  implements  Runnable {

    private ServerSocket serverSocket;

    public HttpTask(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            Socket socket;
            InputStream input;
            OutputStream output;


            socket = serverSocket.accept();
            input = socket.getInputStream();
            output = socket.getOutputStream();
            //封装request请求
            Request request = new Request(input);
            request.parse();
            //封装response对象
            Response response = new Response(output);
            response.setRequest(request);
            response.sendStaticResource();

            socket.close();
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
