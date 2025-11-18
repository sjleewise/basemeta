package kr.wise.executor.dao.testmain;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import kr.wise.commons.ConnectionHelper;
import kr.wise.commons.Utils;
import kr.wise.executor.dm.DmnResult;

public class DateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Pattern.matches("^([1-9]|0[1-9]|1[012])$", "02:32"));
		System.out.println(Pattern.matches("^([1-9]|0[1-9]|1[012])$", "20191014031214"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY.MM.DD");
		sdf.setLenient(false);
		
		try {
//			System.out.println(sdf.parse("02:32"));
//			System.out.println(sdf.parse("20191014031214"));
//			System.out.println(sdf.parse("2019-10-14"));
			System.out.println(sdf.parse("2019.12.19"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
