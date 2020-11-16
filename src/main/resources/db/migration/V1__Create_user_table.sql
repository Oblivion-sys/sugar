create table user
(
	id int auto_increment,
	account varchar(100),
	name varchar(50),
	token varchar(100),
	create_time bigint,
	modify_time bigint,
	tips varchar(256),
	constraint USER_PK
		primary key (id)
);