DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS clubs CASCADE;
DROP TABLE IF EXISTS club_join_states CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS pictures CASCADE;
DROP TABLE IF EXISTS comments CASCADE;


CREATE TABLE users
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    email               varchar(50)     NOT NULL COMMENT '사용자 이메일',
    password            varchar(80)     NOT NULL COMMENT '비밀번호',
    name                varchar(10)     NOT NULL COMMENT '이름',
    nickname            varchar(20)     NOT NULL COMMENT '닉네임',
    university          varchar(20)     NOT NULL COMMENT '대학교명',
    profile_url         varchar(500)    DEFAULT NULL COMMENT '프로필 사진',
    introduction        varchar(100)    NOT NULL COMMENT '자기소개',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (id),
    KEY users_idx_nickname (nickname)
);


CREATE TABLE categories
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    name                varchar(20)     NOT NULL COMMENT '카테고리명',
    description         varchar(50)     NOT NULL COMMENT '카테고리 설명',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    PRIMARY KEY (id)
);


CREATE TABLE clubs
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    name                varchar(10)     NOT NULL COMMENT '동아리명',
    image_url           varchar(500)    DEFAULT NULL COMMENT '동아리 사진',
    address             varchar(50)     NOT NULL COMMENT '동아리 위치',
    university          varchar(20)     NOT NULL COMMENT '소속 대학교',
    description         varchar(100)    NOT NULL COMMENT '동아리 소개',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    category_id         bigint          DEFAULT NULL COMMENT '카테고리 id',
    PRIMARY KEY (id),
    KEY clubs_idx_name (name),
    KEY clubs_idx_university (university),
    KEY clubs_idx_name_university (name, university),
    CONSTRAINT fk_club_to_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE club_join_states
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    join_state          varchar(10)     NOT NULL COMMENT '가입 상태(관리자, 운영진, 일반 회원, 미가입)',
    is_used             boolean         NOT NULL DEFAULT 0 COMMENT '동아리 탈퇴 여부 판단',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    user_id             bigint          DEFAULT NULL COMMENT '사용자 id',
    club_id             bigint          DEFAULT NULL COMMENT '동아리 id',
    PRIMARY KEY (id),
    KEY join_states_idx_user (user_id),
    KEY join_states_idx_club (club_id),
    KEY join_states_idx_user_join_state (user_id, join_state),
    KEY join_states_idx_club_join_state (club_id, join_state),
    CONSTRAINT fk_join_state_to_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_join_state_to_club FOREIGN KEY (club_id) REFERENCES clubs (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);


CREATE TABLE pictures
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    url                 varchar(500)    NOT NULL COMMENT '사진 URL',
    description         varchar(100)    DEFAULT NULL COMMENT '사진 설명',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    user_id             bigint          DEFAULT NULL COMMENT '사용자 id',
    club_id             bigint          DEFAULT NULL COMMENT '동아리 id',
    PRIMARY KEY (id),
    KEY pictures_idx_user (user_id),
    KEY pictures_idx_club (club_id),
    CONSTRAINT fk_pictures_to_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_pictures_to_club FOREIGN KEY (club_id) REFERENCES clubs (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE posts
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    content             varchar(500)    NOT NULL COMMENT '게시물 내용',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    user_id             bigint          DEFAULT NULL COMMENT '사용자 id',
    club_id             bigint          DEFAULT NULL COMMENT '동아리 id',
    PRIMARY KEY (id),
    KEY posts_idx_user (user_id),
    KEY posts_idx_club (club_id),
    CONSTRAINT fk_posts_to_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_posts_to_club FOREIGN KEY (club_id) REFERENCES clubs (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE comments
(
    id                  bigint          NOT NULL AUTO_INCREMENT COMMENT 'id',
    content             varchar(500)    NOT NULL COMMENT '댓글 내용',
    created_at          datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at          datetime        DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    post_id             bigint          DEFAULT NULL COMMENT '게시물 id',
    user_id             bigint          DEFAULT NULL COMMENT '사용자 id',
    PRIMARY KEY (id),
    KEY comments_idx_post (post_id),
    KEY comments_idx_user (user_id),
    CONSTRAINT fk_comments_to_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_comments_to_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);