create user OSB_WHITELIST identified by welcome1;
grant connect,resource to OSB_WHITELIST;
create TABLE WHITE_LIST ( white_list_id number(10), context varchar2(100), ip_address varchar2(30));
create sequence SEQ_WHITE_LIST ;

ALTER USER  OSB_WHITELIST quota unlimited on USERS;


insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'helloWorld','127.0.0.1');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'helloWorld','127.0.0.2');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'helloWorld','127.0.0.3');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'helloWorld','127.0.0.4');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'kpsService','127.0.0.4');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'kpsService','127.0.0.5');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'kpsService','127.0.0.6');
insert into WHITE_LIST values ( SEQ_WHITE_LIST.nextval, 'kpsService','127.0.0.7');

