package work.exception;

/** �߸��� �����Ϳ� ���� �߻� ����Ŭ����  */
public class InvalidDataException  extends Exception {
	/** �⺻������ */
	public InvalidDataException() {
		super("�߸��� ������ ����");
	}

	/** �ùٸ��� ���� �����͸� �ƱԸ�Ʈ�� �޾Ƽ� ���ܸ޼��� ���� ������ */
	public InvalidDataException(String data) {
		super("�߸��� ������ ���� : " + data);
	}

}
