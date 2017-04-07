CREATE SCHEMA klim;

CREATE TABLE klim.comments (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  post_id VARCHAR(45) NOT NULL,
  time TIMESTAMP NOT NULL,
  content VARCHAR(45) NOT NULL
);

CREATE TABLE klim.friends (
  user_id INTEGER NOT NULL,
  friend_id INTEGER NOT NULL
);

CREATE TABLE klim.posts (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  creator INTEGER NOT NULL,
  creation_time TIMESTAMP NOT NULL,
  content text NOT NULL,
  posted_to INTEGER NOT NULL
);

CREATE TABLE klim.roles (
  id INTEGER NOT NULL,
  role VARCHAR(100) NOT NULL
);

CREATE TABLE klim.user_roles (
  user_id INTEGER NOT NULL,
  role_id INTEGER NOT NULL
);

CREATE TABLE klim.users (
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  password VARCHAR(50) NOT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  avatar VARCHAR(100) DEFAULT NULL,
  background VARCHAR(100) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  role VARCHAR(255) DEFAULT NULL,
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)
);

INSERT INTO klim.users (username, email, password, avatar, background, description, role) VALUES ('admin', 'admin@pleasechangeme.com', 'MD5:21232f297a57a5a743894a0e4a801fc3', 'https://placehold.it/150x150', 'https://placehold.it/350x150', 'admin::loves food', 'admin');
INSERT INTO klim.user_roles (user_id, role_id) VALUES (1, 1);
