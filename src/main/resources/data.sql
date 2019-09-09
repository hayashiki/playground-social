DROP TABLE IF EXISTS users;

CREATE TABLE users(
                            id    serial primary key not null,
                            username  varchar(255) unique not null,
                            password  varchar(255),
                            email varchar(255),
                            role varchar(255),
                            image_url varchar(255),
                            imageUrl varchar(255)
);


create table UserConnection (
  userId varchar(255) not null,
  providerId varchar(255) not null,
  providerUserId varchar(255),
  ranking int not null,
  displayName varchar(255),
  profileUrl varchar(512),
  imageUrl varchar(512),
  accessToken varchar(1024) not null,
  secret varchar(255),
  refreshToken varchar(255),
  expireTime bigint,
  primary key (userId, providerId, providerUserId));