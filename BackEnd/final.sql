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

CREATE TABLE Board (
	board_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id varchar(255),
    writer VARCHAR(255) NOT NULL,
    title VARCHAR(50) NOT NULL,
    content TEXT,
    view_cnt INT DEFAULT 0,
    reg_date TIMESTAMP DEFAULT now(),
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
('jiwoo99@gmail.com', 'asdfasdf1234', '지우지우', '1999-03-12', '01012344321', 0, 178.32, 65.36, '서울'),
('test@test.com', '1234', 'test', '1999-12-23', '01012341234', 1, 164.36, 64.21, '서울'),
('test@test', '1234', '하나둘', '1999-12-23', '01012341234', 1, 164.36, 64.21, '서울');

-- Pet Table 더미 데이터
INSERT INTO Pet (pet_name, pet_birth, pet_gender, castration, `group`, breed, pet_weight, pet_photo, ps, user_id)
VALUES 
('코코', '2020-05-10', 0, 1, '소형견', '푸들', 5.2, 'https://example.com/photos/pet1.jpg', '활발하고 사교적인 성격입니다.', 'harrisanita@hotmail.com'),
('나비', '2018-07-21', 1, 1, '소형견', '푸들', 3.8, 'https://example.com/photos/pet2.jpg', '낯을 가리지만 애교가 많습니다.', 'levidixon@yahoo.com'),
('초코', '2019-12-01', 0, 0, '중형견', '말티즈', 4.5, 'https://example.com/photos/pet3.jpg', '작고 귀여운 크기의 강아지입니다.', 'jennifermartinez@brown-carr.com'),
('몽이', '2021-03-15', 1, 1, '대형견', '시추', 6.1, 'https://example.com/photos/pet4.jpg', '털 관리가 중요합니다.', 'johnathan93@summers-thomas.com'),
('두리', '2017-09-10', 0, 1, '중형견', '진돗개', 4.3, 'https://example.com/photos/pet5.jpg', '조용하고 차분한 성격입니다.', 'lori35@gmail.com'),
('뭉치', '2022-01-20', 0, 0, '중형견', '진돗개', 12.8, 'https://example.com/photos/pet6.jpg', '한국 전통견으로 충성심이 높습니다.', 'jackallen@alvarado-pena.com'),
('하늘', '2019-04-30', 1, 0, '소형견', '포메라니안', 4.7, 'https://example.com/photos/pet7.jpg', '부드럽고 긴 털이 매력적입니다.', 'thomasjames@hotmail.com'),
('보리', '2020-11-05', 0, 1, '대형견', '비숑프리제', 6.5, 'https://example.com/photos/pet8.jpg', '사람을 좋아하고 잘 따릅니다.', 'vlee@murphy-lewis.com'),
('구름', '2016-06-17', 1, 1, '중형견', '말티즈', 11.3, 'https://example.com/photos/pet9.jpg', '짧은 다리와 긴 몸이 특징입니다.', 'thomas05@gmail.com'),
('바다', '2016-06-17', 1, 1, '소형견', '포메라니안', 12.5, 'petPhoto/1732081391173_하츄핑.jpeg', 'My lovely dog', 'test@test.com'),
('구리', '2018-06-27', 1, 0, '대형견', '사모예드', 11.3, 'petPhoto/1732067779355_input.jpg', '짧은 다리와 긴 몸이 특징입니다.', 'test@test.com'),
('우비', '2023-06-17', 1, 1, '소형견', '포메라니안', 2.5, 'petPhoto/우비.jpg', '우비 쏘 큩ㅠㅠ', 'test@test'),
('메롱', '2022-03-27', 1, 0, '중형견', '시바견', 6.3, 'petPhoto/메롱.jpg', '메롱하는거도 귀여워.', 'test@test'),
('탄', '2021-04-14', 1, 1, '소형견', '포메라니안', 3.2, 'petPhoto/탄.jpg', '귀엽고 예쁘고 착해', 'jiwoo99@gmail.com'),
('별이', '2015-03-25', 1, 0, '소형견', '포메라니안', 3.2, 'https://example.com/photos/pet10.jpg', '활발하고 장난기 많은 성격입니다.', 'kristina65@mckay.com');

-- Trade Table 더미 데이터
INSERT INTO Trade (kind, trade_start_time, trade_end_time, cost, region, detail, state, large_dog, super_id, user_id)
VALUES 
('해드려요', '2024-11-15 10:00:00', '2024-11-15 12:00:00', 25000, '서울', '강아지 산책 대행 요청', 0, FALSE, 'harrisanita@hotmail.com', 'levidixon@yahoo.com'),
('해드려요', '2024-11-16 17:00:00', '2024-11-16 18:00:00', 20000, '부산', '고양이 하루 종일 돌봄 요청', 0, FALSE, 'jennifermartinez@brown-carr.com', 'johnathan93@summers-thomas.com'),
('해주세요', '2024-11-17 15:00:00', '2024-11-17 16:30:00', 30000, '광주', '작은 강아지 산책', 0, FALSE, 'lori35@gmail.com', NULL),
('해주세요', '2024-11-18 18:00:00', '2024-11-18 19:00:00', 15000, '대전', '노령견 하루 돌봄', 0, FALSE, 'thomasjames@hotmail.com', NULL),
('해드려요', '2024-11-19 14:00:00', '2024-11-19 15:30:00', 30000, '인천', '말티즈 산책 대행', 0, FALSE, 'vlee@murphy-lewis.com', 'jackallen@alvarado-pena.com'),
('해주세요', '2024-11-20 14:00:00', '2024-11-20 16:00:00', 40000, '울산', '강아지 반나절 돌봄', 0, TRUE, 'kristina65@mckay.com', 'thomas05@gmail.com'),
('해드려요', '2024-11-21 17:00:00', '2024-11-21 18:30:00', 35000, '성남', '활동적인 강아지 산책', 0, TRUE, 'harrisanita@hotmail.com', 'jennifermartinez@brown-carr.com'),
('해주세요', '2024-11-22 19:00:00', '2024-11-22 20:00:00', 18000, '서울', '포메라니안 산책해주세요', 0, FALSE, 'jiwoo99@gmail.com', NULL),
('해드려요', '2024-11-23 13:00:00', '2024-11-23 14:30:00', 45000, '대구', '소형견 산책 대행', 0, FALSE, 'johnathan93@summers-thomas.com', 'lori35@gmail.com'),
('해주세요', '2024-11-24 20:00:00', '2024-11-24 21:00:00', 20000, '서울', '저녁 산책 요청', 1, FALSE, 'jackallen@alvarado-pena.com', 'thomasjames@hotmail.com');

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
(8, 14),
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
    'test@test', 
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
    'test@test', 
    NULL
),
(
    '[
        { "lat": 37.500622, "lng": 127.036456, "time": "2024-11-16 08:00:00" },
        { "lat": 37.500430, "lng": 127.035900, "time": "2024-11-16 08:01:00" },
        { "lat": 37.500210, "lng": 127.035300, "time": "2024-11-16 08:02:00" },
        { "lat": 37.499980, "lng": 127.034700, "time": "2024-11-16 08:03:00" },
        { "lat": 37.499750, "lng": 127.034100, "time": "2024-11-16 08:04:00" },
        { "lat": 37.499520, "lng": 127.033500, "time": "2024-11-16 08:05:00" },
        { "lat": 37.499300, "lng": 127.032900, "time": "2024-11-16 08:06:00" },
        { "lat": 37.499080, "lng": 127.032300, "time": "2024-11-16 08:07:00" },
        { "lat": 37.498850, "lng": 127.031700, "time": "2024-11-16 08:08:00" },
        { "lat": 37.498620, "lng": 127.031100, "time": "2024-11-16 08:09:00" },
        { "lat": 37.498400, "lng": 127.030500, "time": "2024-11-16 08:10:00" },
        { "lat": 37.498180, "lng": 127.029900, "time": "2024-11-16 08:11:00" },
        { "lat": 37.497960, "lng": 127.029300, "time": "2024-11-16 08:12:00" },
        { "lat": 37.497780, "lng": 127.028800, "time": "2024-11-16 08:13:00" },
        { "lat": 37.497690, "lng": 127.028200, "time": "2024-11-16 08:14:00" },
        { "lat": 37.497650, "lng": 127.027700, "time": "2024-11-16 08:15:00" },
        { "lat": 37.497642, "lng": 127.027621, "time": "2024-11-16 08:16:00" }
        ]', 
    '2024-11-16 08:00:00', 
    '2024-11-16 08:16:00', 
	0.85, 
    FALSE, 
    'test@test', 
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

