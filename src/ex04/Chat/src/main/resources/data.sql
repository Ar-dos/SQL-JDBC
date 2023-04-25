insert into users values (default,'login1', 'pass1');
insert into users values (default,'login2', 'pass2');
insert into users values (default,'login3', 'pass3');
insert into users values (default,'login4', 'pass4');
insert into users values (default,'login5', 'pass5');

insert into chatrooms values (default,'room1',5);
insert into chatrooms values (default,'room2',4);
insert into chatrooms values (default,'room3',3);
insert into chatrooms values (default,'room4',2);
insert into chatrooms values (default,'room5',1);

insert into messages values (default,1,5,'Me im user 1','2022-12-20 01:00:00');
insert into messages values (default,2,4,'Me im user 2','2022-12-20 02:00:00');
insert into messages values (default,3,3,'Me im user 3','2022-12-20 03:00:00');
insert into messages values (default,4,2,'Me im user 4','2022-12-20 04:00:00');
insert into messages values (default,5,1,'Me im user 5','2022-12-20 05:00:00');

insert into users_chatrooms values (1,5);
insert into users_chatrooms values (2,5);
insert into users_chatrooms values (2,4);
insert into users_chatrooms values (3,4);
insert into users_chatrooms values (3,3);
insert into users_chatrooms values (4,3);
insert into users_chatrooms values (4,2);
insert into users_chatrooms values (5,2);
insert into users_chatrooms values (5,1);
insert into users_chatrooms values (1,1);