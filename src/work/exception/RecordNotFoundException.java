package work.exception;

/** ���ڵ� ���°�� �߻� ����Ŭ���� : ��ȸ, ����, ���� ��� 
  * 1. extends Exception
  * 2. super("���ܸ޼������ڿ�");
  */
public class RecordNotFoundException  extends Exception {
	/** �⺻������ */
	public RecordNotFoundException() {
		super("���ڵ� �������� ���� ����");
	}

	/** ���ڵ� �ߺ�Ű�� �ƱԸ�Ʈ�� �޾Ƽ� ���ܸ޼��� ���� ������ */
	public RecordNotFoundException(String key) {
		super("���ڵ� �������� ���� ���� : " + key);
	}

}
