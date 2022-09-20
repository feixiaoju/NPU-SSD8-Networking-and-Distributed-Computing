package exec1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer {
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    final int TCP_PORT = 2021;
    final int POOL_SIZE = 4;
    public FileServer(){
        try {
            serverSocket = new ServerSocket(TCP_PORT);
            executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
            System.out.println("server start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void service(String path){
        System.out.println("root is " + path);
        Socket socket;
        try {
            while (true){
                socket = serverSocket.accept();
                executorService.execute(new Thread(new socketThread(socket,path)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new FileServer().service("D:\\Java");
    }
}
