package UDPtest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpReceive {
    public static void main(String[] args) throws IOException {
        //1，创建Udp接收socket
        DatagramSocket socket = new DatagramSocket(8081);
        //2，创建接收字节数据区
        byte[] bytes = new byte[1024];
        //3，创建用于接收数据package包
        DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length);
        //4，接收数据，写入packet里面
        File file = new File("gettest.txt");
        FileOutputStream fos = new FileOutputStream(file);
        int packNum = (int) ((file.length() / 1024) + 1);
        for (int i = 0; i < packNum; i++) {
            socket.receive(packet);
            fos.write(packet.getData(),0,packet.getLength());
            System.out.println("接收端：" + new String(packet.getData(),0,packet.getLength()));
        }


        //5，打印传输的文件


    }

}
