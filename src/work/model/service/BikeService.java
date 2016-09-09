package work.model.service;

import java.util.ArrayList;

import work.model.dao.BikeDao;
import work.model.dto.Bike;


/**
 * ������ ���� �ý��� ���� Ŭ����
 * ������ ���� ����� ����Ŭ����
 * @author choi
 *
 */
public class BikeService {
	/**������DAO Ŭ����*/
	private BikeDao dao = BikeDao.getInstance();
	
	/**
	 * �����ŵ�� ����
	 * @param bike ��ü
	 * @return  �����ȯ
	 */
	public int insertBike(Bike bike){
		String bikeCheck="�뿩����";
		bike.setBikeCheck(bikeCheck);
		return dao.insertBike(bike);
		
	}
	/**
	 * ������ ���� ����
	 * @param bikeNo �������Ϸù�ȣ
	 * @return  �����ȯ
	 */
	public int deleteBike(int bikeNo){
		return dao.deleteBike(bikeNo);
	}
	/**
	 * ������ ����ȸ ����
	 * @param bikeNo �������Ϸù�ȣ
	 * @return Bike�����Ű�ü��ȯ
	 */
	public Bike selectOneBike(int bikeNo){
		
		return dao.selectOneBike(bikeNo);
	}
	
	/**
	 * ������ ��ü��ȸ ����
	 * @return  ArrayList<Bike>���·� �����ȯ
	 */
	public ArrayList<Bike> selectAllBike(){
		return dao.selectAllBike();
	}
	
	/**
	 * ������ ��ġ �κа˻���ȸ ����
	 * @param location��ġ�޾Ƽ�
	 * @return ����Ʈ ������ �����ȯ
	 */
	public ArrayList<Bike> selectAllBikeLocation(String location){
		return dao.selectAllBikeLocation(location);
	}
	/**
	 * ������ ��ġ���� ����
	 * @param bikeNo �������Ϸù�ȣ�޾Ƽ�
	 * @param location ��������ġ�� �޾Ƽ�
	 * @param newLocation ���ο� ��ġ�κ���
	 * @return int �� �����ȯ
	 */
	public int updateLocation(int bikeNo,String location, String newLocation){
		return dao.updateLocation(bikeNo, location, newLocation);
	}
	
	/**
	 * ������ ���̵�� �������������� 
	 * @param bikeNo ������ �Ϸù�ȣ
	 * @param location ������ ��ġ
	 * @param bikeCheck ������ �뿩����
	 * @return Bike ��ü �����ȯ
	 */
	public Bike updateIdBike(int bikeNo, String location, String bikeCheck){
		return dao.updateIdBike(bikeNo, location, bikeCheck);
	}

}
