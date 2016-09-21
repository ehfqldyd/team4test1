-- È¸¿ø
CREATE TABLE MEMBER (
   ID VARCHAR2(40) NOT NULL, 
   PW VARCHAR2(20), 
   MEMBER_NAME VARCHAR2(20), 
   COUPLE_NO NUMBER
);


ALTER TABLE MEMBER
   ADD
      CONSTRAINT PK_MEMBER
      PRIMARY KEY (
         ID
      );

INSERT INTO MEMBER VALUES('test01','pass01','name01', 0);
INSERT INTO MEMBER VALUES('test02','pass02','name02', 0);
INSERT INTO MEMBER VALUES('test03','pass03','name03', 0);
INSERT INTO MEMBER VALUES('test04','pass04','name04', 0);
INSERT INTO MEMBER VALUES('test05','pass05','name05', 0);
INSERT INTO MEMBER VALUES('test06','pass06','name06', 0);
INSERT INTO MEMBER VALUES('test07','pass07','name07', 0);
INSERT INTO MEMBER VALUES('test08','pass08','name08', 0);
INSERT INTO MEMBER VALUES('test09','pass09','name09', 0);

commit;

