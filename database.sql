CREATE SCHEMA `klim` ;

CREATE TABLE `klim`.`comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` varchar(45) NOT NULL,
  `time` datetime NOT NULL,
  `content` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `klim`.`friends` (
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  UNIQUE KEY `user0_id` (`user_id`,`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `klim`.`posts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creator` int(11) NOT NULL,
  `creation_time` datetime NOT NULL,
  `content` text NOT NULL,
  `posted_to` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `klim`.`roles` (
  `id` int(11) NOT NULL,
  `role` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `klim`.`user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  UNIQUE KEY `user_id` (`user_id`,`role_id`),
  KEY `user_id_2` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `klim`.`users` (
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(50) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `avatar` varchar(100) DEFAULT NULL,
  `background` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `klim`.`users` (`username`, `email`, `password`, `avatar`, `background`, `description`, `id`) VALUES ('admin', 'admin@pleasechangeme.com', 'MD5:21232f297a57a5a743894a0e4a801fc3', 'https://placehold.it/150x150', 'https://placehold.it/350x150', 'admin::loves food', 1);
INSERT INTO `klim`.`roles` (`id`, `role`) VALUES ('1', 'admin');
INSERT INTO `klim`.`roles` (`id`, `role`) VALUES ('2', 'user');
INSERT INTO `klim`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '1');
