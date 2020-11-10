insert into User(`first_name`, `last_name`, `email`, `password`, `enabled`, `role`)
values ('Marwa','Sahraoui', 'marwa@gmail.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1, "ROLE_ADMIN");

insert into User(`first_name`, `last_name`, `email`, `password`, `enabled`, `role`)
values ('omaima','sahraoui', 'maima@gmail.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1, "ROLE_USER");

insert into User(`first_name`, `last_name`, `email`, `password`, `enabled`, `role`)
values ('Bob','Marley', 'bob@gmail.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1, "ROLE_USER");

insert into User(`first_name`, `last_name`, `email`, `password`, `enabled`, `role`)
values ('Tom','Hanks', 'hanks@gmail.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 1, "ROLE_USER");


insert into Account (`amount`, `date`, `owner`)
values(0, '2020-09-01', 1 );

insert into Account (`amount`, `date`, `owner`)
values(100, '2020-09-01', 2 );

insert into Account (`amount`, `date`, `owner`)
values(20, '2020-09-01', 3);

insert into Account (`amount`, `date`, `owner`)
values(500, '2020-09-01', 4);


insert into Credit_Card (`name_Card`,`associate_account`)
values('Marwa Sahraoui', 1);

insert into Credit_Card (`name_Card`,`associate_account`)
values('Oumaima Sahraoui', 2);

insert into Credit_Card (`name_Card`,`associate_account`)
values('Bob Marley', 3);

insert into Credit_Card (`name_Card`,`associate_account`)
values('John Dow',4);


insert into Transaction(`amount`,`date`,`tax`,`sender`,`receiver`)
values(20,'2020-09-01', 1, 2, 1);

insert into Transaction(`amount`,`date`,`tax`,`sender`,`receiver`)
values(20,'2020-09-01', 1, 2, 3);

insert into Transaction(`amount`,`date`,`tax`,`sender`,`receiver`)
values(100,'2020-09-01', 5, 4, 1);



