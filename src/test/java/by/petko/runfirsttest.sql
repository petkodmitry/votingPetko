DROP DATABASE IF EXISTS voting_test;
create DATABASE voting_test;
use voting_test;
create table THEMES (
  theme_id   INT         NOT NULL AUTO_INCREMENT,
  theme_name VARCHAR(50) NOT NULL UNIQUE,
  start_date TIMESTAMP   NULL DEFAULT NULL,
  end_date   TIMESTAMP   NULL DEFAULT NULL,
  link       VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (theme_id)
);

create table OPTIONS(
  option_id INT NOT NULL AUTO_INCREMENT,
  option_name VARCHAR(30) NOT NULL,
  quantity INT NOT NULL,
  theme_id INT NOT NULL,
  PRIMARY KEY (option_id)
);

insert into THEMES (theme_name) values ('Откуда Вы узнали о нас?');
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('tut.by', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Откуда Вы узнали о нас?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('onliner.by', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Откуда Вы узнали о нас?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('bbc.com', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Откуда Вы узнали о нас?'));

insert into THEMES (theme_name) values ('What is better: iphone or galaxy?');
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('iPhone', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What is better: iphone or galaxy?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Galaxy', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What is better: iphone or galaxy?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Siemens C55', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What is better: iphone or galaxy?'));

insert into THEMES (theme_name) values ('Сколько Вам лет?');
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Мало', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Сколько Вам лет?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Средне', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Сколько Вам лет?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Много не бывает', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Сколько Вам лет?'));

insert into THEMES (theme_name) values ('Страдаете ли Вы бессонницей?');
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Да', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Страдаете ли Вы бессонницей?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Нет', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Страдаете ли Вы бессонницей?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Не знаю', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'Страдаете ли Вы бессонницей?'));

insert into THEMES (theme_name) values ('What was earlier: a hen or an egg?');
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Hen', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What was earlier: a hen or an egg?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Egg', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What was earlier: a hen or an egg?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('Bacteria', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What was earlier: a hen or an egg?'));
insert into OPTIONS (option_name, quantity, theme_id) VALUES ('God', '0',
                    (select THEMES.theme_id from THEMES where theme_name = 'What was earlier: a hen or an egg?'));