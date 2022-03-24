INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test@gmail.com','1q2w3e','테스트','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());
INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test1@gmail.com','1q2w3e','테스트1','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());
INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test2@gmail.com','1q2w3e','테스트2','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());
INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test3@gmail.com','1q2w3e','테스트3','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());
INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test4@gmail.com','1q2w3e','테스트4','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());
INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test5@gmail.com','1q2w3e','테스트5','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());
INSERT INTO users(id,email,password,name,nickname,university,introduction,created_at) VALUES (null,'test6@gmail.com','1q2w3e','테스트6','테스트닉네임','서울사이버대학교','안녕하세요',CURRENT_TIMESTAMP());

INSERT INTO categories(id, name, description, created_at) VALUES (null, '문화/예술/공연', '문화/예술/공연 관련 동아리입니다.',CURRENT_TIMESTAMP());
INSERT INTO categories(id, name, description, created_at) VALUES (null, '봉사/사회활동', '봉사/사회활동 관련 동아리입니다.',CURRENT_TIMESTAMP());
INSERT INTO categories(id, name, description, created_at) VALUES (null, '학술/교양', '학술/교양 관련 동아리입니다.',CURRENT_TIMESTAMP());
INSERT INTO categories(id, name, description, created_at) VALUES (null, '창업/취업', '창업/취업 관련 동아리입니다.',CURRENT_TIMESTAMP());
INSERT INTO categories(id, name, description, created_at) VALUES (null, '어학', '어학 관련 동아리입니다.',CURRENT_TIMESTAMP());
INSERT INTO categories(id, name, description, created_at) VALUES (null, '체육', '체육 관련 동아리입니다.',CURRENT_TIMESTAMP());
INSERT INTO categories(id, name, description, created_at) VALUES (null, '친목', '친목 관련 동아리입니다.',CURRENT_TIMESTAMP());


INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '문화/예술/공연1',null,'A동','서울사이버대학교','문화/예술/공연 동아리입니다.',1,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '봉사/사회활동1',null,'A동','서울사이버대학교','봉사/사회활동 동아리입니다.',2,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '학술/교양1',null,'A동','서울사이버대학교','학술/교양 동아리입니다.',3,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '창업/취업1',null,'A동','서울사이버대학교','창업/취업 동아리입니다.',4,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '어학1',null,'A동','서울사이버대학교','어학 동아리입니다.',5,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '체육1',null,'A동','서울사이버대학교','체육 동아리입니다.',6,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '친목1',null,'A동','서울사이버대학교','친목 동아리입니다.',7,CURRENT_TIMESTAMP());

INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '문화/예술/공연2',null,'A동','서울사이버대학교','문화/예술/공연 동아리입니다.',1,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '봉사/사회활동2',null,'A동','서울사이버대학교','봉사/사회활동 동아리입니다.',2,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '학술/교양2',null,'A동','서울사이버대학교','학술/교양 동아리입니다.',3,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '창업/취업2',null,'A동','서울사이버대학교','창업/취업 동아리입니다.',4,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '어학2',null,'A동','서울사이버대학교','어학 동아리입니다.',5,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '체육2',null,'A동','서울사이버대학교','체육 동아리입니다.',6,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '친목2',null,'A동','서울사이버대학교','친목 동아리입니다.',7,CURRENT_TIMESTAMP());

INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '문화/예술/공연3',null,'A동','테스트대학교','문화/예술/공연 동아리입니다.',1,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '봉사/사회활동3',null,'A동','테스트대학교','봉사/사회활동 동아리입니다.',2,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '학술/교양3',null,'A동','테스트대학교','학술/교양 동아리입니다.',3,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '창업/취업3',null,'A동','테스트대학교','창업/취업 동아리입니다.',4,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '어학3',null,'A동','테스트대학교','어학 동아리입니다.',5,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '체육3',null,'A동','테스트대학교','체육 동아리입니다.',6,CURRENT_TIMESTAMP());
INSERT INTO clubs(id, name, image_url, address, university, description, category_id, created_at) VALUES (null, '친목3',null,'A동','테스트대학교','친목 동아리입니다.',7,CURRENT_TIMESTAMP());

INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 1, true, 1, 1, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 2, true, 1, 2, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 3, true, 1, 3, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 4, true, 1, 4, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 1, true, 1, 5, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 3, true, 1, 6, CURRENT_TIMESTAMP());

INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 3, true, 2, 6, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 2, true, 3, 6, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 1, true, 4, 6, CURRENT_TIMESTAMP());

INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 4, true, 2, 1, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 4, true, 3, 1, CURRENT_TIMESTAMP());
INSERT INTO club_join_states(id, join_state, is_used, user_id, club_id, created_at) VALUES(null, 4, true, 4, 1, CURRENT_TIMESTAMP());