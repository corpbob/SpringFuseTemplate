create table customer (id int primary key, firstname varchar(10), lastname varchar(10), phoneno varchar(10), isdeleted number(1,0));
create sequence customer_seq start with 1 
increment by 1
minvalue 1
maxvalue 10000;

create or replace trigger customer_trig before insert on Customer for each row
begin
select customer_seq.nextval into :new.id
from dual;
end;
/

insert into customer (firstname, lastname,phoneno, isdeleted) values ('Mickey', 'Mouse', '123456', 0);
insert into customer (firstname, lastname,phoneno, isdeleted) values ('Donald', 'Duck', '123451', 0);

create table orders(id number(38) not null,item varchar2(10), amount number,description varchar2(30), processed number);

insert into orders (id, item, amount, description, processed) values (2, 'Camel', 4, 'Camel Book', 1);

