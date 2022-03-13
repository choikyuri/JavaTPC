package kr.project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer_01 extends Thread{
	
	ServerSocket ss;
	
	public MyServer_01(){
		
		try {
			// 서버소켓을 생성시에 서비스 포트번호를 지정한다.
			// 물론 클라이언트가 접속할 때 필요하다.
			// 서비스 포트번호의 범위는 약 2000번 이후의 번호를 사용!
			ss = new ServerSocket(3000);
			// 서버가 준비 되었다.
			
			System.out.println("서버 완료!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 스레드가 해야할 일들..
	@Override
	public void run() {
		// 무한반복 속에서 접속자들을 항상 받아들인다.
		while(true){
			try {
				Socket s = ss.accept();// 접속자를 받아들인다.
				//접속자가 올 때까지 아래 내용을 수행하지 않고 대기상태에 빠진다.
				
				//위에서 지정한 포트로 접속한 클라이언트의 ip주소를 가				져온다.
				String ip = s.getInetAddress().getHostAddress();
				
				System.out.println(ip+"님 왔다 감!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// 프로그램의 시작!		
		MyServer_01 ms = new MyServer_01();
		ms.start();
		
		
		//new MyServer().start();
		// 현재 객체가 Thread로부터 상속 받았으므로
		// 자신이 곧 스레드다.
		// 그래서 생성하자마자 start()호출하여 Thread가 시작하도록 했다.
	}
}
