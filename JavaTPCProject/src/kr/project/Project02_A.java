package kr.project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Project02_A {
	
	public static void main(String[] args) {
      // Jsoup API
      String url="https://sports.news.naver.com/wfootball/index.nhn";
      Document doc=null; // Document : Jsoup 얻어온 결과 HTML 전체문서(DOM=>Document Object Model)
      try {
		doc=Jsoup.connect(url).get(); //요청
	  } catch (Exception e) {
		e.printStackTrace();
	  }
      Elements element=doc.select("div.home_news"); //Elements:Document의 요소들 또는 노드들
      String title=element.select("h2").text().substring(0, 4);//앞에서부터 4글자
      System.out.println("===============================");
      System.out.println(title);
      System.out.println("===============================");
      for(Element el : element.select("li")) {
    	  System.out.println(el.text());    	  
      }
      System.out.println("===============================");
	}
}
