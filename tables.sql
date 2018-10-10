create table customer (id int primary key, firstname varchar(10), lastname varchar(10), phoneno varchar(10), isdeleted number(1,0));
create or replace trigger customer_trig before insert on Customer for each row
begin
select customer_seq.nextval into :new.id
from dual;
end;
/
insert into customer (firstname, lastname,phoneno, isdeleted) values ('Mickey', 'Mouse', '123456', 0);
insert into customer (firstname, lastname,phoneno, isdeleted) values ('Donald', 'Duck', '123451', 0);

