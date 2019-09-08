DROP TABLE IF EXISTS users;

CREATE TABLE users(
                            id    serial primary key not null,
                            username  varchar(255) unique not null,
                            password  varchar(255),
                            email varchar(255),
                            image_url varchar(255),
                            imageUrl varchar(255)
);
