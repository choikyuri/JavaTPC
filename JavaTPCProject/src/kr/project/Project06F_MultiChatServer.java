package kr.project;
import java.io.*;
import java.net.*;
import java.util.*;
public class Project06F_MultiChatServer {
	HashMap clients;
	Project06F_MultiChatServer() {
		clients = new HashMap();
		Collections.synchronizedMap(clients); //동기화를 맞추어서 thread 충돌 막음
	}
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try{
			serverSocket = new ServerSocket(9999);
			System.out.println("start server...");
			while(true) {
				socket = serverSocket.accept(); //서버에서 만들었지만 클라이언트 정보가 들어있음
				//IP와 Port 출력
				System.out.println(socket.getInetAddress()+":"+
						socket.getPort()+" connect!");
				//thread 생성
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start(); // run()
			}
		}catch(Exception e) {e.printStackTrace();}
	}
	void sendToAll(String msg) {//브로드캐스팅 기능
		Iterator iterator = clients.keySet().iterator();
		while(iterator.hasNext()) {
			try {//모든 클라이언트에게 알림
				DataOutputStream out = 
					(DataOutputStream)clients.get(iterator.next());
				out.writeUTF(msg);
			}catch(IOException e) {
				e.printStackTrace();}
		}
	}
	public static void main(String[] args) {
		new Project06F_MultiChatServer().start();
	}
	//inner class
	class ServerReceiver extends Thread {
		Socket socket; 
		DataInputStream in; 
		DataOutputStream out;
		//클라이언트 소켓으로 생성자 구현
		ServerReceiver(Socket socket) {
			this.socket = socket;
			try{//클라언트 입출력 스트림 생성
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}catch(Exception e) {e.printStackTrace();}
		}
		public void run() {
			String name = "";
			try{
				name = in.readUTF(); // park, lee
				if (clients.get(name) != null) {//같은 이름이 존재
					out.writeUTF("#Aleady exist name : "+name);
					out.writeUTF("#Please reconnect by other name");
					System.out.println(socket.getInetAddress()+":"+
							socket.getPort()+" disconnect!");
					in.close();
					out.close();
					socket.close();
					socket = null;
				} else {//같은 이름 존재하지 않는 경우
					sendToAll("#"+name+" join!");
					clients.put(name, out);
					while(in != null) {
						sendToAll(in.readUTF()); 
						}
				}
			}catch(IOException e) { e.printStackTrace();
			}finally{
				if (socket != null) {
					sendToAll("#"+name+" exit!");
					clients.remove(name);
					System.out.println(socket.getInetAddress()+":"+
							socket.getPort()+" disconnect!");
				}
			}
		}
	}
}
