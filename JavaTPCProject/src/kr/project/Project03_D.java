package kr.project;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kr.soldesk.ExcelVO;
//키보드로 도서 정보 요청하여 Excel에 저장하기
public class Project03_D {
	   public static void main(String[] args) {
		  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));//키보드로 데이터 입력
		  try {
			System.out.print("책제목:");
			String title=br.readLine();
			System.out.print("책저자:");
			String author=br.readLine();
			System.out.print("출판사:");
			String company=br.readLine();
			
			ExcelVO vo=new ExcelVO(title, author, company);
			getIsbnImage(vo);
		  } catch (Exception e) {
			e.printStackTrace();
		 }
	   }

	  private static void getIsbnImage(ExcelVO vo) {
		 try {
			 
			String openApi="https://openapi.naver.com/v1/search/book_adv.xml?d_titl=" //요청 URL에서 확인
					+ URLEncoder.encode(vo.getTitle(), "UTF-8") //요청변수에서 확인
					+ "&d_auth="+URLEncoder.encode(vo.getAuthor(), "UTF-8")
					+ "&d_publ="+URLEncoder.encode(vo.getCompany(), "UTF-8");
			URL url=new URL(openApi);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", "R3GgXvVEJ6c0E6FCb1gz");
			con.setRequestProperty("X-Naver-Client-Secret", "QJ4_VkuzeI");
			int responseCode=con.getResponseCode();
			
			 BufferedReader br1=null;
			 if(responseCode==200) {
				 br1=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));			 
			 }else {
				 br1=new BufferedReader(new InputStreamReader(con.getErrorStream()));
			 }
			 String inputLine;
			 StringBuffer response=new StringBuffer();
			 while((inputLine=br1.readLine())!=null) {
				 response.append(inputLine);
			 }
			 br1.close();
			 //System.out.println(response.toString());
			 // isbn, image
			 Document doc=Jsoup.parse(response.toString());
			 //System.out.println(doc.toString());
			 Element total=doc.select("total").first();
			// --------------------------컴파일---------
			 if(!(total.text().equals("0"))) {
				 Element isbn=doc.select("isbn").first();
				 String isbnStr=isbn.text();
				 System.out.println(isbnStr);	 //8994492046 9788994492049	 
				 String isbn_find=isbnStr.split(" ")[1]; //공백으로 자른뒤 첫번째(8994492046) isbn만 가져오기
				 vo.setIsbn(isbn_find); // (International Standard Book Number, ISBN)
				 //-------------------------------------
				 String imgDoc=doc.toString();
				 String imgTag=imgDoc.substring(imgDoc.indexOf("<img>")+5); //<img>테그 5글자 뒤부터 가져옴
				 //System.out.println(imgTag); // https://bookthumb-phinf.pstatic.net/cover/160/313/16031391.jpg?type=m1&amp;udate=20200108
				 String imgURL=imgTag.substring(0, imgTag.indexOf("?")); //처음부터 ?전까지 잘라서 가져옴(https://bookthumb-phinf.pstatic.net/cover/160/313/16031391.jpg)
				 System.out.println(imgURL);
				 String fileName=imgURL.substring(imgURL.lastIndexOf("/")+1);//마지막 /부터 끝까지
				 System.out.println(fileName);
				 vo.setImgurl(fileName);
				 
				 System.out.println(vo);
				 
			 }else {
				 System.out.println("검색데이터가 없습니다.");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	  }
	}
