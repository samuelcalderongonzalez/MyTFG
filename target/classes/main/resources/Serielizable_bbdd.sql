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

create table Film(
	id int primary key,
    user_id int references user(id),
    title varchar(50) NOT NULL,
    status varchar(50), -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score decimal check (score >= 0 AND score <= 10),
    personal_score decimal check (personal_score >= 0 AND personal_score <= 10), -- Dato subjetivo del usuario --
    synopsis text,
    release_date date,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    duration int  -- En minutos --
);
-- rollback: 'drop table Film;'

create table Serie(
	id int primary key,
    user_id int references user(id),
    title varchar(50) NOT NULL,
    status varchar(50), -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score decimal check (score >= 0 AND score <= 10),
    personal_score decimal check (personal_score >= 0 AND personal_score <= 10), -- Dato subjetivo del usuario --
    synopsis text,
    release_date date,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    duration_per_episode int, -- En minutos --
    total_episodes int,
    current_episodes int
);
-- rollback: 'drop table Serie;'
-- INSERT INTO USER VALUES(1, "Admin", "Admin123@");
-- INSERT INTO FILM VALUES (1, 1, "PeliPrueba", "Completed", null, null, null, null, null, null, null, null, null);
-- INSERT INTO SERIE VALUES (1, 1, "SeriePrueba", "Completed", null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO USER VALUES(1, "Admin", "Admin123@");
INSERT INTO FILM VALUES (1, 1, "PeliPrueba", "Completed", null, null, null, null, null, null, null, null, null);
INSERT INTO SERIE VALUES (1, 1, "SeriePrueba", "Completed", null, null, null, null, null, null, null, null, null, null, null);
