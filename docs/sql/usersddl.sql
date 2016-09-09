drop table users purge;

create table users (
	user_id varchar2(20) primary key,
	user_pw  varchar2(20) not null,
	name  varchar2(20) not null,
	mobile  varchar2(14) not null,
	email  varchar2(20) not null ,
	address varchar2(100) not null,
 	grade varchar2(1)  not null
	);

select name, grade from users where user_id = 'admin01@naver.com' and user_pw = 112;