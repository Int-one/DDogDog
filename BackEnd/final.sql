DROP database if exists final;

create database final;

use final;
    

-- User Table
CREATE TABLE User (
    user_id VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(255) NOT NULL,
    birth DATETIME NOT NULL,
    phone VARCHAR(11) NOT NULL,
    gender BOOLEAN NOT NULL,
    height DOUBLE NOT NULL,
    weight DOUBLE NOT NULL,
    region VARCHAR(255) NOT NULL,
    exp INT NOT NULL DEFAULT 0,
    dog_walker BOOLEAN NOT NULL DEFAULT FALSE,
    hire_cnt BIGINT NOT NULL DEFAULT 0,
    hired_cnt BIGINT NOT NULL DEFAULT 0,
    bpm DOUBLE NOT NULL DEFAULT 125
);

-- Pet Table
CREATE TABLE Pet (
    pet_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    pet_name VARCHAR(30) NOT NULL,
    pet_birth DATETIME NOT NULL,
    pet_gender BOOLEAN NOT NULL,
    castration BOOLEAN NOT NULL,
    `group` VARCHAR(10) NOT NULL,
    breed VARCHAR(30),
    pet_weight DOUBLE DEFAULT 0,
    pet_photo VARCHAR(255),
    ps TEXT,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Trade Table
CREATE TABLE Trade (
    trade_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    kind VARCHAR(10) NOT NULL,
    trade_start_time DATETIME NOT NULL,
    trade_end_time DATETIME NOT NULL,
    cost BIGINT(20) NOT NULL,
    region VARCHAR(30) NOT NULL,
    detail TEXT,
    state int DEFAULT 0,
    large_dog boolean DEFAULT FALSE,
    super_id VARCHAR(255),
    user_id VARCHAR(255),
    FOREIGN KEY (super_id) REFERENCES User(user_id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE SET NULL
);

-- Cand Table
CREATE TABLE Cand (
    trade_id BIGINT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (trade_id, user_id),
    FOREIGN KEY (trade_id) REFERENCES Trade(trade_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- Together Table
CREATE TABLE Together (
    trade_id BIGINT NOT NULL,
    pet_id INT NOT NULL,
    PRIMARY KEY (trade_id, pet_id),
    FOREIGN KEY (trade_id) REFERENCES Trade(trade_id) ON DELETE CASCADE,
    FOREIGN KEY (pet_id) REFERENCES Pet(pet_id) ON DELETE CASCADE
);

-- Photo Table
CREATE TABLE Photo (
    photo_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    photo_url VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- WalkLog Table
CREATE TABLE WalkLog (
    log_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    walk_path JSON NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    total DOUBLE NOT NULL,
    dog_walking boolean NOT NULL DEFAULT FALSE,
    user_id VARCHAR(255) NOT NULL,
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
	user_id varchar(255) PRIMARY KEY,
    weekday_am boolean NOT NULL DEFAULT FALSE,
    weekday_pm boolean NOT NULL DEFAULT FALSE,
    weekend_am boolean NOT NULL DEFAULT FALSE,
    weekend_pm boolean NOT NULL DEFAULT FALSE,
    `all` boolean NOT NULL DEFAULT FALSE,
    able_time int NOT NULL DEFAULT 0,
    handle boolean NOT NULL DEFAULT FALSE,
    introduce text,
    FOREIGN KEY (user_id) REFERENCES User(user_id) ON DELETE CASCADE
);

-- User Table 더미 데이터
INSERT INTO User (user_id, password, nickname, birth, phone, gender, height, weight, region)
VALUES 
('harrisanita@hotmail.com', 'g5K#q28_$I', '이민수', '2006-08-04', '01038809413', 0, 167.43, 45.83, '부산'),
('levidixon@yahoo.com', '7*F8TqXz(d', '임서연', '1994-02-08', '01032274516', 0, 180.3, 81.4, '울산'),
('jennifermartinez@brown-carr.com', '3$VV2lQgYn', '윤수빈', '1987-10-11', '01018291109', 1, 183.23, 64.81, '광주'),
('johnathan93@summers-thomas.com', 'x37DAVpA^w', '이유진', '2003-04-11', '01036598475', 0, 150.1, 77.72, '인천'),
('lori35@gmail.com', '_11TIYYA@n', '최서연', '2004-05-28', '01030608941', 1, 179.08, 74.43, '수원'),
('jackallen@alvarado-pena.com', '_2VTAFwGj)', '윤동현', '1965-10-28', '01065228044', 0, 156.27, 67.57, '광주'),
('thomasjames@hotmail.com', 'YCJamnAT*9', '박유진', '1971-06-21', '01094285798', 0, 169.14, 51.97, '청주'),
('vlee@murphy-lewis.com', 'czlRUEmM+1', '조동현', '1997-09-12', '01033515921', 0, 169.71, 57.02, '대전'),
('thomas05@gmail.com', 'eybc0BC4O!', '임민수', '1974-09-20', '01084187012', 1, 188.93, 78.39, '성남'),
('kristina65@mckay.com', '+5WPvFhzG(', '오민수', '1975-11-15', '01016419721', 1, 164.37, 50.72, '울산'),
('test@test.com', '1234', 'test', '1999-12-23', '01012341234', 1, 164.36, 64.21, '서울');

-- Pet Table 더미 데이터
INSERT INTO Pet (pet_name, pet_birth, pet_gender, castration, `group`, breed, pet_weight, pet_photo, ps, user_id)
VALUES 
('코코', '2020-05-10', 0, 1, '강아지', '푸들', 5.2, 'https://example.com/photos/pet1.jpg', '활발하고 사교적인 성격입니다.', 'harrisanita@hotmail.com'),
('나비', '2018-07-21', 1, 1, '고양이', '러시안블루', 3.8, 'https://example.com/photos/pet2.jpg', '낯을 가리지만 애교가 많습니다.', 'levidixon@yahoo.com'),
('초코', '2019-12-01', 0, 0, '강아지', '말티즈', 4.5, 'https://example.com/photos/pet3.jpg', '작고 귀여운 크기의 강아지입니다.', 'jennifermartinez@brown-carr.com'),
('몽이', '2021-03-15', 1, 1, '강아지', '시추', 6.1, 'https://example.com/photos/pet4.jpg', '털 관리가 중요합니다.', 'johnathan93@summers-thomas.com'),
('두리', '2017-09-10', 0, 1, '고양이', '샴', 4.3, 'https://example.com/photos/pet5.jpg', '조용하고 차분한 성격입니다.', 'lori35@gmail.com'),
('뭉치', '2022-01-20', 0, 0, '강아지', '진돗개', 12.8, 'https://example.com/photos/pet6.jpg', '한국 전통견으로 충성심이 높습니다.', 'jackallen@alvarado-pena.com'),
('하늘', '2019-04-30', 1, 0, '고양이', '페르시안', 4.7, 'https://example.com/photos/pet7.jpg', '부드럽고 긴 털이 매력적입니다.', 'thomasjames@hotmail.com'),
('보리', '2020-11-05', 0, 1, '강아지', '비숑프리제', 6.5, 'https://example.com/photos/pet8.jpg', '사람을 좋아하고 잘 따릅니다.', 'vlee@murphy-lewis.com'),
('구름', '2016-06-17', 1, 1, '강아지', '웰시코기', 11.3, 'https://example.com/photos/pet9.jpg', '짧은 다리와 긴 몸이 특징입니다.', 'thomas05@gmail.com'),
('Bud', '2016-06-17', 1, 1, 'Small', 'Beagle', 12.5, 'petPhoto/1732081391173_하츄핑.jpeg', 'My lovely dog', 'test@test.com'),
('Buddy', '2018-06-27', 1, 0, 'Big', '웰시코기', 11.3, 'petPhoto/1732067779355_input.jpg', '짧은 다리와 긴 몸이 특징입니다.', 'test@test.com'),
('별이', '2015-03-25', 1, 0, '고양이', '먼치킨', 3.2, 'https://example.com/photos/pet10.jpg', '활발하고 장난기 많은 성격입니다.', 'kristina65@mckay.com');

-- Trade Table 더미 데이터
INSERT INTO Trade (kind, trade_start_time, trade_end_time, cost, region, detail, state, large_dog, super_id, user_id)
VALUES 
('해드려요', '2024-11-15 10:00:00', '2024-11-15 12:00:00', 50000, '서울', '강아지 산책 대행 요청', 0, FALSE, 'harrisanita@hotmail.com', 'levidixon@yahoo.com'),
('해드려요', '2024-11-16 09:00:00', '2024-11-16 18:00:00', 120000, '부산', '고양이 하루 종일 돌봄 요청', 0, FALSE, 'jennifermartinez@brown-carr.com', 'johnathan93@summers-thomas.com'),
('해주세요', '2024-11-17 15:00:00', '2024-11-17 16:30:00', 30000, '광주', '작은 강아지 산책', 0, FALSE, NULL, 'lori35@gmail.com'),
('해주세요', '2024-11-18 08:00:00', '2024-11-18 19:00:00', 150000, '대전', '노령견 하루 돌봄', 0, FALSE, 'thomasjames@hotmail.com', NULL),
('해드려요', '2024-11-19 14:00:00', '2024-11-19 15:30:00', 40000, '인천', '말티즈 산책 대행', 0, FALSE, 'vlee@murphy-lewis.com', 'jackallen@alvarado-pena.com'),
('해주세요', '2024-11-20 10:00:00', '2024-11-20 16:00:00', 80000, '울산', '강아지 반나절 돌봄', 0, TRUE, 'kristina65@mckay.com', 'thomas05@gmail.com'),
('해드려요', '2024-11-21 17:00:00', '2024-11-21 18:30:00', 35000, '성남', '활동적인 강아지 산책', 0, TRUE, 'harrisanita@hotmail.com', 'jennifermartinez@brown-carr.com'),
('해주세요', '2024-11-22 08:00:00', '2024-11-22 20:00:00', 180000, '청주', '페르시안 고양이 돌봄 요청', 0, FALSE, NULL, 'levidixon@yahoo.com'),
('해드려요', '2024-11-23 13:00:00', '2024-11-23 14:30:00', 45000, '대구', '소형견 산책 대행', 0, FALSE, 'johnathan93@summers-thomas.com', 'lori35@gmail.com'),
('해주세요', '2024-11-24 07:00:00', '2024-11-24 21:00:00', 200000, '서울', '하루 종일 돌봄 요청', 0, FALSE, 'jackallen@alvarado-pena.com', 'thomasjames@hotmail.com');

-- Cand Table 더미 데이터
INSERT INTO Cand (trade_id, user_id)
VALUES 
(1, 'harrisanita@hotmail.com'),
(2, 'levidixon@yahoo.com'),
(2, 'jennifermartinez@brown-carr.com'),
(3, 'johnathan93@summers-thomas.com'),
(4, 'lori35@gmail.com'),
(4, 'jackallen@alvarado-pena.com'),
(5, 'thomasjames@hotmail.com'),
(6, 'vlee@murphy-lewis.com'),
(6, 'thomas05@gmail.com'),
(6, 'kristina65@mckay.com'),
(7, 'harrisanita@hotmail.com'),
(8, 'levidixon@yahoo.com'),
(8, 'jennifermartinez@brown-carr.com'),
(9, 'johnathan93@summers-thomas.com'),
(9, 'lori35@gmail.com'),
(10, 'jackallen@alvarado-pena.com'),
(10, 'thomasjames@hotmail.com'),
(10, 'vlee@murphy-lewis.com'),
(10, 'thomas05@gmail.com'),
(10, 'kristina65@mckay.com');


INSERT INTO Together (trade_id, pet_id)
VALUES 
(1, 1),
(1, 2),
(2, 3),
(3, 4),
(3, 5),
(4, 6),
(5, 7),
(5, 8),
(6, 9),
(6, 10),
(7, 1),
(8, 2),
(9, 3),
(10, 4),
(10, 5);

-- Photo Table 더미 데이터
INSERT INTO Photo (photo_url, user_id)
VALUES 
('https://example.com/photos/photo1.jpg', 'harrisanita@hotmail.com'),
('https://example.com/photos/photo2.jpg', 'levidixon@yahoo.com'),
('https://example.com/photos/photo3.jpg', 'jennifermartinez@brown-carr.com'),
('https://example.com/photos/photo4.jpg', 'johnathan93@summers-thomas.com'),
('https://example.com/photos/photo5.jpg', 'lori35@gmail.com'),
('https://example.com/photos/photo6.jpg', 'jackallen@alvarado-pena.com'),
('https://example.com/photos/photo7.jpg', 'thomasjames@hotmail.com'),
('https://example.com/photos/photo8.jpg', 'vlee@murphy-lewis.com'),
('https://example.com/photos/photo9.jpg', 'thomas05@gmail.com'),
('https://example.com/photos/photo10.jpg', 'kristina65@mckay.com');

-- WalkLog Table 더미 데이터
INSERT INTO WalkLog (walk_path, start_time, end_time, total, dog_walking, user_id, trade_id)
VALUES 
(
    '[
            {"lat": 37.5665, "lng": 126.9780, "time": "2024-11-15 10:00:00"},
            {"lat": 37.5670, "lng": 126.9790, "time": "2024-11-15 10:15:00"},
            {"lat": 37.5680, "lng": 126.9800, "time": "2024-11-15 10:30:00"}
        ]', 
    '2024-11-15 10:00:00', 
    '2024-11-15 11:00:00', 
    2.5, 
    TRUE, 
    'harrisanita@hotmail.com', 
    1
),
(
    '[
            {"lat": 37.5745, "lng": 126.9900, "time": "2024-11-16 08:00:00"},
            {"lat": 37.5750, "lng": 126.9910, "time": "2024-11-16 08:15:00"},
            {"lat": 37.5760, "lng": 126.9920, "time": "2024-11-16 08:30:00"}
        ]', 
    '2024-11-16 08:00:00', 
    '2024-11-16 08:30:00', 
    1.2, 
    FALSE, 
    'levidixon@yahoo.com', 
    NULL
),
(
    '[
            {"lat": 37.5690, "lng": 126.9820, "time": "2024-11-17 18:00:00"},
            {"lat": 37.5700, "lng": 126.9830, "time": "2024-11-17 18:20:00"},
            {"lat": 37.5710, "lng": 126.9840, "time": "2024-11-17 18:40:00"}
        ]', 
    '2024-11-17 18:00:00', 
    '2024-11-17 18:40:00', 
    1.8, 
    TRUE, 
    'jennifermartinez@brown-carr.com', 
    NULL
),
(
    '[
            {"lat": 37.5690, "lng": 126.9820, "time": "2024-11-17 18:00:00"},
            {"lat": 37.5700, "lng": 126.9830, "time": "2024-11-17 18:20:00"},
            {"lat": 37.5710, "lng": 126.9840, "time": "2024-11-17 18:40:00"}
        ]', 
    '2024-11-17 18:00:00', 
    '2024-11-17 18:40:00', 
    1.8, 
    TRUE, 
    'test@test.com', 
    NULL
),
(
    '[
            {"lat": 37.5690, "lng": 126.9820, "time": "2024-11-17 18:00:00"},
            {"lat": 37.5700, "lng": 126.9830, "time": "2024-11-17 18:20:00"},
            {"lat": 37.5710, "lng": 126.9840, "time": "2024-11-17 18:40:00"}
        ]', 
    '2024-11-12 18:00:00', 
    '2024-11-12 18:40:00', 
    3.8, 
    TRUE, 
    'test@test.com', 
    NULL
);