-- Board Table 더미 데이터
INSERT INTO Board(title, user_id, writer, content)
VALUES
("우리 강아지가 새 친구를 만났어요!", "harrisanita@hotmail.com", "이민수", "오늘 산책 중에 강아지가 다른 강아지와 친구가 되었어요. 서로 너무 잘 어울리더라고요!"),
("강아지 발바닥이 뜨거울 때 어떻게 해야 하나요?", "levidixon@yahoo.com", "임서연", "여름철 산책 중 강아지 발바닥이 뜨거워질까 걱정됩니다. 꿀팁 있으면 공유해주세요!"),
("처음으로 반려견과 캠핑 다녀왔습니다.", "jennifermartinez@brown-carr.com", "윤수빈", "우리 아이와 함께 캠핑을 다녀왔어요. 첫 경험이라 부족한 점도 많았지만 너무 즐거웠습니다."),
("강아지 입양 준비물 리스트 공유", "johnathan93@summers-thomas.com", "이유진", "입양 전 준비해야 할 물품 리스트와 팁을 공유합니다. 도움이 되었으면 좋겠어요!"),
("반려견 목욕, 너무 싫어해요 ㅠㅠ", "lori35@gmail.com", "최서연", "우리 강아지가 목욕을 너무 싫어합니다. 목욕을 잘 시킬 수 있는 방법 아시는 분 계신가요?"),
("강아지와 함께한 첫 바다 여행", "jackallen@alvarado-pena.com", "윤동현", "우리 강아지가 처음으로 바다를 봤어요! 모래사장에서 신나게 뛰어노는 모습을 보니 너무 행복하네요."),
("강아지가 갑자기 사료를 안 먹어요.", "thomasjames@hotmail.com", "박유진", "며칠 전부터 사료를 잘 먹지 않아서 걱정입니다. 사료 바꿔야 할까요?"),
("강아지 산책 시 좋은 리드줄 추천", "vlee@murphy-lewis.com", "조동현", "편하고 강아지도 좋아할만한 리드줄 추천 부탁드려요."),
("강아지 생일파티 준비 완료!", "thomas05@gmail.com", "임민수", "이번 주말 우리 강아지 생일이에요! 맛있는 간식과 예쁜 케이크로 파티 준비했답니다."),
("강아지와 함께 아침 조깅 시작!", "kristina65@mckay.com", "오민수", "요즘 매일 아침 강아지와 함께 조깅을 해요. 건강도 챙기고 유대감도 깊어지는 것 같아요."),
("새로운 간식 추천 부탁드려요!", "harrisanita@hotmail.com", "이민수", "우리 강아지가 좋아할 만한 건강한 간식 추천해주세요~"),
("강아지와 여름철 더위 피하는 방법", "levidixon@yahoo.com", "임서연", "무더운 여름, 강아지와 더위를 피하는 꿀팁 공유 부탁드립니다."),
("우리 강아지가 드디어 기본 훈련 성공!", "jennifermartinez@brown-carr.com", "윤수빈", "앉아, 기다려, 손! 모두 성공했어요. 너무 뿌듯합니다."),
("반려견과 함께 찍은 추억 사진 공유", "johnathan93@summers-thomas.com", "이유진", "오늘 산책하면서 찍은 사진이에요. 우리 아이 너무 귀엽죠?"),
("강아지 장난감 추천해주세요!", "lori35@gmail.com", "최서연", "오래 가지고 놀 수 있는 강아지 장난감 뭐가 있을까요? 추천 부탁드립니다.");



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