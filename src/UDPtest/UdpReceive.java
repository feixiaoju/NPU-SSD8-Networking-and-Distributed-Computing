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
        //1������Udp����socket
        DatagramSocket socket = new DatagramSocket(8081);
        //2�����������ֽ�������
        byte[] bytes = new byte[1024];
        //3���������ڽ�������package��
        DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length);
        //4���������ݣ�д��packet����
        File file = new File("gettest.txt");
        FileOutputStream fos = new FileOutputStream(file);
        int packNum = (int) ((file.length() / 1024) + 1);
        for (int i = 0; i < packNum; i++) {
            socket.receive(packet);
            fos.write(packet.getData(),0,packet.getLength());
            System.out.println("���նˣ�" + new String(packet.getData(),0,packet.getLength()));
        }


        //5����ӡ������ļ�


    }

}
