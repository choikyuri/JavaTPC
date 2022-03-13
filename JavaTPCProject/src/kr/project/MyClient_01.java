package kr.project;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyClient_01 {
	public static void main(String[] args) 
		       throws UnknownHostException, IOException {
// 프로그램 시작

// 서버 접속은 Socket을 생성하는 것이다.
// 소켓을 생성하여 서버의 ip주소와 포트를 입력.
Socket s = new Socket("1.236.112.193", 3000);
}
}