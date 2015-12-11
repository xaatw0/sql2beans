drop table if exists SQL;
drop table if exists PACKAGE;
drop table if exists PARAMETER;

create table if not exists  SQL (
	SQL_ID int identity primary key,
	PACKAGE_ID int,
	NAME varchar(50),
	STATEMENT varchar(500) not null,
	SQL_TYPE int,
	unique(PACKAGE_ID, NAME)
);

create table if not exists PACKAGE (
	PACKAGE_ID int identity primary key,
	PACKAGE_NAME varchar(50) not null unique,
	FOLDER varchar(50)
);

create table if not exists PARAMETER(
	SQL_ID int,
	KEYWORD varchar(20),
	DATA varchar(30),
	DATA_TYPE int,
	primary key(SQL_ID, KEYWORD)
);
