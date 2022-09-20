package Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket socket = serverSocket.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner sc = new Scanner(System.in);
        String out; //服务器输出
        String in; //服务器输入
        while (true){
            in = br.readLine();
            System.out.println(in + " from client");
            if (in.equals("quit\n")){
                break;
            }

            bw.write(sc.nextLine() + "\n");
            bw.flush();
        }
        bw.close();
    }
}
