package UDPtest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class UdpSend {
    public static void main(String[] args) throws IOException {
        //1������upd���ݱ�socket�����Ľ��ն˵�Ip�Ͷ˿ڣ������ݰ��ж��壬socket������
        //���������TCP���䷽ʽ
        DatagramSocket socket = new DatagramSocket();
        //2���������ݰ����������ݣ�Ip �˿�
        //����ʹ�� 127.0.0.1
        InetAddress inetAddress = InetAddress.getLocalHost();
        String str = "��ã����Ƿ��Ͷ�";
        //ת��Ϊ�ֽڷ���
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

        //3������upd����

        //4���ر�socket��Դ
        socket.close();

    }

}

