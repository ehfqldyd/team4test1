package work.util;

import java.util.Date;
import java.util.Locale;

public class UnitTest {

	public static void main(String[] args) {
//		Date date =new Date();
//		System.out.println(date);
//		String date1 =Utility.getCurrentDate();
//		System.out.println(date1);
//		System.out.println(Utility.getCurrentDate("YYYY.MM.dd"));
//		System.out.println(Utility.getCurrentDate("HH:mm"));
//		System.out.println(Utility.getCurrentDate("[a] hh:mm"));
//		System.out.println(Utility.getCurrentDate("[a] hh:mm",Locale.KOREA));
		
//		//숫자데이터 천단위마다 컴마표기
//		int data1 = 123456789;
//		String convertData =  Utility.convertNumber(data1);
//		System.out.println(convertData);
//		
//		//메서드명 : convertCurrency(); 
//		String convertCurrency = Utility.convertCurrency(data1);
//		System.out.println(convertCurrency);
//		
//		String convertCurrencyLocale =Utility.convertCurrency(data1,Locale.CANADA);
//		System.out.println(convertCurrencyLocale);
		String data2 = "abcdefg";
		String convertSecureCode = Utility.convertSecureCode(data2);
		System.out.println(convertSecureCode);
	}

}
