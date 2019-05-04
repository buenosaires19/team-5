drop database if exists socios;
create database socios;
use socios;


create table region (
id int not null primary key auto_increment,
reg_desc varchar(255) not null
);
create table nivel (
id int not null primary key auto_increment,
nivel_desc varchar(255) not null
);
create table videos (
id int not null primary key auto_increment,
url varchar(255) not null
);
create table usuario (
	id int not null primary key auto_increment,
	nombreusuario varchar(255) not null,
	apellidousuario varchar(255) not null,
	mailusuario varchar(255) not null,
	regionid int not null,
	nivelid int not null,
	videoid int not null
	-- foreign key (regionid) references region(id) on delete cascade,
	-- foreign key (nivelid) references nivel(id) on delete cascade,
	-- foreign key (videoid) references videos(id) on delete cascade
);
create table profesion (
	id int not null primary key auto_increment,
	profesion_desc varchar(255) not null,
	videoid int not null
    -- foreign key (videoid) references videos(id) on delete cascade
);
create table mentor (
	id int not null primary key auto_increment,
	nombrementor varchar(255) not null,
	apellidomentor varchar(255) not null,
	mailmentor varchar(255) not null,
	regionid int not null,
	profesionid int not null
	-- foreign key (regionid) references region(id) on delete cascade,
	-- foreign key (profesionid) references profesion(id) on delete cascade
);




select * from mentor;
