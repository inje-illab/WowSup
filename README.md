# WowSup
와써?(Wow! Sup?) 프로젝트 1차 기획 요약

# 1차 회의 안건
![preview1](./app/src/main/res/drawable/january_16_2019_second_meeting.jpg)

# 첫 번째 시안
![preview2](./app/src/main/res/drawable/the_first_concept.jpg)

# 두 번째 시안
![preview3](./app/src/main/res/drawable/the_second_concept.jpg)

# 데이터베이스 구상도 시안
![preview4](./app/src/main/res/drawable/database_concepts.jpg)


## 안드로이드와 서버간의 HTTP 통신을 위한 테스트 데이터베이스 구조
    create table user_manage(
        user_id varchar(32) not null,
        user_pw varchar(32) not null,
        user_email varchar(32) not null unique,
        primary key (user_id)
    );