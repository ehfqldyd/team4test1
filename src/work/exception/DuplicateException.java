package work.exception;

//���ڵ� �ߺ��� �߻� ����Ŭ����
public class DuplicateException extends Exception {
	//�⺻������
	public DuplicateException() {
		//�θ������ ���� : ���ܸ޼��� ����
		super("���ڵ� �ߺ� ����.");
	}
 
 //���ڵ� �ߺ�Ű�� �ƱԸ�Ʈ�� �޾Ƽ� ���ܸ޽��� ���� ������
	public DuplicateException(String key) {
		//�θ������ ���� : ���ܸ޼��� ����
		super("���ڵ� �ߺ� ���� : "+ key);
	}
}