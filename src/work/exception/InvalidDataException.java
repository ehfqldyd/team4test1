package work.exception;

//레코드가 없을땨의 발생예외클래스 : 조회 변경 삭제 클래스에서 사용
//1.extends Exception
//2.super("예외메세지문자열")
public class InvalidDataException extends Exception {
	//기본생성자
	public InvalidDataException() {
		//부모생성자 지정 : 예외메세지 전달
		super("잘못입력된 데이터");
	}
 
 //레코드 중복키를 아규먼트로 받아서 예외메시지 전달 생성자
	public InvalidDataException(String key) {
		//부모생성자 지정 : 예외메세지 전달
		super("잘못입력된 데이터" + key);
	}
}