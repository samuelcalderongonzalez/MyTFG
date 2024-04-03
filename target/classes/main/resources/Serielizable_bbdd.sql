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
	id int,
    user_id int references user(id),
    title varchar(50) NOT NULL,
    status varchar(50) NOT NULL, -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score decimal(4,2) check (score >= 0 AND score <= 10) NOT NULL,
    personal_score decimal(4,2) check (personal_score >= 0 AND personal_score <= 10), -- Dato subjetivo del usuario --
    synopsis text NOT NULL,
    release_date date NOT NULL,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    duration int, -- En minutos --
    primary key(id, user_id)
);
-- rollback: 'drop table Film;'

create table Serie(
	id int,
    user_id int references user(id),
    title varchar(50) NOT NULL,
    status varchar(50) NOT NULL, -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score decimal(4,2) check (score >= 0 AND score <= 10) NOT NULL,
    personal_score decimal(4,2) check (personal_score >= 0 AND personal_score <= 10), -- Dato subjetivo del usuario --
    synopsis text NOT NULL,
    release_date date NOT NULL,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    duration_per_episode int, -- En minutos --
    total_episodes int,
    current_episodes int,
    total_seasons int,
	primary key(id, user_id)
);
-- rollback: 'drop table Serie;'

create table Season(
	id int,
    serie_id int references serie(id),
    user_id int references user(id),
    season_number int,
    name varchar(50),
    status varchar(50),
    release_date date,
	completed_date date,
    total_episodes int,
    current_episodes int,
    score decimal(4,2) check (score >= 0 AND score <= 10),
	personal_score decimal(4,2) check (personal_score >= 0 AND personal_score <= 10),
    review text,
    primary key(id, serie_id, user_id)
);
-- rollback: 'drop table Season;'

INSERT INTO USER VALUES(1, "Admin", "Admin123@");
INSERT INTO FILM VALUES (1, 1, "PeliPrueba", "Completed", "Review de prueba", 6.78, 8, "Sinopsis de prueba", '2020-01-01', '2020-01-01', '2020-01-01', "Drama, Acción, Romance", 50);
INSERT INTO FILM VALUES (2, 1, "PeliPrueba2", "Completed", null, 6,  null, "Sinopsis de prueba", '2020-01-01', null, null,"Drama, Comedia, Romance", 140);
INSERT INTO FILM VALUES (3, 1, "PeliPrueba3", "Completed", null, 9.8, null, "Sinopsis de prueba", '2024-01-01', null, null, "Romance", 120);

INSERT INTO SERIE VALUES(1, 1, "SeriePrueba", "Completed", null, 10 , null, "Sinopsis de prueba", '2024-01-01', null, null, null, null, null, null, 3);
INSERT INTO SERIE VALUES(2, 1, "SeriePrueba2", "Completed", null, 1 , null, "Sinopsis de prueba", '2020-01-01', null, null, null, null, null, null, 4);
INSERT INTO SERIE VALUES(3, 1, "SeriePrueba3", "Completed", null, 2 , null, "Sinopsis de prueba", '2020-01-01', null, null, null, null, null, null, 0);

select * from film;
select * from serie;
select * from season;