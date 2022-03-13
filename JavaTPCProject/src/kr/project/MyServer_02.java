package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer_02 extends Thread{

	ServerSocket ss;
	
	public MyServer_02(){
		try {
			ss = new ServerSocket(3001);
			System.out.println("서버 시작!");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		// 프로그램의 시작!
		new MyServer_02().start();
	}

	@Override
	public void run() {
		// thread가 해야할 일
		// (접속자를 받고, 바로 문자열을 받아낸다.
		//   그 문자열을 ip와 함께 화면에 출력! )
		while(true){
			try {
				Socket s = ss.accept(); // 접속자가 발생할 때까지 
							// 대기한다.
				// 클라이언트는 접속하자마자 문자열을 보내기 때문에
				// 여기서는 문자열을 받아야 한다.
				BufferedReader reader = 
					new BufferedReader(
					new InputStreamReader(s.getInputStream()));
				
				String msg = reader.readLine();//접속자가보낸 메세지								
				String ip = s.getInetAddress().getHostAddress();
				
				System.out.println(ip+" : "+msg);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}