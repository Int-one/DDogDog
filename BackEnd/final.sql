	DROP database if exists final;

	create database final;

	use final;
    

-- User Table
CREATE TABLE User (
    user_id VARCHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR(30) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    birth TIMESTAMP NOT NULL,
    phone VARCHAR(11) NOT NULL,
    gender BOOLEAN NOT NULL,
    height DOUBLE,
    weight DOUBLE,
    region VARCHAR(30) NOT NULL,
    exp INT NOT NULL DEFAULT 0,
    dog_walker BOOLEAN NOT NULL DEFAULT FALSE,
    hire_cnt BIGINT NOT NULL DEFAULT 0,
    hired_cnt BIGINT NOT NULL DEFAULT 0,
    bpm DOUBLE NOT NULL DEFAULT 125
);

-- Pet Table
CREATE TABLE Pet (
    pet_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    birth TIMESTAMP NOT NULL,
    gender BOOLEAN NOT NULL,
    castration BOOLEAN NOT NULL,
    `group` VARCHAR(10) NOT NULL,
    breed VARCHAR(30),
    weight DOUBLE DEFAULT 0,
    user_id VARCHAR(30) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Trade Table
CREATE TABLE Trade (
    trade_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    kind VARCHAR(10) NOT NULL,
    startTime TIMESTAMP NOT NULL,
    endTime TIMESTAMP NOT NULL,
    cost BIGINT(20) NOT NULL,
    region VARCHAR(30) NOT NULL,
    detail TEXT,
    super_id VARCHAR(30),
    user_id VARCHAR(30),
    FOREIGN KEY (super_id) REFERENCES User(user_id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE SET NULL
);

-- Cand Table
CREATE TABLE Cand (
    trade_id BIGINT NOT NULL,
    user_id VARCHAR(30) NOT NULL,
    PRIMARY KEY (trade_id, user_id),
    FOREIGN KEY (trade_id) REFERENCES Trade(trade_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Together Table
CREATE TABLE Together (
    together_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    trade_id BIGINT NOT NULL,
    pet_id INT NOT NULL,
    FOREIGN KEY (trade_id) REFERENCES Trade(trade_id) ON DELETE CASCADE,
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id) ON DELETE CASCADE
);

-- Photo Table
CREATE TABLE Photo (
    photo_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    photo_url VARCHAR(255) NOT NULL,
    user_id VARCHAR(30) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- WalkLog Table
CREATE TABLE WalkLog (
    log_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    walk_path JSON NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    total DOUBLE NOT NULL,
    dog_walking boolean NOT NULL DEFAULT FALSE,
    user_id VARCHAR(30) NOT NULL,
    trade_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE,
    FOREIGN KEY (trade_id) REFERENCES Trade(trade_id) ON DELETE SET NULL
);
-- PetLog Table
CREATE TABLE PetLog (
    plog_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    note TEXT,
    small INT NOT NULL DEFAULT 0,
    big INT NOT NULL DEFAULT 0,
    log_id BIGINT NOT NULL,
    pet_id INT NOT NULL,
    FOREIGN KEY (log_id) REFERENCES WalkLog(log_id) ON DELETE CASCADE,
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id) ON DELETE CASCADE
);

-- Result Table
CREATE TABLE Result (
    log_id BIGINT NOT NULL PRIMARY KEY,
    price INT NOT NULL,
    bpm DOUBLE NOT NULL,
    review TEXT,
    FOREIGN KEY (log_id) REFERENCES WalkLog(log_id) ON DELETE CASCADE
);

-- DogWalker Table
CREATE TABLE DogWalker (
	user_id varchar(30) PRIMARY KEY,
    weekday_am boolean NOT NULL DEFAULT FALSE,
    weekday_pm boolean NOT NULL DEFAULT FALSE,
    weekend_am boolean NOT NULL DEFAULT FALSE,
    weekend_pm boolean NOT NULL DEFAULT FALSE,
    `all` boolean NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- User Table 더미 데이터
INSERT INTO User (user_id, password, nickname, birth, phone, gender, height, weight, region, exp, dog_walker, hire_cnt, hired_cnt, bpm)
VALUES
('user01', 'password123', '강아지사랑', '1990-01-15', '01012345678', 1, 175.5, 70.0, '서울', 10, TRUE, 5, 3, 120),
('user02', 'password234', '냥이사랑', '1985-05-20', '01023456789', 0, 160.2, 55.0, '부산', 8, FALSE, 2, 4, 125),
('user03', 'password345', '펫시터', '1992-09-10', '01034567890', 1, 180.0, 80.0, '대구', 20, TRUE, 10, 5, 115);

-- Pet Table 더미 데이터
INSERT INTO Pet (name, birth, gender, castration, `group`, breed, weight, user_id)
VALUES
('코코', '2021-03-10', 1, TRUE, '강아지', '푸들', 5.0, 'user01'),
('미미', '2019-07-25', 0, FALSE, '고양이', '샴', 4.5, 'user02'),
('초코', '2020-05-05', 1, TRUE, '강아지', '말티즈', 3.2, 'user01');

-- Trade Table 더미 데이터
INSERT INTO Trade (kind, startTime, endTime, cost, region, detail, super_id, user_id)
VALUES
('산책', '2024-11-15 10:00:00', '2024-11-15 11:00:00', 15000, '서울', '서울숲에서 산책', 'user01', 'user02'),
('펫 시터', '2024-11-16 12:00:00', '2024-11-16 15:00:00', 30000, '부산', '부산에서 고양이 돌봄 서비스', 'user02', 'user03');

-- Cand Table 더미 데이터
INSERT INTO Cand (trade_id, user_id)
VALUES
(1, 'user02'),
(2, 'user03');

-- Together Table 더미 데이터
INSERT INTO Together (trade_id, pet_id)
VALUES
(1, 1),
(2, 2);

-- Photo Table 더미 데이터
INSERT INTO Photo (photo_url, user_id)
VALUES
('/images/coco_walk.jpg', 'user01'),
('/images/mimi_sitter.jpg', 'user02');

-- WalkLog Table 더미 데이터
INSERT INTO WalkLog (walk_path, start_time, end_time, total, dog_walking, user_id, trade_id)
VALUES
('{
    "path": [
        {"lat": 37.5665, "lng": 126.9780, "time": "2024-11-15T10:00:00"},
        {"lat": 37.5670, "lng": 126.9790, "time": "2024-11-15T10:15:00"},
        {"lat": 37.5680, "lng": 126.9800, "time": "2024-11-15T10:30:00"}
    ],
    "duration": "1시간"
}', '2024-11-15 10:00:00', '2024-11-15 11:00:00', 2.5, TRUE, 'user01', 1),
('{
    "path": [
        {"lat": 35.1796, "lng": 129.0756, "time": "2024-11-16T12:00:00"},
        {"lat": 35.1800, "lng": 129.0760, "time": "2024-11-16T12:20:00"},
        {"lat": 35.1810, "lng": 129.0770, "time": "2024-11-16T12:40:00"}
    ],
    "duration": "1시간"
}', '2024-11-16 12:00:00', '2024-11-16 13:00:00', 3.0, FALSE, 'user02', 2);

-- PetLog Table 더미 데이터
INSERT INTO PetLog (note, small, big, log_id, pet_id)
VALUES
('산책 중 소변 1회', 1, 0, 1, 1),
('돌봄 중 배변 1회', 0, 1, 2, 2);

-- Result Table 더미 데이터
INSERT INTO Result (log_id, price, bpm, review)
VALUES
(1, 15000, 120.5, '산책 서비스가 매우 좋았습니다. 강아지가 행복해했어요.'),
(2, 30000, 115.0, '펫 시터 서비스가 매우 만족스러웠습니다.');

-- DogWalker Table 더미 데이터
INSERT INTO DogWalker (user_id, weekday_am, weekday_pm, weekend_am, weekend_pm, `all`)
VALUES
('user01', TRUE, TRUE, FALSE, TRUE, FALSE),
('user03', FALSE, TRUE, TRUE, TRUE, TRUE);



select * from user;
select * from pet;
select * from trade;
select * from cand;
select * from together;
select * from photo;
select * from walklog;
select * from petlog;
select * from result;




-- 	drop table if exists user;
-- 	drop table if exists pet;
-- 	drop table if exists walk_log;
-- 	drop table if exists pet_log;

-- 	create table user (
-- 		`user_id` varchar(30) primary key,
-- 		`password` varchar(30) not null,
-- 		`nickname` varchar(30) not null,
-- 		`age` int(3) zerofill not null,
--         `phone` varchar(11) not null,
-- 		`gender` boolean not null,
-- 		`region` varchar(30) not null,
-- 		`exp` int(10) zerofill not null default 0,
-- 		`dog_walker` boolean default false,
-- 		`hire_cnt` int(10) zerofill not null default 0,
-- 		`hired_cnt` int(10) zerofill not null default 0,
-- 		`bpm` int(10) not null default 125
-- 	);

-- 	describe user;

-- 	create table pet (
-- 		`name` varchar(30) not null,
-- 		`age` int(10) zerofill not null,
-- 		`gender` boolean not null,
-- 		`group` varchar(10),
-- 		`user_id` varchar(30) not null,
-- 		`pet_id` bigint(20) primary key auto_increment,
-- 		foreign key (`user_id`) references user(`user_id`) on delete cascade
-- 	);

-- 	describe pet;

-- 	create table walk_log (
-- 		`log_id` bigint(20) primary key auto_increment,
-- 		`walk_path` json not null,
-- 		`start_time` timestamp not null,
-- 		`end_time` timestamp not null,
-- 		`total` double not null,
-- 		`user_id` varchar(30) not null,
-- 		foreign key (`user_id`) references user(`user_id`) on delete cascade
-- 	);

-- 	describe walk_log;

-- 	create table pet_log (
-- 		`plog_id` bigint(20) primary key auto_increment,
-- 		`note` text,
-- 		`small` int default 0,
-- 		`big` int default 0,
-- 		`log_id` bigint(20) not null,
-- 		`pet_id` bigint(20) not null,
-- 		foreign key (`log_id`) references walk_log(`log_id`) on delete cascade,
-- 		foreign key (`pet_id`) references pet(`pet_id`) on delete cascade
-- 	);
--     
--     describe pet_log;


-- INSERT INTO user (user_id, password, nickname, age, phone, gender, region, exp, dog_walker, hire_cnt, hired_cnt, bpm) VALUES
-- ('user01', 'password1', '사용자1', 25, 01012345678, 1, '서울', 100, false, 0, 0, 125),
-- ('user02', 'password2', '사용자2', 30, 01012341234, 0, '부산', 200, true, 5, 3, 130),
-- ('user03', 'password3', '사용자3', 22, 01012341234, 0, '대구', 50, false, 1, 2, 120),
-- ('user04', 'password4', '사용자4', 24, 01012344321, true, '울산', 150, false, 1, 2, 120);

-- INSERT INTO pet (name, age, gender, `group`, user_id) VALUES
-- ('강아지1', 3, 1, '소형', 'user01'),
-- ('고양이1', 2, 0, '중형', 'user01'),
-- ('강아지2', 1, 1, '대형', 'user02'),
-- ('강아지3', 4, 0, '소형', 'user03');

-- INSERT INTO walk_log (walk_path, start_time, end_time, total, user_id) VALUES
-- ('{"path": [{"lat": 37.5665, "lng": 126.9780}], "duration": "30분"}', '2024-11-01 09:00:00', '2024-11-01 09:30:00', 2.5, 'user01'),
-- ('{"path": [{"lat": 35.1796, "lng": 129.0756}], "duration": "1시간"}', '2024-11-01 10:00:00', '2024-11-01 11:00:00', 4.0, 'user02'),
-- ('{"path": [{"lat": 35.8714, "lng": 128.6010}], "duration": "45분"}', '2024-11-01 11:30:00', '2024-11-01 12:15:00', 3.0, 'user03');


-- INSERT INTO pet_log (note, small, big, log_id, pet_id) VALUES
-- ('산책을 잘 했어요!', 1, 0, 1, 1),
-- ('너무 신났어요!', 0, 1, 1, 2),
-- ('많이 뛰었어요.', 2, 1, 2, 3),
-- ('휴식도 필요해요.', 1, 0, 3, 4);


-- -- insert into user
-- -- values 

-- INSERT INTO user (user_id, password, nickname, age, phone, gender, region)
-- VALUES ('test', 'test', 'test', 1234, '01012341234', true, 'seoul');
-- select * from user;
