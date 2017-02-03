create table THEME (
  theme_id   INT         NOT NULL IDENTITY,
  theme_name VARCHAR(50) NOT NULL UNIQUE,
  start_date TIMESTAMP   DEFAULT NULL NULL,
  end_date   TIMESTAMP   DEFAULT NULL NULL,
  link       VARCHAR(100) DEFAULT NULL
);

create table THEME_OPTION(
  option_id INT NOT NULL IDENTITY,
  option_name VARCHAR(30) NOT NULL,
  quantity INT DEFAULT 0 NOT NULL,
  theme_id INT NOT NULL
);

insert into THEME (theme_name) values ('Откуда Вы узнали о нас?');
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('tut.by', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Откуда Вы узнали о нас?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('onliner.by', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Откуда Вы узнали о нас?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('bbc.com', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Откуда Вы узнали о нас?'));

insert into THEME (theme_name) values ('What is better: iphone or galaxy?');
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('iPhone', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What is better: iphone or galaxy?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Galaxy', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What is better: iphone or galaxy?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Siemens C55', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What is better: iphone or galaxy?'));

insert into THEME (theme_name) values ('Сколько Вам лет?');
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Мало', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Сколько Вам лет?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Средне', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Сколько Вам лет?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Много не бывает', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Сколько Вам лет?'));

insert into THEME (theme_name) values ('Страдаете ли Вы бессонницей?');
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Да', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Страдаете ли Вы бессонницей?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Нет', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Страдаете ли Вы бессонницей?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Не знаю', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'Страдаете ли Вы бессонницей?'));

insert into THEME (theme_name) values ('What was earlier: a hen or an egg?');
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Hen', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What was earlier: a hen or an egg?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Egg', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What was earlier: a hen or an egg?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('Bacteria', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What was earlier: a hen or an egg?'));
insert into THEME_OPTION (option_name, quantity, theme_id) VALUES ('God', '0',
                                                                   (select THEME.theme_id from THEME where theme_name = 'What was earlier: a hen or an egg?'));