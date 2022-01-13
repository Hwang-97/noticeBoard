-- D:\class\server\Code\script.sql

drop table tblUser;

create table tblUser (
    id varchar2(30) primary key,
    pw varchar2(30) not null,
    name varchar2(30) not null,
    lv number(1) default 3 not null
);

insert into tblUser (id, pw, name, lv) values ('hong', '1111', '홍길동', default);
insert into tblUser (id, pw, name, lv) values ('test', '1111', '테스트', default);
insert into tblUser (id, pw, name, lv) values ('admin', '1111', '관리자', 1);

select * from tblUser;

commit;





-- 게시판 테이블
create table tblBoard (
    seq number primary key,                             --글번호(PK)
    id varchar2(30) not null references tblUser(id),    --작성자(FK)
    subject varchar2(300) not null,                     --제목
    content varchar2(2000) not null,                    --내용
    regdate date default sysdate not null,              --작성시간
    readcount number default 0 not null,                --조회수
    userip varchar2(15) not null                        --접속IP
);

create sequence seqBoard;

insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '게시판입니다.', '내용입니다.', default, default, '127.0.0.1');

select * from tblBoard;
    
update tblBoard set subject = '제목', content = '내용' where seq = 1;

delete from tblBoard where seq = 1;

commit;



-- 게시판 뷰
create or replace view vwBoard
as
select 
    seq, subject, id, (select name from tblUser where id = tblBoard.id) as name, regdate, readcount,
    (sysdate - regdate) as isnew
    from tblBoard;



delete from tblBoard;

insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '게시판입니다.', '내용입니다.', to_date('2022-01-10 12:30:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');
insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '안녕하세요. 홍길동입니다..', '내용입니다.', to_date('2022-01-10 18:45:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');    
insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '반갑습니다~~', '내용입니다.', to_date('2022-01-11 06:30:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');
insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '날씨가 참 좋습니다.', '내용입니다.', to_date('2022-01-12 11:10:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');
insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '점심 시간입니다. 밥먹자~.', '내용입니다.', to_date('2022-01-12 12:50:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');
insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '게시만 만드는 중입니다..', '내용입니다.', to_date('2022-01-13 09:30:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');
insert into tblBoard (seq, id, subject, content, regdate, readcount, userip)
    values (seqBoard.nextVal, 'hong', '게시만 계속 만드는 중입니다..', '내용입니다.', to_date('2022-01-13 11:20:00', 'yyyy-mm-dd hh24:mi:ss'), default, '127.0.0.1');

commit;







