insert into SQL(PACKAGE_ID,NAME,STATEMENT,SQL_TYPE) values (${PACKAGE_ID}, ${NAME}, ${STATEMENT}, ${SQL_TYPE});

insert into PACKAGE(PACKAGE_NAME,FOLDER) values (${PACKAGE_NAME},${FOLDER});

insert into PARAMETER(KEYWORD, DATA, DATA_TYPE) valeus (${KEYWORD}, ${DATA}, ${DATA_TYPE})

select SQL_ID, PACKAGE_ID, NAME, STATEMENT, SQL_TYPE from SQL order SQL_ID;

select PACKAGE_ID, PACKAGE_NAME, FOLDER from PACKAGE

update PACKAGE set PACKAGE_NAME = {$PACKAGE_NAME}, FOLDER = ${FOLDER} where PACKAGE_ID = ${PACKAGE_ID}

delete from PACKAGE where PACKAGE_ID = ${PACKAGE_ID}


-PACKAGE
select
 PACKAGE_ID
,APP_ID
,PACKAGE_NAME
,FOLDER
from PACKAGE
where PACKAGE_NAME = ${PACKAGE_NAME} or ${PACKAGE_NAME} is null;

insert into PACKAGE(APP_ID,PACKAGE_NAME,FOLDER) values (${APP_ID},${PACKAGE_NAME},${FOLDER});

update PACKAGE set PACKAGE_NAME = ${PACKAGE_NAME}, FOLDER = ${FOLDER} where PACKAGE_ID = ${PACKAGE_ID}

delete from PACKAGE where PACKAGE_ID = ${PACKAGE_ID}

-- APPLICATION
select
 APP_ID
,APP_NAME
,DB_NAME
,DB_CONNECTION
from APPLICATION

insert into APPLICATION(
 APP_NAME
,DB_NAME
,DB_CONNECTION
) values (
 ${APP_NAME}
,${DB_NAME}
,${DB_CONNECTION}
)

update APPLICATION set
 APP_NAME = ${APP_NAME}
,DB_NAME = ${DB_NAME}
,DB_CONNECTION = ${DB_CONNECTION}
where APP_ID = ${APP_ID}

delete from  APPLICATION
where APP_ID = ${APP_ID}

