package kr.project;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;
import javax.swing.ImageIcon;
import org.json.*;
import kr.soldesk.AddressVO;

public class NaverMap implements ActionListener{
	Project01_F naverMap;
	public NaverMap(Project01_F naverMap) {
		this.naverMap=naverMap;
	}
	//클릭하면 실행되는 부분
	@Override
	public void actionPerformed(ActionEvent e) {
		String clientId = "159r6z3668";
        String clientSecret = "fDVW2gM3o7lUnWzT4POjEbfLK0ApFSGRlQ1CPMNP";
        AddressVO vo=null;
        try {
        	//입력한 주소 가져오기
        	String address =naverMap.address.getText();
            String addr = URLEncoder.encode(address, "UTF-8");//입력 공백도 문자처리해줘야한다.
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; 
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);            
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8")); 
            } else {  
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer(); //문자열 추가 변경시 사용
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            JSONTokener tokener=new JSONTokener(response.toString());
            JSONObject object=new JSONObject(tokener);
            System.out.println(object);
            
            JSONArray arr=object.getJSONArray("addresses");	            
		    for(int i=0;i<arr.length();i++){
		  	 JSONObject temp = (JSONObject) arr.get(i);
		  	 // AddressVO 객체에 주소 정보를 저장
		  	 vo=new AddressVO();
		  	 vo.setRoadAddress((String)temp.get("roadAddress"));
		  	 vo.setJibunAddress((String)temp.get("jibunAddress"));
		  	 vo.setX((String)temp.get("x"));
		  	 vo.setY((String)temp.get("y"));
		  	 System.out.println(vo);
		   } 
		    //vo를 매개변수로 하여 지도 가져옴
		    map_service(vo);
         } catch (Exception err) {
            System.out.println(err);
        }
	}
	public void map_service(AddressVO vo) {
		String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
     	try {
     		String pos=URLEncoder.encode(vo.getX() + " " + vo.getY(), "UTF-8");
	        URL_STATICMAP += "center=" + vo.getX() + "," + vo.getY();
	        URL_STATICMAP += "&level=16&w=700&h=500";
	        URL_STATICMAP += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(vo.getRoadAddress(), "UTF-8");;
	        URL url = new URL(URL_STATICMAP);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "159r6z3668");
            con.setRequestProperty("X-NCP-APIGW-API-KEY", "fDVW2gM3o7lUnWzT4POjEbfLK0ApFSGRlQ1CPMNP");
            int responseCode = con.getResponseCode();
            BufferedReader br;            
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File(tempname + ".jpg");   
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            	ImageIcon img=new ImageIcon(f.getName());
            	naverMap.imageLabel.setIcon(img);           	
                naverMap.resAddress.setText(vo.getRoadAddress());
                naverMap.jibunAddress.setText(vo.getJibunAddress());
                naverMap.resX.setText(vo.getX());
                naverMap.resY.setText(vo.getY());
            } else {  // 에러 발생
                System.out.println(responseCode);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}
}