-- PetLog Table 더미 데이터
INSERT INTO PetLog (note, small, big, log_id, pet_id)
VALUES
('산책 중 강아지가 활발하게 뛰어놀며 체력을 소모했습니다.', 1, 0, 1, 1),
('조용히 산책을 즐기며 주변 환경을 관찰했습니다.', 0, 0, 1, 2),
('활발하게 움직이며 산책을 즐겼고 강아지가 건강해 보였습니다.', 1, 0, 2, 9),
('주변 환경에 대한 관심을 보이며 긴장을 풀고 산책했습니다.', 1, 1, 2, 10),
('산책 중 흥미롭게 주변을 살피며 활동적이었습니다.', 1, 0, 3, 4),
('산책 중 흥미롭게 주변을 살피며 활동적이었습니다.', 1, 0, 2, 10),
('조용하고 차분하게 산책을 즐겼습니다.', 0, 1, 3, 5);


-- Result Table 더미 데이터
INSERT INTO Result (log_id, price, bpm, review)
VALUES
(1, 25000, 120.5, '서비스가 만족스러웠습니다. 강아지가 산책을 즐긴 것 같아요. 다음에도 이용하고 싶습니다.'),
(3, 18000, 115.2, '산책 후 강아지가 매우 행복해 보였습니다. 하지만 시간이 약간 늦었습니다.');


