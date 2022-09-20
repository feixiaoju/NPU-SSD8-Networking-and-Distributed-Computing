package Test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9999);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Scanner sc = new Scanner(System.in);
        String out; //客户端输出
        String in; //客户端输入
        while (true) {
            bw.write(sc.nextLine() + "\n");
            bw.flush();

            System.out.println(br.readLine() + " from server");
        }
    }
}
