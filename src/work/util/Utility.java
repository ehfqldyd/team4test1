package work.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utility {
   /**
    * @return 반환
    */
   public static String getSecureCode() {
//      StringBuilder nos = new StringBuilder();
//      for (int i = 0; i < 4; i++) {
//         nos.append((int)(Math.random() * 10));
//      }
//      return nos.toString();
      
      return getSecureCode(4);
   }

   /**
    * @param length 길이
    * @return 반환
    */
   public static String getSecureCode(int length) {
      StringBuilder code = new StringBuilder();
//      for (int i = 0; i < length; i++) {
//         code.append((int)(Math.random() * 10));
//      };
      
      Random random = new Random((long)(System.nanoTime() * Math.random()));
      for (int i = 0; i < length; i++) {
         code.append(random.nextInt(10));
      }
            
      return code.toString();      
   }
   
   /**
    * @return 반환
    */
   public static String getSecureCodeNumberAndAlphabet() {
      return getSecureCodeNumberAndAlphabet(4);
   }

   /**
    * @param length 길이
    * @return 반환
    */
   public static String getSecureCodeNumberAndAlphabet(int length) {
      Random random = new Random((long)(System.nanoTime() * Math.random()));
      StringBuilder code = new StringBuilder();
      for (int i = 0; i < 2; i++) {
         code.append(random.nextInt(10));  // 0 ~ 9 (10개의 숫자)
      }
      
      for (int i = 2; i < length; i++) {
         code.append((char)(random.nextInt(26)+65));
         //code.append((char)(Math.random()*10)+65);
         // a b c d e f g h i j => 0 ~ 9 => 영문 10자리      
      }
      return code.toString();
   }

   /**
    * @return 반환
    */
   public static String getSecureCodeAlphabet() {
      return getSecureCodeAlphabet(4);
   }
   
   /**
    * @param isUpperCase 올림
    * @return 반환
    */
   public static String getSecureCodeAlphabet(boolean isUpperCase) {
      if (isUpperCase) {
         return getSecureCodeAlphabet(4);
      } else {
         return getSecureCodeAlphabet(4).toLowerCase();
      }
   }   
   
   /**
    * @param length 길이
    * @return 반환
    */
   public static String getSecureCodeAlphabet(int length) {
      Random random = new Random();
      StringBuilder code = new StringBuilder();
      
      for (int i = 0; i < length; i++) {
         code.append((char)(random.nextInt(26)+65));
      }
      return code.toString();
   }

   /**
    * @param length 길이
    * @param isUpperCase 올림
    * @return 반환
    */
   public static String getSecureCodeAlphabet(int length, boolean isUpperCase) {
      if (isUpperCase) {
         return getSecureCodeAlphabet(6);
      } else {
         return getSecureCodeAlphabet(6).toLowerCase();
      }
   }
   
   /** 올림차순 */
   /**
    * @param data 데이터
    * @return 반환
    */
   public static String[] ascSort(String[] data) {
      String tmp = null;
      for (int i = 0; i < data.length; i++) {
         for (int j = i+1; j < data.length; j++) {
            if (data[i].compareTo(data[j]) > 0) {
               tmp = data[i];
               data[i] = data[j];
               data[j] = tmp;
            }
         }
      }
      return data;
   }
   /**
    * @param data 데이터
    * @return 반환
    */
   public static int[] ascSort(int[] data) {
      int tmp = -1;
      for (int i = 0; i < data.length; i++) {
         for (int j = i+1; j < data.length; j++) {
            if (data[i] > (data[j])) {
               tmp = data[i];
               data[i] = data[j];
               data[j] = tmp;
            }
         }
      }
      return data;
   }

   /** 내림차순 */
   /**
    * @param data 데이터
    * @return 반환
    */
   public static String[] descSort(String[] data) {
      String tmp = null;
      for (int i = 0; i < data.length; i++) {
         for (int j = i+1; j < data.length; j++) {
            if (data[i].compareTo(data[j]) < 0) {
               tmp = data[i];
               data[i] = data[j];
               data[j] = tmp;
            }
         }
      }
      return data;
   }
   
   /**
    * @param data 데이터
    * @return 반환
    */
   public static int[] descSort(int[] data) {
      int tmp = -1;
      for (int i = 0; i < data.length; i++) {
         for (int j = i+1; j < data.length; j++) {
            if (data[i] < (data[j])) {
               tmp = data[i];
               data[i] = data[j];
               data[j] = tmp;
            }
         }
      }
      return data;
   }

   /** 현재 날짜를 기본형식의 문자열로 반환 
    * 
    * @see java.text.SimpleDateFormat ㅇ
    * @see java.text.DateFormat ㅇ 
    * @see java.util.Date ㅇ
    * @see java.util.Locale ㅇ
    */
   /**
    * @return 반환
    */
   public static String getCurrentDate() {
//      // 날짜형식(기본) : 년4자리/월2자리/일2자리
//      String pattern = "yyyy/MM/dd";
//      
//      // 날짜형식 객체 생성 : SimpleDateFormat
//      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//      
//      // 날짜형식객체의 메서드를 사용해서 현재날짜를 지정한 형식의 문자열 반환
//      return dateFormat.format(new Date());
   
      return getCurrentDate("yyyy/MM/dd");
   }

   /**
    * @param pattern 패턴
    * @return 반환
    */
   public static String getCurrentDate(String pattern) {
      // 날짜형식(기본) : 년4자리/월2자리/일2자리
      //String pattern = "yyyy/MM/dd";
      
      // 날짜형식 객체 생성 : SimpleDateFormat
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      
      // 날짜형식객체의 메서드를 사용해서 현재날짜를 지정한 형식의 문자열 반환
      return dateFormat.format(new Date());
   }
   
   /**
    * @param pattern 패턴
    * @param locale 위치
    * @return 반환
    */
   public static String getCurrentDate(String pattern, Locale locale) {
//      // 날짜형식(기본) : 년4자리/월2자리/일2자리
//      //String pattern = "yyyy/MM/dd";
//      
//      // 날짜형식 객체 생성 : SimpleDateFormat
//      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
//      
//      // 날짜형식객체의 메서드를 사용해서 현재날짜를 지정한 형식의 문자열 반환
//      return dateFormat.format(new Date());
      
      return new SimpleDateFormat(pattern, locale).format(new Date());
   }
   
   /**
    * 숫자 데이터를 천단위마다 컴마표기 변환 
    * 
    * @see java.text.NumberFormat 숫자형식
    * 
    * @param data 데이터
    * @return 반환
    */
   public static String convertNumber(long data) {
//      NumberFormat numberFormat = NumberFormat.getInstance();
//      return numberFormat.format(data);
      
      return NumberFormat.getInstance().format(data);
   }
   
   /** 통화 숫자데이터 맨앞에 화폐단위표기하고, 천단위마다 컴마표기  
    *  기본화폐단위 : 원 
    */
   /**
    * @param data 데이터
    * @return 반환
    */
   public static String convertCurrency(long data) {
      return convertCurrency(data, Locale.KOREA);
   }
   
   /** 통화 숫자데이터 맨앞에 화폐단위표기하고, 천단위마다 컴마표기  
    *  아규먼트로 전달받은 로케일의 화폐단뒤 
    */
   /**
    * @param data 데이터
    * @param locale 위치
    * @return 반환
    */
   public static String convertCurrency(long data, Locale locale) {
      NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
      return numberFormat.format(data);
      
//      return NumberFormat.getCurrencyInstance(locale).format(data);
   }
   
   
   /**
    * @param data 데이터
    * @return 반환
    */
   public static String convertSecureCode(String data) {
      StringBuilder convertData = new StringBuilder(data.substring(0, 2));
      for (int i = 2; i < data.length(); i++) {
         convertData.append("*");
      }
      return convertData.toString();
   }

   /**
    * @param data 데이터
    * @param length 길이
    * @return 반환
    */
   public static String convertSecureCode(String data, int length) {
      StringBuilder convertData = new StringBuilder(data.substring(0, length));
      for (int i = length; i < data.length(); i++) {
         convertData.append("*");
      }
      return convertData.toString();
   }
/** 웹 어플리케이션의 get 방식의 한글 인코딩 변환 메서드 */
   /**
    * @param data 데이터
    * @return 반환
    */
   public static String toKor(String data) {
      try{
         return new String(data.getBytes("8859_1"), "euc-kr");
   } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
   }
      return null;
   }
}