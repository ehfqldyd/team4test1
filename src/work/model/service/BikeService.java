package work.model.service;

import java.util.ArrayList;

import work.model.dao.BikeDao;
import work.model.dto.Bike;


/**
 * 자전거 관리 시스템 서비스 클래스
 * 자전거 관리 기능의 로직클래스
 * @author choi
 *
 */
public class BikeService {
	/**자전거DAO 클래스*/
	private BikeDao dao = BikeDao.getInstance();
	
	/**
	 * 자전거등록 서비스
	 * @param bike 객체
	 * @return  결과반환
	 */
	public int insertBike(Bike bike){
		String bikeCheck="대여가능";
		bike.setBikeCheck(bikeCheck);
		return dao.insertBike(bike);
		
	}
	/**
	 * 자전거 삭제 서비스
	 * @param bikeNo 자전거일련번호
	 * @return  결과반환
	 */
	public int deleteBike(int bikeNo){
		return dao.deleteBike(bikeNo);
	}
	/**
	 * 자전거 상세조회 서비스
	 * @param bikeNo 자전거일련번호
	 * @return Bike자전거객체반환
	 */
	public Bike selectOneBike(int bikeNo){
		
		return dao.selectOneBike(bikeNo);
	}
	
	/**
	 * 자전거 전체조회 서비스
	 * @return  ArrayList<Bike>형태로 결과반환
	 */
	public ArrayList<Bike> selectAllBike(){
		return dao.selectAllBike();
	}
	
	/**
	 * 자전거 위치 부분검색조회 서비스
	 * @param location위치받아서
	 * @return 리스트 형태의 결과반환
	 */
	public ArrayList<Bike> selectAllBikeLocation(String location){
		return dao.selectAllBikeLocation(location);
	}
	/**
	 * 자전거 위치변경 서비스
	 * @param bikeNo 자전거일련번호받아서
	 * @param location 자전거위치도 받아서
	 * @param newLocation 새로운 위치로변경
	 * @return int 형 결과반환
	 */
	public int updateLocation(int bikeNo,String location, String newLocation){
		return dao.updateLocation(bikeNo, location, newLocation);
	}
	
	/**
	 * 자전거 아이디로 자전거정보변경 
	 * @param bikeNo 자전거 일련번호
	 * @param location 자전거 위치
	 * @param bikeCheck 자전거 대여여부
	 * @return Bike 객체 결과반환
	 */
	public Bike updateIdBike(int bikeNo, String location, String bikeCheck){
		return dao.updateIdBike(bikeNo, location, bikeCheck);
	}

}
