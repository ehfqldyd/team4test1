package work.exception;

//���ڵ尡 �����x�� �߻�����Ŭ���� : ��ȸ ���� ���� Ŭ�������� ���
//1.extends Exception
//2.super("���ܸ޼������ڿ�")
public class InvalidDataException extends Exception {
	//�⺻������
	public InvalidDataException() {
		//�θ������ ���� : ���ܸ޼��� ����
		super("�߸��Էµ� ������");
	}
 
 //���ڵ� �ߺ�Ű�� �ƱԸ�Ʈ�� �޾Ƽ� ���ܸ޽��� ���� ������
	public InvalidDataException(String key) {
		//�θ������ ���� : ���ܸ޼��� ����
		super("�߸��Էµ� ������" + key);
	}
}