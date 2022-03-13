package kr.project;
import java.net.*;
import java.io.*;
public class Project06A_Server {
	public static void main(String[] args) {
        ServerSocket ss=null;
        try {
			ss=new ServerSocket(9999); 
			System.out.println("Server ready....");
		} catch (Exception e) {
           e.printStackTrace();
		}
        while(true) {
        	try {
		        Socket socket=ss.accept();	//상대방 정보와 연결수락
		        System.out.println("client connect success!");
		        //소켓생성:클라이언트 식별(상대방 정보 받음)
		        InputStream in=socket.getInputStream(); 
		        //입력 스트림 : 클라이언트 신호를 받음
		        DataInputStream dis=new DataInputStream(in);
		        String message=dis.readUTF(); //메세지 읽어들임
		        
		        //출력소켓생성 : 클라이언트에 신호 보냄
		        OutputStream out=socket.getOutputStream();
		        //출력 스트림
                DataOutputStream dos=new DataOutputStream(out);
                dos.writeUTF("[ECHO]"+message+"(from Server!)");//저장
		        
                dos.close();
		        dis.close();
		        socket.close();    
		        System.out.println("client socket close....");
			} catch (Exception e) {
              e.printStackTrace();
			}
        }//while
	}//main
}//class
