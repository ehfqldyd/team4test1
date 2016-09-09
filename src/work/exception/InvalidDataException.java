package work.exception;

/** 잘못된 데이터에 대한 발생 예외클래스  */
public class InvalidDataException  extends Exception {
	/** 기본생성자 */
	public InvalidDataException() {
		super("잘못된 데이터 예외");
	}

	/** 올바르지 않은 데이터를 아규먼트로 받아서 예외메세지 전달 생성자 */
	public InvalidDataException(String data) {
		super("잘못된 데이터 예외 : " + data);
	}

}
