create table User
(
    id         bigint not null auto_increment primary key,
    first_name varchar(30),
    last_name  varchar(30),
    email      varchar(50) UNIQUE,
    password   varchar(255),
    enabled    boolean,
    role       varchar(10)

);

create table Account
(
    id     bigint           not null auto_increment primary key,
    amount double precision not null,
    date   datetime(6),
    owner  bigint,
    foreign key (owner) references User (id)
);

create table Transaction
(
    id       bigint           not null auto_increment primary key,
    amount   double precision not null,
    date     datetime(6),
    tax      double precision not null,
    receiver bigint,
    sender   bigint,
    foreign key (receiver) references Account (id),
    foreign key (sender) references Account (id)
);

create table Credit_Card
(
    id                bigint not null auto_increment primary key,
    name_Card         varchar(50),
    associate_account bigint,
    foreign key (associate_account) references Account (id)
);

create table User_contacts
(
    user_id    bigint not null,
    contact_id bigint not null,
    foreign key (user_id) references User (id),
    foreign key (contact_id) references User (id)
);