package kr.project;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import kr.soldesk.ExcelVO;
public class Project03_A {
	public static void main(String[] args) {
      String fileName="bookList.xls";
      List<ExcelVO> data=new ArrayList<ExcelVO>();  //컬렉션 생성
      try(FileInputStream fis=new FileInputStream(fileName)) {
    	  HSSFWorkbook workbook=new HSSFWorkbook(fis); //HSSFWorkbook : bookList.xls파일을 메모리에 적재시킴
    	  HSSFSheet sheet=workbook.getSheetAt(0); //메모리안에 sheet읽어들임
    	  Iterator<Row> rows=sheet.rowIterator(); //시트의 행을 순회해서 갯수 가져오기
    	  rows.next();//첫번째 줄은 지나가기 위한 코드
    	  String[] imsi=new String[5];//컬럼의 데이터를 넣기 위한 임시배열
    	  while(rows.hasNext()) {
    		  HSSFRow row=(HSSFRow) rows.next();//줄 가져오기
    		  Iterator<Cell> cells=row.cellIterator();//컬럼을 순회하여 컬럼갯수 가져오기
    		  int i=0;
    		  //셀데이터 가져오기
    		  while(cells.hasNext()) {
    			     HSSFCell cell=(HSSFCell) cells.next();
    			     imsi[i]=cell.toString();
    			     i++;
    		  }
    		  // 묶고(VO)->담고(List)
    		  ExcelVO vo=new ExcelVO(imsi[0],imsi[1],imsi[2],imsi[3],imsi[4]);
    		  data.add(vo);
    	  }
    	   showExcelData(data);   	  
	   } catch (Exception e) {
		e.printStackTrace();
	  }    
	}
	public static void showExcelData(List<ExcelVO> data) {
		for(ExcelVO vo : data) {
			System.out.println(vo);
		}
	}
}
