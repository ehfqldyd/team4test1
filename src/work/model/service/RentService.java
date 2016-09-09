package work.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import work.model.dao.BikeDao;
import work.model.dao.RentDao;
import work.model.dto.Bike;
import work.model.dto.Rent;
import work.util.Utility;

/**��Ʈ �ý��ۿ����� ���� Ŭ����
 * --��Ʈ �ý����� ��ɵ鿡 ���� business logic Ŭ����
 * 
 * */

public class RentService {
	/**��ƮDAO Ŭ����*/
	private RentDao dao = RentDao.getInstance();
	
	/**
	 * �����Ŵ뿩 ����
	 * @param bikeNo �������Ϸù�ȣ
	 * @param userId ����ھ��̵�
	 * @param location ��������ġ
	 * @return int������ �����ȯ
	 */
	public int insertRent(int bikeNo,String userId ,String location){
		return dao.insertRent(bikeNo, userId, location);
		
	}
	/**
	 * ������ �ݳ�����
	 * @param bikeNo �������Ϸù�ȣ
	 * @param location ��������ġ
	 * @return int ��Ʈ������ �����ȯ
	 */
	public int deleteRent(int bikeNo, String location){
		return dao.deleteRent(bikeNo, location);
	}
	
	/**
	 * �뿩�˻�����
	 * @return ����Ʈ���·� ��� ���
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
