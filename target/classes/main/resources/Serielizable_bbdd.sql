create schema if not exists serielizable;
-- rollback: 'drop schema serielizable;' -- 

use serielizable;

create table User(
	id int auto_increment,
    user_name varchar(50) unique not null,
    password varchar(50) not null,
    CONSTRAINT pk_users primary key (id)
);
-- rollback: 'drop table User;' --

create table Footage(
	id varchar(50) primary key,
    user_id int references user(id),
    title varchar(50) NOT NULL,
    type varchar(50), -- Enumerado EJ: (Film, Series, Short, etc) --
    status varchar(50), -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score int check (score >= 0 AND score <= 10), -- Dato subjetivo del usuario -- 
    release_date date,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    episodes int,
    duration int
);
-- rollback: 'drop table Footage;'

INSERT INTO Footage VALUES ("IDPRUEBA", 1, "Breaking bad", "Series", "Completed", "", 9, null, null, null, "Action, Drama", 24, 40);
INSERT INTO Footage VALUES ("IDPRUEBA2", 1, "Big Bang Theory", "Series", "Completed", "", 7, null, null, null, "Comedia", 24, 20);
INSERT INTO Footage VALUES ("IDPRUEBA3", 1, "One punch man", "Series", "Completed", "", 4, null, null, null, "Action", 12, 20);



