create table rentbike(
	rent_no number(10) primary key,
	bike_no number(10) references bikes(bike_no) ,
	user_id varchar2(20) references users(user_id) ,
	rent_start varchar2(20) not null,
	rent_end varchar2(20) not null
	);