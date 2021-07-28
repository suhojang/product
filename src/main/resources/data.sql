INSERT INTO users (created_at, updated_at) VALUES (NOW(), NOW());

INSERT INTO PRODUCT (title, total_investing_amount, started_at, finished_at) VALUES ('제약회사 보험상품',1000000, NOW(), date_add(now(),INTERVAL 3 DAY));
INSERT INTO PRODUCT (title, total_investing_amount, started_at, finished_at) VALUES ('독서실 보험상품',3000000, NOW(), date_add(now(),INTERVAL 3 DAY));