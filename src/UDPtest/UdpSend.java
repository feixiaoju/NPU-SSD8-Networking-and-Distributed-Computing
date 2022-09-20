package UDPtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class UdpSend {
    public static void main(String[] args) throws IOException {
        //1，创建upd数据报socket，报文接收端的Ip和端口，在数据包中定义，socket不关心
        //这点区别于TCP传输方式
        DatagramSocket socket = new DatagramSocket();
        //2，创建数据包，数据内容，Ip 端口
        //这里使用 127.0.0.1
        InetAddress inetAddress = InetAddress.getLocalHost();
        String str = "你好，我是发送端";
        //转换为字节发送
        byte[] bytes = str.getBytes();
        File file = new File("test.txt");
        System.out.println(file.length());
        FileInputStream fis = new FileInputStream(file);
        DatagramPacket packet;
        int length = 0;
        while ((length = fis.read(bytes)) != -1){
            packet = new DatagramPacket(bytes,0,bytes.length,inetAddress,8081);
            socket.send(packet);
        }

        //3，启动upd发送

        //4，关闭socket资源
        socket.close();

    }

}

