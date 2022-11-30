-- SQL script to create and fill in tables used in the project

drop table if exists televisions;

create table televisions (
    id serial primary key,
    model text not null,
    producer text not null,
    production_country text,
    width integer not null,
    height integer not null,
    sold boolean not null default false,
    constraint positive_width_and_height check (width > 0 and height > 0)
);

insert into televisions (id, model, producer, production_country, width, height) values
	(1, '43PFS5505/60', 'Philips', NULL, 1920, 1080),
	(2, '28TN515S-PZ', 'LG', 'Russia', 1366, 768),
	(3, 'MI TV P1 43', 'Xiaomi', 'Russia', 3840, 2160),
	(4, 'OLED65B2RLA', 'LG', 'Russia', 3840, 2160),
	(5, 'UE32T5300AUXCE', 'Samsung', NULL, 1920, 1080);
