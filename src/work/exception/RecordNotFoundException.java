package work.exception;

//���ڵ尡 �����x�� �߻�����Ŭ���� : ��ȸ ���� ���� Ŭ�������� ���
//1.extends Exception
//2.super("���ܸ޼������ڿ�")
public class RecordNotFoundException extends Exception {
	//�⺻������
	public RecordNotFoundException() {
		//�θ������ ���� : ���ܸ޼��� ����
		super("���ڵ� ����");
	}
 
 //���ڵ� �ߺ�Ű�� �ƱԸ�Ʈ�� �޾Ƽ� ���ܸ޽��� ���� ������
	public RecordNotFoundException(String key) {
		//�θ������ ���� : ���ܸ޼��� ����
		super("���ڵ� ����" + key);
	}
}