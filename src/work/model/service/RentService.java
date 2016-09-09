package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.BikeDao;
import work.model.dao.RentDao;
import work.model.dto.Bike;
import work.model.dto.Rent;
import work.util.Utility;

/**렌트 시스템에대한 서비스 클래스
 * --렌트 시스템의 기능들에 대한 business logic 클래스
 * 
 * */

public class RentService {
	/**렌트DAO 클래스*/
	private RentDao dao = RentDao.getInstance();
	
	/**
	 * 자전거대여 서비스
	 * @param bikeNo 자전거일련번호
	 * @param userId 사용자아이디
	 * @param location 자전거위치
	 * @return int형으로 결과반환
	 */
	public int insertRent(int bikeNo,String userId ,String location){
		return dao.insertRent(bikeNo, userId, location);
		
	}
	/**
	 * 자전거 반납서비스
	 * @param bikeNo 자전거일련번호
	 * @param location 자전거위치
	 * @return int 인트형으로 결과반환
	 */
	public int deleteRent(int bikeNo, String location){
		return dao.deleteRent(bikeNo, location);
	}
	
	/**
	 * 대여검색서비스
	 * @return 리스트형태로 결과 출력
	 */
	public ArrayList<Rent> selectAllRent(){
		return dao.selectAllRent();
	}
	
	public ArrayList<Rent> selectOneRent(String userId){
		return dao.selectOneRent(userId);
	}
	public int delayRent(int bikeNo){
		return dao.delayRent(bikeNo);
	}

}
