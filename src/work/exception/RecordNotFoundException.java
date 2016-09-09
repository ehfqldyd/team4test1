package work.exception;

/** 레코드 없는경우 발생 예외클래스 : 조회, 변경, 삭제 기능 
  * 1. extends Exception
  * 2. super("예외메세지문자열");
  */
public class RecordNotFoundException  extends Exception {
	/** 기본생성자 */
	public RecordNotFoundException() {
		super("레코드 존재하지 않음 예외");
	}

	/** 레코드 중복키를 아규먼트로 받아서 예외메세지 전달 생성자 */
	public RecordNotFoundException(String key) {
		super("레코드 존재하지 않음 예외 : " + key);
	}

}
