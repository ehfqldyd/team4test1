package work.util;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utility {
   /**
    * @return ��ȯ
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
    * @param length ����
    * @return ��ȯ
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
    * @return ��ȯ
    */
   public static String getSecureCodeNumberAndAlphabet() {
      return getSecureCodeNumberAndAlphabet(4);
   }

   /**
    * @param length ����
    * @return ��ȯ
    */
   public static String getSecureCodeNumberAndAlphabet(int length) {
      Random random = new Random((long)(System.nanoTime() * Math.random()));
      StringBuilder code = new StringBuilder();
      for (int i = 0; i < 2; i++) {
         code.append(random.nextInt(10));  // 0 ~ 9 (10���� ����)
      }
      
      for (int i = 2; i < length; i++) {
         code.append((char)(random.nextInt(26)+65));
         //code.append((char)(Math.random()*10)+65);
         // a b c d e f g h i j => 0 ~ 9 => ���� 10�ڸ�      
      }
      return code.toString();
   }

   /**
    * @return ��ȯ
    */
   public static String getSecureCodeAlphabet() {
      return getSecureCodeAlphabet(4);
   }
   
   /**
    * @param isUpperCase �ø�
    * @return ��ȯ
    */
   public static String getSecureCodeAlphabet(boolean isUpperCase) {
      if (isUpperCase) {
         return getSecureCodeAlphabet(4);
      } else {
         return getSecureCodeAlphabet(4).toLowerCase();
      }
   }   
   
   /**
    * @param length ����
    * @return ��ȯ
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
    * @param length ����
    * @param isUpperCase �ø�
    * @return ��ȯ
    */
   public static String getSecureCodeAlphabet(int length, boolean isUpperCase) {
      if (isUpperCase) {
         return getSecureCodeAlphabet(6);
      } else {
         return getSecureCodeAlphabet(6).toLowerCase();
      }
   }
   
   /** �ø����� */
   /**
    * @param data ������
    * @return ��ȯ
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
    * @param data ������
    * @return ��ȯ
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

   /** �������� */
   /**
    * @param data ������
    * @return ��ȯ
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
    * @param data ������
    * @return ��ȯ
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

   /** ���� ��¥�� �⺻������ ���ڿ��� ��ȯ 
    * 
    * @see java.text.SimpleDateFormat ��
    * @see java.text.DateFormat �� 
    * @see java.util.Date ��
    * @see java.util.Locale ��
    */
   /**
    * @return ��ȯ
    */
   public static String getCurrentDate() {
//      // ��¥����(�⺻) : ��4�ڸ�/��2�ڸ�/��2�ڸ�
//      String pattern = "yyyy/MM/dd";
//      
//      // ��¥���� ��ü ���� : SimpleDateFormat
//      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//      
//      // ��¥���İ�ü�� �޼��带 ����ؼ� ���糯¥�� ������ ������ ���ڿ� ��ȯ
//      return dateFormat.format(new Date());
   
      return getCurrentDate("yyyy/MM/dd");
   }

   /**
    * @param pattern ����
    * @return ��ȯ
    */
   public static String getCurrentDate(String pattern) {
      // ��¥����(�⺻) : ��4�ڸ�/��2�ڸ�/��2�ڸ�
      //String pattern = "yyyy/MM/dd";
      
      // ��¥���� ��ü ���� : SimpleDateFormat
      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
      
      // ��¥���İ�ü�� �޼��带 ����ؼ� ���糯¥�� ������ ������ ���ڿ� ��ȯ
      return dateFormat.format(new Date());
   }
   
   /**
    * @param pattern ����
    * @param locale ��ġ
    * @return ��ȯ
    */
   public static String getCurrentDate(String pattern, Locale locale) {
//      // ��¥����(�⺻) : ��4�ڸ�/��2�ڸ�/��2�ڸ�
//      //String pattern = "yyyy/MM/dd";
//      
//      // ��¥���� ��ü ���� : SimpleDateFormat
//      SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
//      
//      // ��¥���İ�ü�� �޼��带 ����ؼ� ���糯¥�� ������ ������ ���ڿ� ��ȯ
//      return dateFormat.format(new Date());
      
      return new SimpleDateFormat(pattern, locale).format(new Date());
   }
   
   /**
    * ���� �����͸� õ�������� �ĸ�ǥ�� ��ȯ 
    * 
    * @see java.text.NumberFormat ��������
    * 
    * @param data ������
    * @return ��ȯ
    */
   public static String convertNumber(long data) {
//      NumberFormat numberFormat = NumberFormat.getInstance();
//      return numberFormat.format(data);
      
      return NumberFormat.getInstance().format(data);
   }
   
   /** ��ȭ ���ڵ����� �Ǿտ� ȭ�����ǥ���ϰ�, õ�������� �ĸ�ǥ��  
    *  �⺻ȭ����� : �� 
    */
   /**
    * @param data ������
    * @return ��ȯ
    */
   public static String convertCurrency(long data) {
      return convertCurrency(data, Locale.KOREA);
   }
   
   /** ��ȭ ���ڵ����� �Ǿտ� ȭ�����ǥ���ϰ�, õ�������� �ĸ�ǥ��  
    *  �ƱԸ�Ʈ�� ���޹��� �������� ȭ��ܵ� 
    */
   /**
    * @param data ������
    * @param locale ��ġ
    * @return ��ȯ
    */
   public static String convertCurrency(long data, Locale locale) {
      NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
      return numberFormat.format(data);
      
//      return NumberFormat.getCurrencyInstance(locale).format(data);
   }
   
   
   /**
    * @param data ������
    * @return ��ȯ
    */
   public static String convertSecureCode(String data) {
      StringBuilder convertData = new StringBuilder(data.substring(0, 2));
      for (int i = 2; i < data.length(); i++) {
         convertData.append("*");
      }
      return convertData.toString();
   }

   /**
    * @param data ������
    * @param length ����
    * @return ��ȯ
    */
   public static String convertSecureCode(String data, int length) {
      StringBuilder convertData = new StringBuilder(data.substring(0, length));
      for (int i = length; i < data.length(); i++) {
         convertData.append("*");
      }
      return convertData.toString();
   }
/** �� ���ø����̼��� get ����� �ѱ� ���ڵ� ��ȯ �޼��� */
   /**
    * @param data ������
    * @return ��ȯ
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