-- DogWalker Table 더미 데이터
INSERT INTO DogWalker (user_id, weekday_am, weekday_pm, weekend_am, weekend_pm, `all`, able_time, handle, introduce)
VALUES
('harrisanita@hotmail.com', TRUE, TRUE, TRUE, TRUE, FALSE, 3, TRUE, '강아지와 오랜 산책 경험이 있습니다. 주중과 주말 모두 가능하며, 특히 활발한 강아지를 잘 다룹니다.'),
('levidixon@yahoo.com', FALSE, TRUE, TRUE, FALSE, FALSE, 2, FALSE, '작은 강아지를 돌보는 데 익숙하며, 주말 오전 산책에 주력합니다.'),
('jennifermartinez@brown-carr.com', TRUE, FALSE, FALSE, TRUE, FALSE, 1, TRUE, '고양이와 강아지 모두 다룰 수 있는 경험이 풍부한 반려동물 전문가입니다.'),
('johnathan93@summers-thomas.com', FALSE, TRUE, TRUE, TRUE, TRUE, 3, TRUE, '장시간 산책이 필요한 반려동물에게 적합합니다. 주말과 주중 오후가 가능합니다.'),
('lori35@gmail.com', TRUE, FALSE, TRUE, FALSE, FALSE, 2, FALSE, '소형견 산책에 특화되어 있으며, 주중 오전과 주말 오전에 활동 가능합니다.'),
('thomasjames@hotmail.com', TRUE, TRUE, TRUE, TRUE, TRUE, 3, TRUE, '대형견을 포함하여 다양한 크기의 강아지를 돌볼 수 있습니다. 언제든지 가능합니다.'),
('kristina65@mckay.com', FALSE, FALSE, FALSE, TRUE, FALSE, 1, FALSE, '반려동물 돌봄 초보자지만 강아지와의 상호작용을 즐깁니다. 주말 오후만 가능합니다.');

        

select * from user;
select * from pet;
select * from trade;
select * from cand;
select * from together;
select * from photo;
select * from walklog;
select * from petlog;
select * from result;
select * from dogwalker;