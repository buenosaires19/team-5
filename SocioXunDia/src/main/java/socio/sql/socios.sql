drop database if exists socios;
create database socios;
use socios;


create table region (
idregion int not null primary key auto_increment,
reg_desc varchar(255) not null
);
create table nivel (
idnivel int not null primary key auto_increment,
nivel_desc varchar(255) not null
);
create table videos (
idvideos int not null primary key auto_increment,
url varchar(255) not null
);
create table usuario (
idusuario int not null primary key auto_increment,
nombreusuario varchar(255) not null,
apellidousuario varchar(255) not null,
mailusuario varchar(255) not null,
regionid int not null,
nivelid int not null,
videoid int not null,
foreign key (regionid) references region(idregion) on delete cascade,
foreign key (nivelid) references nivel(idnivel) on delete cascade,
foreign key (videoid) references videos(idvideos) on delete cascade
);
create table profesion (
	idprofesion int not null primary key auto_increment,
	profesion_desc varchar(255) not null,
	videoid int not null,
foreign key (videoid) references videos(idvideos) on delete cascade
);
create table mentor (
	idmentor int not null primary key auto_increment,
	nombrementor varchar(255) not null,
	apellidomentor varchar(255) not null,
	mailmentor varchar(255) not null,
	regionid int not null,
	profesionid int not null,
	foreign key (regionid) references region(idregion) on delete cascade,
	foreign key (profesionid) references profesion(idprofesion) on delete cascade
);




select * from mentor;
