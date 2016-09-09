package work.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {
	public static String getSecureCode(){
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < 4; i++) {
//					sb.append((int)(Math.random()*10));
//		}
		return getSecureCode(4);
	}
	public static String getSecureCode(int num){
		StringBuilder stringbuilder = new StringBuilder();
		for (int i = 0; i < num; i++) {
			stringbuilder.append((int)(Math.random()*10)); 
			
		}
		return stringbuilder.toString();
	}
	public static String getSecureCodeNumberAndAlphabet(){
		return getSecureCodeAlphabet(4);
	}	
	
	public static String getSecureCodeAlphabet(int length) {
		StringBuilder stringbd = new StringBuilder();
		for (int i = 0; i < 2; i++) {
			stringbd.append((int)(Math.random()*10));
		}
		for (int i = 0; i < length; i++) {
			stringbd.append((char)(65+Math.random()*26));
		}
		return stringbd.toString();
	}
	
	public static String[] ascSort(String[] hero){
		String temp = null;
		for (int i = 0; i < hero.length; i++) {
			for (int j = 0; j < hero.length; j++) {
				if (hero[i].compareTo(hero[j]) > 0) { //문자열비교 compareTo
					temp = hero[i];
					hero[i] = hero[j];
					hero[j] = temp;
				}
			}
			
		}
		return hero;
	}
	public static int []ascSort(int[] args){
		int temp = 0;
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args.length; j++) {
				if (args[i]<args[j]) {
					temp = args[i];
					args[i] = args[j];
					args[j] = temp;
				}
			}
		}
		return args;
	}
	
	public static String []descSort(String[] args){
		String temp = null;
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args.length; j++) {
				if (args[i].compareTo(args[j]) < 0) {
					temp = args[i];
					args[i] = args[j];
					args[j] = temp;
				}
			}
		}
		return args;
	}
	
	public static int[] descSort(int[] args){
		int temp = 0;
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args.length; j++) {
				if (args[i]>args[j]) {
					temp = args[i];
					args[i] = args[j];
					args[j] = temp;
				}
			}
		}
		return args;
	}
	//현재날짜불러오기
	public static String getCurrentDate(){
		String pattern = "yyyy/MM/dd";
		SimpleDateFormat simpleDate = new SimpleDateFormat(pattern);
		
		return simpleDate.format(new Date());
	}
	
	public static String getCurrentDate(String pattern){
		//String pattern = "yyyy/MM/dd";
		SimpleDateFormat simpleDate = new SimpleDateFormat(pattern);
		
		return simpleDate.format(new Date());
	}
	public static String getCurrentDate(String pattern,Locale locale){
//		String pattern = "yyyy/MM/dd";
//		SimpleDateFormat simpleDate = new SimpleDateFormat(pattern,locale);
//		
//		return simpleDate.format(new Date());
		return new SimpleDateFormat(pattern,locale).format(new Date());
	}
	
	public static String convertNumber(int data) {
//		NumberFormat numberFormat = NumberFormat.getInstance();
//		return numberFormat.format(data);
		
		return  NumberFormat.getInstance().format(data);
	}
	
	public static String convertCurrency(int data) {
//		NumberFormat numberFormat = NumberFormat.getInstance();
//		return numberFormat.format(data);
		
		return  NumberFormat.getCurrencyInstance().format(data);
	}
	
	public static String convertCurrency(int data,Locale locale) {
//		NumberFormat numberFormat = NumberFormat.getInstance();
//		return numberFormat.format(data);
		
		return  NumberFormat.getCurrencyInstance(locale).format(data);
	}
	
	public static String convertSecureCode(String data){
		StringBuilder strbd = new StringBuilder(data.substring(0,2));
		
		for (int i = 2; i < data.length(); i++) {
			strbd.append("*");
		}
		return strbd.toString();
	}
	public static String convertSecureCode(String data, int length){
		if (data!=null && data.length()>length) {
			StringBuilder strbd = new StringBuilder(data.substring(0,length));
			
			for (int i = length; i < data.length(); i++) {
				strbd.append("*");
			}
	
			return strbd.toString();
		}return "오류";
	}
	
	}
