package work.exception;

//레코드 중복시 발생 예외클래스
public class DuplicateException extends Exception {
	//기본생성자
	public DuplicateException() {
		//부모생성자 지정 : 예외메세지 전달
		super("레코드 중복 에러.");
	}
 
 //레코드 중복키를 아규먼트로 받아서 예외메시지 전달 생성자
	public DuplicateException(String key) {
		//부모생성자 지정 : 예외메세지 전달
		super("레코드 중복 에러 : "+ key);
	}
}