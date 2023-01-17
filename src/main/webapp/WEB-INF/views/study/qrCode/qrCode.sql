show tables;

create table qrCode (
	/*idx int not null auto_increment primary key,*/
	idx char(8) not null, /* UUID 앞에서 8글자로 지정 */
	qrCode varchar(200) not null,
	bigo varchar(200)
);