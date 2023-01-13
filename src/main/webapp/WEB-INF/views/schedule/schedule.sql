show tables;

create table schedule2 (
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	sDate datetime not null,		/* 일정 등록한 날짜 */
	part varchar(20) not null,  /* 1.모임 2.업무 3.학습 4.여행 0.기타 */
	content text not null				/* 일정 상세 내역 */
);

desc shedule2;
drop table shedule2;

insert into schedule2 values (default, 'hkd1234', '2023-01-11', '모임', '테니스모임');
insert into schedule2 values (default, 'admin', '2023-01-11', '업무', '코딩공부');
insert into schedule2 values (default, 'rrr1234', '2023-01-14', '여행', '강릉여행');
insert into schedule2 values (default, 'rrr1234', '2023-01-14', '학습', '자기개발공부');
insert into schedule2 values (default, 'admin', '2023-01-12', '여행', '강릉여행');
insert into schedule2 values (default, 'rrr1234', '2023-01-09', '학습', '자기개발공부');
insert into schedule2 values (default, 'admin', '2023-01-07', '모임', '테니스모임');
insert into schedule2 values (default, 'hkd1234', '2023-01-08', '모임', '테니스모임');
insert into schedule2 values (default, 'rrr1234', '2023-01-25', '업무', '코딩공부');
insert into schedule2 values (default, 'admin', '2023-01-19', '기타', '집돌이모드');

select * from schedule2;

select * from schedule2 where mid='hkd1234' order by sDate;
/* X
select * from schedule2 where mid='hkd1234' and sDate='2023-1' order by sDate;
select * from schedule2 where mid='hkd1234' and sDate='2023-01' order by sDate;
*/
select * from schedule2 where mid='hkd1234' and substring(sDate,1,7)='2023-01' order by sDate;

select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2023-01' order by sDate; 
select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2023-01' group by substring(sDate,1,7) order by sDate; 
select sDate,count(*) from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m')='2023-01' group by substring(sDate,1,7) order by sDate;

select * from schedule2 where mid='hkd1234' and date_format(sDate, '%Y-%m-%d')='2023-01-11';




create table schedule1 (
	idx int not null auto_increment primary key,
	mid varchar(20) not null,
	sDate datetime not null,		/* 일정 등록한 날짜 */
	time varchar(20),
	part varchar(20) not null,  /* 1.모임 2.업무 3.학습 4.여행 0.기타 */
	content text 
);

create table team (
  idx int not null auto_increment,	/* 회원 고유번호 */
  mid varchar(20) not null,					/* 회원 아이디(중복불허) */
  pwd varchar(100) not null,				/* 비밀번호(SHA암호화 처리) */
  nickName varchar(20) not null,		/* 별명(중복불허/수정가능) */
  name	   varchar(20) not null,			/* 회원 성명 */
  gender   varchar(5) default '남자',	/* 성별 */
  email   varchar(50) not null,			/* 이메일(아이디/비밀번호 분실시 사용) - 형식체크필수 */
  power			varchar(20),							/* 회원 직업 */
  photo		varchar(100) default 'noimage.jpg',	/* 회원사진 */
  userDel   char(2) default 'NO',		/* 회원 탈퇴 신청 여부(OK:탈퇴신청한회원, NO:현재가입중인회원) */
  level			int default 4,					/* 회원등급(0:관리자, 1:운영자, 2:우수회원, 3:정회원, 4:준회원 */
  primary key(idx,mid)							/* 주키: idx(고유번호), mid(아이디) */
);


















