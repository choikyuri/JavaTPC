package kr.project;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_D {
	public static void main(String[] args) {
        
		//String apiURL="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=";
		String client_id = "159r6z3668";
        String client_secret = "fDVW2gM3o7lUnWzT4POjEbfLK0ApFSGRlQ1CPMNP";
		BufferedReader io=new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("주소를 입력하세요:");
			String address=io.readLine();
			String addr=URLEncoder.encode(address, "UTF-8");
			String reqUrl="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
			
			URL url=new URL(reqUrl);
			//HttpURLConnection:연결해주는 API클래스
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id); //API정보 입력(가이드 참고)
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);//API정보 입력(가이드 참고)
			BufferedReader br; //BufferedReader :라인단위로 읽어들임
			int responseCode=con.getResponseCode(); // 200
			if(responseCode==200) {
				//바이트스트림(getInputStream())을 문자스트림(new InputStreamReader)으로 생성
                br=new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));//한글정보 인코딩			
			}else {
				//getErrorStream() : 에러 스트림
				br=new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String line; 
			StringBuffer response=new StringBuffer(); // StringBuffer :스트림버퍼 객체생성
			while((line=br.readLine())!=null) {
				    response.append(line); // 스트림버퍼에 담기			
			}
			br.close();
			
			JSONTokener tokener=new JSONTokener(response.toString()); //JSON객체
			JSONObject object=new JSONObject(tokener); //Object로 변환
			System.out.println(object.toString());
			
			JSONArray arr=object.getJSONArray("addresses"); //주소가 여러개일수 있으므로 반복문으로 출력
			for(int i=0;i<arr.length();i++) {
				  JSONObject temp=(JSONObject) arr.get(i);
				  System.out.println("address:" + temp.get("roadAddress"));
				  System.out.println("jibunAddress:" + temp.get("jibunAddress"));
				  System.out.println("경도:" + temp.get("x"));
				  System.out.println("위도:" + temp.get("y"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	

	}

}
