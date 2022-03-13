package kr.project;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import kr.soldesk.DownloadBroker;

public class Project02_B {
	public static void main(String[] args) {
      String url="https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
      //InputStreamReader 는 Reader의 하위 클래스로서 BufferedReader 의 생성자의 인자로 전달 할 수 있다
      BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); //System.in:키보드로부터 읽어들임
      try {
		System.out.print("[입력->년(yyyy)-월(mm)-일(dd)]:");
		String bible=br.readLine(); //한줄씩 읽어들임
		url=url+"&Base_de="+bible+"&bibleType=1"; //Base_de :날짜 / bibleType :기본1로설정
		System.out.println("================================");
		Document doc=Jsoup.connect(url).post();
		Element bible_text=doc.select(".bible_text").first(); //주제목 /.first():첫번째 값
		System.out.println(bible_text.text());
		
		Element bibleinfo_box=doc.select(".bibleinfo_box").first(); //소제목
		System.out.println(bibleinfo_box.text());
		
		Elements liList=doc.select(".body_list > li"); //내용
        for(Element li : liList) {
        	System.out.print(li.select(".num").first().text()+":"); //내용안에 번호
        	System.out.println(li.select(".info").first().text());  //말씀
        }
		// 리소스 다운로드(mp3, image)
        
        Element tag=doc.select("source").first(); // //*[@id="video"]/source
        String dPath=tag.attr("src").trim(); //속성을 붙임
        System.out.println(dPath); // 컴파일후 입력된 날짜를 주소로 받아옴 http://meditation.su.or.kr/meditation_mp3/2019/20191010.mp3
        String fileName=dPath.substring(dPath.lastIndexOf("/")+1); //주소의 /이후 파일명만 빼서 받아옴
        System.out.println(fileName);
        /*
        Element tag=doc.select(".img> img").first();
        String dPath="https://sum.su.or.kr:8888"+tag.attr("src").trim();
        System.out.println(dPath); // https://sum.su.or.kr:8888/attach/X07/2c06c62f3695489a8ff525a6ed138395.jpg
        String fileName=dPath.substring(dPath.lastIndexOf("/")+1);
        */
        Runnable r=new DownloadBroker(dPath, fileName); 
        Thread dLoad=new Thread(r); //스레드 구현
        dLoad.start(); //다운로드 시작
        for(int i=0; i<10;i++) {
        	try {
				Thread.sleep(1000); //1초
			} catch (Exception e) {
				e.printStackTrace();
			}
        	System.out.print(""+(i+1));
         }
          System.out.println();
          System.out.println("===============================");
	    } catch (Exception e) {
		e.printStackTrace();
	  }
	}
}







