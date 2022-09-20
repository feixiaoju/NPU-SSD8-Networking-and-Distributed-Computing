package exec1;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class socketThread implements Runnable{

    private Socket socket;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private String rootPath;
    private String currentPath;

    private BufferedReader br;
    private PrintWriter pw;
    private FileInputStream fis;

    static final int UDP_PORT = 2023;
    static final String HOST = "127.0.0.1";

    public void initStream(){
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public socketThread(Socket socket,String path){
       this.socket = socket;
       this.currentPath = path;
       this.rootPath = path;
    }

    @Override
    public void run() {
        initStream();
        String userCommand;
        String response;
        try {
            while (true){
                userCommand = br.readLine();
                System.out.println(userCommand);

                if (userCommand.equals("bye")){
                    pw.println("command-quit");
                }else if (userCommand.equals("ls")){
                    pw.println("command-ls");
                    showDirectory(currentPath);
                    pw.println("end");
                }else if (userCommand.startsWith("cd")){
                    String[] commands = userCommand.split(" ");
                    if (commands.length == 1 && userCommand.equals("cd..")){
                        pw.println("command-cd");
                        jumpToDirectory();
                        pw.println("end");
                    }else if (commands.length == 2 && isDirectory(commands[1])){
                            pw.println("command-cd");
                            jumpToDirectory(commands[1]);
                            pw.println("end");
                    }
                    else {
                        pw.println("error dir is wrong or isn't exist");
                    }
                }else if (userCommand.startsWith("get")){
                    String[] commands = userCommand.split(" ");
                    if (commands.length == 2){
                        sendFile(commands[1]);
                    }else {
                        pw.println("error too many file parameters");
                    }
                }
                else {
                    pw.println("error unknown command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendFile(String fileName) throws IOException {
        String sendFileName = currentPath + "\\" + fileName;
        File file = new File(sendFileName);
        if (file.isDirectory()){
            pw.println("error this is a directory!");
        }else if (!file.exists()){
            pw.println("error file isn't exist!");
        }else {
            pw.println("command-file");
            pw.println(fileName);
            pw.println(file.length());

            DatagramSocket socket = new DatagramSocket();

            InetAddress inetAddress = InetAddress.getLocalHost();


            byte[] bytes = new byte[1024];
            System.out.println(file.length());
            FileInputStream fis = new FileInputStream(file);
            DatagramPacket packet;
            int length = 0;
            while ((length = fis.read(bytes)) != -1){
                packet = new DatagramPacket(bytes,0,bytes.length,inetAddress,2020);
                socket.send(packet);
            }

            socket.close();
            fis.close();
        }
    }

    private boolean isDirectory(String dir){
        boolean flag = true;
        File file = new File(currentPath+"\\"+dir);
        if(!file.isDirectory()&&!file.isFile()) {
            flag = false;
        }
        return flag;
    }

    private void jumpToDirectory(String dir) {
        File file = new File(currentPath + "\\" +dir);
        if(file.isFile()){
            pw.println("your input <"+dir+"> is a file");
        } else{
            currentPath = currentPath + "\\" + dir;
            pw.println(currentPath + " > OK");
        }

    }


    private void jumpToDirectory() {
        if(currentPath.equals(rootPath)){
            pw.println("this dir is root!");
        } else {
            String path = "";
            StringTokenizer stringTokenizer = new StringTokenizer(currentPath,"\\");

            int count = stringTokenizer.countTokens();

            for(int i=0;i<count-2;i++) {
                path = path + stringTokenizer.nextToken();
                path += "\\";
            }
            path = path+stringTokenizer.nextToken();
            currentPath = path;
            pw.println(currentPath + " > OK");
        }

    }
    private void showDirectory(String dir) {
        File directory = new File(dir);
        File[] files = directory.listFiles();
        for(File file:files) {
            if(file.isDirectory()) {
                pw.print("<dir>          "+file.getName() +"    ");
                int i = 20-file.getName().length();
                if(i > 1){
                    for(int j = 0;j < i;j++){
                        pw.print(" ");
                    }
                }else{
                    pw.print("  ");
                }
                pw.print(file.length()/1000+"KB"+"\n");

            } else if(file.isFile()) {
                pw.print("<file>         "+file.getName() +"    ");
                int i = 20-file.getName().length();
                if(i > 1){
                    for(int j = 0;j < i;j++){
                        pw.print(" ");
                    }
                }else {
                    pw.print("  ");
                }
                pw.print(file.length()/1000+"KB"+"\n");
            }
        }
        if (files.length==0) {
            pw.println("this dir is empty");
        }
    }
}
