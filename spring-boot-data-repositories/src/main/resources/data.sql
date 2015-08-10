insert into Customer (id, email, firstname, lastname) values (1, 'dietmar@example.org', 'Dietmar', 'Friedrich');
insert into Customer (id, email, firstname, lastname) values (2, 'stefan@example.org', 'Stefan', 'Daub');
insert into Customer (id, email, firstname, lastname) values (3, 'ralf@example.org', 'Ralf', 'Steinbach');

insert into Address (id, street, city, country, customer_id) values (1, 'Musterweg 10', 'Kaiserslautern', 'Germany', 1);
insert into Address (id, street, city, country, customer_id) values (2, 'Am Eck 12', 'Kaiserslautern', 'Germany', 2);

insert into Product (id, name, description, price) values (1, 'iPad', 'Apple tablet device', 499.0);
insert into Product (id, name, description, price) values (2, 'MacBook Pro', 'Apple notebook', 1299.0);
insert into Product (id, name, description, price) values (3, 'Dock', 'Dock for iPhone/iPad', 49.0);

insert into Product_Attributes (attributes_key, product_id, attributes) values ('connector', 1, 'socket');
insert into Product_Attributes (attributes_key, product_id, attributes) values ('connector', 3, 'plug');