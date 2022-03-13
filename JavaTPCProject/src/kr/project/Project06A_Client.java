package kr.project;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Project06A_Client {
	public static void main(String[] args) {
       try {
		   Socket socket=new Socket("127.0.0.1", 9999); // -------->accept()
    	   System.out.println("Connection Success!");
    	   Scanner scanner=new Scanner(System.in);
    	   String message=scanner.nextLine();
    	   //소켓 생성:서버 식별(상대방 정보 보냄)
    	   OutputStream out=socket.getOutputStream();
    	   //출력 스트림
    	   DataOutputStream dos=new DataOutputStream(out);
    	   dos.writeUTF(message);//메세지 보냄
 
    	   //입력소켓생성
    	   InputStream in=socket.getInputStream();
    	  //입력 스트림 : 서버 신호를 받음
    	   DataInputStream dis=new DataInputStream(in);
    	   System.out.println("Receive:"+dis.readUTF());
    	   
    	   dis.close();
    	   dos.close();
    	   socket.close();
	   } catch (Exception e) {
		  e.printStackTrace();
	   }		
	}
}
