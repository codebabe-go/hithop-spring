CREATE TABLE `cb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户名',
  `email` varchar(100) DEFAULT '' COMMENT '用户邮件',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';