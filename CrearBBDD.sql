drop database IF EXISTS bingo;
create database bingo;
use bingo;
create table IF NOT EXISTS loginInLB (
username varchar(50) PRIMARY KEY,
passwd varchar(25) NOT NULL
);
insert into loginInLB VALUES 
("Juan Antonio", "#Juan19"),
("Patricia", "#PatriAD");
create table IF NOT EXISTS leaderboard (
posInBoard int UNIQUE,
playerName varchar(10) PRIMARY KEY,
linesCalled int NOT NULL,
bingosCalled int NOT NULL
);
insert into leaderboard VALUES 
(1, "Celeste", 7, 6), 
(2, "François", 5, 5),
(3, "Opera", 1, 6),
(4, "Crisbell", 2, 4),
(5, "Kitagaya", 4, 2);