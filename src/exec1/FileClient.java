package exec1;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 */
public class FileClient {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private Scanner sc;

    private String host = "127.0.0.1";

    final int TCP_PORT = 2021;
    final int UDP_PORT = 2020;

    public FileClient(){
        try {
            socket = new Socket(host,TCP_PORT);
            System.out.println("connect success!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initStream(){
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
            sc = new Scanner(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void work() throws IOException {
        initStream();
        String command;
        String response;
        try {
            while ((command = sc.nextLine()) != null) {
                //System.out.println(command);
                pw.println(command);
                response = br.readLine();
                //System.out.println(response);
                if (response.equals("command-quit")){
                    break;
                } else if (response.equals("command-file")){
                    getFile(response = br.readLine());
                } else if (response.startsWith("error")){
                    System.out.println(response.substring(6));
                } else{
                    while (!(response = br.readLine()).equals("end")){
                        System.out.println(response);
                    }
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            socket.close();
            sc.close();
        }


    }

    private void getFile(String fileName) throws IOException {

        DatagramSocket socket = new DatagramSocket(UDP_PORT);

        byte[] bytes = new byte[1024];

        DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length);

        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        int packNum = (int) ((file.length() / 1024) + 1); // 计算数据包个数
        for (int i = 0; i < packNum; i++) {
            socket.receive(packet);
            fos.write(packet.getData(),0,packet.getLength());
        }
        System.out.println("receive over");
        socket.close();
        fos.close();
    }

    public static void main(String[] args) throws IOException {
        new FileClient().work();
    }
}
