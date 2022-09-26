package homework;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * 使用Java Socket封装HTTP 请求报文分别GET一个文本文件，http://cxhz.hep.com.cn/css/common.css，
 * 和一个图片文件：http://cxhz.hep.com.cn/img/bg_big.png，解析返回的响应报文，将header信息打印在控制台上，
 * 将body中的文件保存到本地磁盘。提示：可先使用浏览器的开发者模式（F12）查看请求和响应的包头信息。
 */
public class HttpGet {

    String requestTextMeg = "GET /css/common.css HTTP/1.1\n" +
                            "Host: cxhz.hep.com.cn\n\n";

    String requestImgMeg = "GET /img/bg_big.png HTTP/1.1\n" +
                            "Host: cxhz.hep.com.cn\n";

    public void getText() throws IOException {

        Socket socket = new Socket("117.34.48.3",80);

        System.out.println("success connect");
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        pw.println(requestTextMeg);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        String readData = null;
        File file = new File("src\\homework\\download.txt");
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while (!(readData = br.readLine()).equals("")){
            System.out.println(readData);
        }
        while ((readData = br.readLine()) != null){
           fileOutputStream.write(readData.getBytes(StandardCharsets.UTF_8));
           fileOutputStream.write("\r\n".getBytes(StandardCharsets.UTF_8));
           fileOutputStream.flush();
        }
        fileOutputStream.close();
        socket.close();
    }

    /**
     * 由于获取图片的时候响应报文的实体体里含有各类图片信息，比如PNG文件标签，depth, colorType, compression, filter，interlace等
     * 所以单纯保存实体体到本地并不能直接显示图片
     * @throws IOException
     */
    public void getImg() throws IOException {
        Socket socket = new Socket("117.34.48.242",80);

        System.out.println("success connect");
        PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
        pw.println(requestTextMeg);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        String readData = null;
        File file = new File("src\\homework\\img.txt");
        if (!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        while (!(readData = br.readLine()).equals("")){
            System.out.println(readData);
        }
        while ((readData = br.readLine()) != null){
            fos.write(readData.getBytes(StandardCharsets.UTF_8));
            fos.write("\r\n".getBytes(StandardCharsets.UTF_8));
            fos.flush();
        }
        fos.close();
        socket.close();
    }
    public static void main(String[] args) throws IOException {
       // new HttpGet().getText();
        new HttpGet().getImg();

    }

}
