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
    title varchar(100) NOT NULL,
    status varchar(50) NOT NULL, -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score decimal(4,2) check (score >= 0 AND score <= 10) NOT NULL,
    personal_score int check (personal_score >= 0 AND personal_score <= 10), -- Dato subjetivo del usuario --
    synopsis text NOT NULL,
    release_date date NOT NULL,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    duration int, -- En minutos --
    total_score_votes int,
	image_link varchar(150),
    favorite bool default false,
    primary key(id, user_id),
	foreign key (user_id) references user(id)
);
-- rollback: 'drop table Film;'

create table Serie(
	id int,
    user_id int references user(id),
    title varchar(100) NOT NULL,
    status varchar(50) NOT NULL, -- Enumerado EJ (Completed, Watching, On-Hold, Dropped, Plan to watch)
    review text, -- Dato subjetivo del usuario -- 
    score decimal(4,2) check (score >= 0 AND score <= 10) NOT NULL,
    personal_score int check (personal_score >= 0 AND personal_score <= 10), -- Dato subjetivo del usuario --
    synopsis text NOT NULL,
    release_date date NOT NULL,
    completed_date date,
    last_update_date date,
    genres varchar(100),
    duration_per_episode int, -- En minutos --
    total_episodes int,
    current_episodes int,
    total_seasons int,
	total_score_votes int,
    image_link varchar(150),
    favorite bool default false,
	primary key(id, user_id),
    foreign key (user_id) references user(id)
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
	personal_score int check (personal_score >= 0 AND personal_score <= 10),
    review text,
    primary key(id, serie_id, user_id),
	foreign key (user_id) references user(id),
	foreign key (serie_id) references serie(id)
);
-- rollback: 'drop table Season;'

INSERT INTO USER VALUES(1, "Admin", "Admin123@");

select * from user;
select * from film;
select * from serie;
select * from season;



