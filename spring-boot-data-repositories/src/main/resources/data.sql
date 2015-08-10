insert into Customer (id, email, firstname, lastname) values (1, 'dietmar@example.org', 'Dietmar', 'Friedrich');
insert into Customer (id, email, firstname, lastname) values (2, 'stefan@example.org', 'Stefan', 'Daub');
insert into Customer (id, email, firstname, lastname) values (3, 'ralf@example.org', 'Ralf', 'Steinbach');

insert into Address (id, street, city, country, customer_id) values (1, 'Musterweg 10', 'Kaiserslautern', 'Germany', 1);
insert into Address (id, street, city, country, customer_id) values (2, 'Am Eck 12', 'Kaiserslautern', 'Germany', 2);