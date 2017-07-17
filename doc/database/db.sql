/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-14 14:05:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `password` varchar(50) NOT NULL COMMENT '密码(MD5加密后)',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_disabled` int(1) DEFAULT NULL COMMENT '启用禁用(0：启用，1：禁用)',
  `deleted` int(1) DEFAULT NULL COMMENT '删除状态（0.未删除;1.已删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin1', '张三', '三傻', '123456', null, null, null, '0', '0', '2017-07-14 13:19:23');
INSERT INTO `sys_user` VALUES ('2', 'admin2', '李四', '四傻', '123456', null, null, null, '0', '0', '2017-07-14 13:19:25');
