package kr.project;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClient_02 {
	public static void main(String[] args) {
		// 프로그램 시작
		
		System.out.println("입력:");
		Scanner scan = new Scanner(System.in);
		String msg = scan.nextLine(); 
		
		if(msg != null && msg.trim().length() > 0){
			Socket s = null;
			try {
				//서버의 아이피와 포트
				s = new Socket("1.236.112.193", 3001);// 서버 접속!!
				
				// 문자열을 서버로 보내기 위해 스트림 준비
				PrintWriter out = 
						new PrintWriter(s.getOutputStream());
				
				// 서버로 문자열 보내기
				out.write(msg);
				
				// 스트림에 적재한 내용을 비워라
				out.flush();
				
				if(out != null)
					out.close(); // 스트림 닫기
				
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					if(s != null)
						s.close(); // 소켓 닫기
						
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}
}