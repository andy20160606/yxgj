/*
Navicat MySQL Data Transfer

Source Server         : YG
Source Server Version : 50533
Source Host           : localhost:3306
Source Database       : yxgj

Target Server Type    : MYSQL
Target Server Version : 50533
File Encoding         : 65001

Date: 2019-07-17 08:58:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_cp`
-- ----------------------------
DROP TABLE IF EXISTS `t_cp`;
CREATE TABLE `t_cp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cplb` varchar(255) DEFAULT NULL,
  `cpmc` varchar(255) DEFAULT NULL,
  `cpxj` double DEFAULT NULL,
  `cpxx` varchar(255) DEFAULT NULL,
  `cpyj` double DEFAULT NULL,
  `cpzt` int(11) DEFAULT NULL,
  `sjsj` datetime DEFAULT NULL,
  `cpxqt1` varchar(255) DEFAULT NULL,
  `cpxqt2` varchar(255) DEFAULT NULL,
  `cpzst` varchar(255) DEFAULT NULL,
  `cpbh` varchar(255) DEFAULT NULL,
  `cptct` varchar(255) DEFAULT NULL,
  `cpxqt3` varchar(255) DEFAULT NULL,
  `gms` varchar(255) DEFAULT NULL,
  `rd` varchar(255) DEFAULT NULL,
  `zsdz` varchar(255) DEFAULT NULL,
  `czdz` varchar(255) DEFAULT NULL,
  `wxlbht` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cp
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cpdd`
-- ----------------------------
DROP TABLE IF EXISTS `t_cpdd`;
CREATE TABLE `t_cpdd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ddlx` varchar(255) DEFAULT NULL,
  `ddzt` int(11) DEFAULT NULL,
  `khdz` varchar(255) DEFAULT NULL,
  `khsj` varchar(255) DEFAULT NULL,
  `khxm` varchar(255) DEFAULT NULL,
  `qtxx` varchar(255) DEFAULT NULL,
  `xdsj` datetime DEFAULT NULL,
  `cp_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `khsp` varchar(255) DEFAULT NULL,
  `yhhd_id` bigint(20) DEFAULT NULL,
  `ydzt` int(11) DEFAULT NULL,
  `tclx` varchar(255) DEFAULT NULL,
  `rydzt` int(11) DEFAULT NULL,
  `active` int(11) DEFAULT NULL,
  `sjsjy` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqt8ci4etjgksipwhwa4171u3i` (`cp_id`),
  KEY `FK4fv593ysertynqd38g6h5wnac` (`user_id`),
  KEY `FKh2n7595mx4nygoatdsdjq041e` (`yhhd_id`),
  CONSTRAINT `FK4fv593ysertynqd38g6h5wnac` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FKh2n7595mx4nygoatdsdjq041e` FOREIGN KEY (`yhhd_id`) REFERENCES `t_yhhd` (`id`),
  CONSTRAINT `FKqt8ci4etjgksipwhwa4171u3i` FOREIGN KEY (`cp_id`) REFERENCES `t_cp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cpdd
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cp_hys`
-- ----------------------------
DROP TABLE IF EXISTS `t_cp_hys`;
CREATE TABLE `t_cp_hys` (
  `cps_id` bigint(20) NOT NULL,
  `hys_id` bigint(20) NOT NULL,
  KEY `FKk4cq1cvv7t286c9yflaq5wryo` (`hys_id`),
  KEY `FKet7gfdl6vhud87ca5q9utgole` (`cps_id`),
  CONSTRAINT `FKet7gfdl6vhud87ca5q9utgole` FOREIGN KEY (`cps_id`) REFERENCES `t_cp` (`id`),
  CONSTRAINT `FKk4cq1cvv7t286c9yflaq5wryo` FOREIGN KEY (`hys_id`) REFERENCES `t_hy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cp_hys
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cp_zts`
-- ----------------------------
DROP TABLE IF EXISTS `t_cp_zts`;
CREATE TABLE `t_cp_zts` (
  `cps_id` bigint(20) NOT NULL,
  `zts_id` bigint(20) NOT NULL,
  KEY `FK1fm0drftge1o1tlhdhfun8p27` (`zts_id`),
  KEY `FK3exoh0cjsym9qpbl508wh0pid` (`cps_id`),
  CONSTRAINT `FK1fm0drftge1o1tlhdhfun8p27` FOREIGN KEY (`zts_id`) REFERENCES `t_zt` (`id`),
  CONSTRAINT `FK3exoh0cjsym9qpbl508wh0pid` FOREIGN KEY (`cps_id`) REFERENCES `t_cp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cp_zts
-- ----------------------------

-- ----------------------------
-- Table structure for `t_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  `attr3` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_file
-- ----------------------------

-- ----------------------------
-- Table structure for `t_hy`
-- ----------------------------
DROP TABLE IF EXISTS `t_hy`;
CREATE TABLE `t_hy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hybq` varchar(255) DEFAULT NULL,
  `hymc` varchar(255) DEFAULT NULL,
  `hyxx` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `hyfl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_hy
-- ----------------------------
INSERT INTO `t_hy` VALUES ('2', '', '全部', '', '0', '1');
INSERT INTO `t_hy` VALUES ('3', '', '服装', '', '0', '1');
INSERT INTO `t_hy` VALUES ('4', '', '超市', '', '0', '1');
INSERT INTO `t_hy` VALUES ('5', '', '教育', '', '0', '1');
INSERT INTO `t_hy` VALUES ('6', '', '健身', '', '0', '1');
INSERT INTO `t_hy` VALUES ('7', '', '餐饮', '', '0', '1');
INSERT INTO `t_hy` VALUES ('9', '', '店铺1', '', '0', '1');
INSERT INTO `t_hy` VALUES ('10', '全部', '全部', '全部', '0', '1');
INSERT INTO `t_hy` VALUES ('11', '', '房产', '', '0', '1');
INSERT INTO `t_hy` VALUES ('12', '', '美业', '', '0', '1');
INSERT INTO `t_hy` VALUES ('13', '', '乐器', '', '0', '1');
INSERT INTO `t_hy` VALUES ('14', '', '宠物', '', '0', '1');
INSERT INTO `t_hy` VALUES ('15', '', '电子', '', '0', '1');
INSERT INTO `t_hy` VALUES ('16', '', '五金', '', '0', '1');
INSERT INTO `t_hy` VALUES ('17', '', '礼品', '', '0', '1');
INSERT INTO `t_hy` VALUES ('18', '', '玩具', '', '0', '1');
INSERT INTO `t_hy` VALUES ('19', '', '酒店', '', '0', '1');
INSERT INTO `t_hy` VALUES ('20', '', '保健品', '', '0', '1');
INSERT INTO `t_hy` VALUES ('21', '', '产品销售', '', '0', '1');
INSERT INTO `t_hy` VALUES ('22', '', '代购', '', '0', '1');
INSERT INTO `t_hy` VALUES ('23', '', '其他', '', '0', '1');
INSERT INTO `t_hy` VALUES ('24', '', '新款上市', '', '0', '1');
INSERT INTO `t_hy` VALUES ('25', '', '购买最多', '', '0', '1');
INSERT INTO `t_hy` VALUES ('26', '', '热度最高', '', '0', '1');
INSERT INTO `t_hy` VALUES ('27', '', '优惠促销', '', '0', '1');
INSERT INTO `t_hy` VALUES ('28', '', '门店宣传', '', '1', '2');
INSERT INTO `t_hy` VALUES ('29', '', '引流获客', '', '1', '2');
INSERT INTO `t_hy` VALUES ('30', '', '提高销量', '', '1', '2');
INSERT INTO `t_hy` VALUES ('31', '', '快速推广', '', '1', '2');
INSERT INTO `t_hy` VALUES ('32', '', '关注吸粉', '', '1', '2');
INSERT INTO `t_hy` VALUES ('33', '', '免费服务', '', '0', '2');

-- ----------------------------
-- Table structure for `t_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_info`;
CREATE TABLE `t_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `k` varchar(255) DEFAULT NULL,
  `val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_info
-- ----------------------------
INSERT INTO `t_info` VALUES ('27', 'string', 'string');
INSERT INTO `t_info` VALUES ('37', 'phonebanner0', '69ec9e75-e940-4290-9dd5-ea886505d145.png');
INSERT INTO `t_info` VALUES ('39', 'phonebanner1', '3115f80c-45fe-44e6-be4a-c5156bc06345.png');
INSERT INTO `t_info` VALUES ('40', 'phonebanner2', '5e5a6063-64ad-4cfb-942d-27504f36e851.png');
INSERT INTO `t_info` VALUES ('41', 'phonebanner3', 'b7ad7d15-da4c-457a-b043-ce762bfa3627.png');

-- ----------------------------
-- Table structure for `t_kh`
-- ----------------------------
DROP TABLE IF EXISTS `t_kh`;
CREATE TABLE `t_kh` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cslx` varchar(255) DEFAULT NULL,
  `khbm` varchar(255) DEFAULT NULL,
  `khmc` varchar(255) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `loginpass` varchar(255) DEFAULT NULL,
  `sjhm` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `vip` int(11) DEFAULT NULL,
  `wybs` varchar(255) DEFAULT NULL,
  `lsrzm` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_kh
-- ----------------------------
INSERT INTO `t_kh` VALUES ('1', null, null, null, 'A', 'A', null, null, null, '111', 'fb70c31c-1a33-4dc0-b298-52dec2fc7216');

-- ----------------------------
-- Table structure for `t_khcp`
-- ----------------------------
DROP TABLE IF EXISTS `t_khcp`;
CREATE TABLE `t_khcp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sm` varchar(255) DEFAULT NULL,
  `starttime` datetime DEFAULT NULL,
  `stoptime` datetime DEFAULT NULL,
  `cp_id` bigint(20) DEFAULT NULL,
  `kh_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl6h7oet6wckwq5gxh2f4rhdiq` (`cp_id`),
  KEY `FKhs04y175un0t4unug5ye4kt8u` (`kh_id`),
  CONSTRAINT `FKhs04y175un0t4unug5ye4kt8u` FOREIGN KEY (`kh_id`) REFERENCES `t_kh` (`id`),
  CONSTRAINT `FKl6h7oet6wckwq5gxh2f4rhdiq` FOREIGN KEY (`cp_id`) REFERENCES `t_cp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_khcp
-- ----------------------------

-- ----------------------------
-- Table structure for `t_org`
-- ----------------------------
DROP TABLE IF EXISTS `t_org`;
CREATE TABLE `t_org` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `seq` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7kp4bd28iid77gvobyfdustpl` (`pid`),
  CONSTRAINT `FK7kp4bd28iid77gvobyfdustpl` FOREIGN KEY (`pid`) REFERENCES `t_org` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_org
-- ----------------------------
INSERT INTO `t_org` VALUES ('1', '行政楼', '111000', '2019-04-10 09:16:10', 'desc', null, '办公室', null, null, null);
INSERT INTO `t_org` VALUES ('2', '行政楼111', '111000', '2019-04-10 09:16:10', 'desc', null, '办公室1', null, null, '1');
INSERT INTO `t_org` VALUES ('3', '行政楼222', '111000', '2019-04-10 09:16:11', 'desc', null, '办公室1', null, null, '2');
INSERT INTO `t_org` VALUES ('4', null, null, '2019-04-17 14:52:50', '微信渠道', null, '微信渠道', null, null, null);

-- ----------------------------
-- Table structure for `t_res`
-- ----------------------------
DROP TABLE IF EXISTS `t_res`;
CREATE TABLE `t_res` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createdate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `resourcetype` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK44mad20e1ud26wjqhy6fn2ls7` (`pid`),
  CONSTRAINT `FK44mad20e1ud26wjqhy6fn2ls7` FOREIGN KEY (`pid`) REFERENCES `t_res` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_res
-- ----------------------------
INSERT INTO `t_res` VALUES ('1', '2019-04-10 09:15:59', '系统管理', null, 'res-name-1', '0', null, null, 'system', null);
INSERT INTO `t_res` VALUES ('2', '2019-04-10 09:15:59', '系统管理/用户管理', null, '用户管理', '0', null, null, 'system/user', '1');
INSERT INTO `t_res` VALUES ('3', '2019-04-10 09:15:59', '系统管理/用户管理/添加', null, '添加', '1', null, null, 'system/user/add', '2');
INSERT INTO `t_res` VALUES ('4', '2019-04-10 09:15:59', '系统管理/用户管理/修改', null, '修改', '1', null, null, 'system/user/edit', '2');
INSERT INTO `t_res` VALUES ('5', '2019-04-10 09:15:59', '系统管理/用户管理/删除', null, '删除', '1', null, null, 'system/user/del', '2');
INSERT INTO `t_res` VALUES ('6', '2019-04-22 11:33:48', 'wx', null, 'wx', '0', null, null, 'yhq/add', null);

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `seq` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'desc', '超级用户', '1', '1');
INSERT INTO `t_role` VALUES ('2', 'wx', 'wx', '1', '1');

-- ----------------------------
-- Table structure for `t_role_ress`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_ress`;
CREATE TABLE `t_role_ress` (
  `roles_id` bigint(20) NOT NULL,
  `ress_id` bigint(20) NOT NULL,
  KEY `FKcgo3urndu6s035ij8nrnwrwrd` (`ress_id`),
  KEY `FK9tnpujhwjo5vdlgf5vajg6hqn` (`roles_id`),
  CONSTRAINT `FK9tnpujhwjo5vdlgf5vajg6hqn` FOREIGN KEY (`roles_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKcgo3urndu6s035ij8nrnwrwrd` FOREIGN KEY (`ress_id`) REFERENCES `t_res` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_ress
-- ----------------------------
INSERT INTO `t_role_ress` VALUES ('1', '1');
INSERT INTO `t_role_ress` VALUES ('2', '6');

-- ----------------------------
-- Table structure for `t_sjrz`
-- ----------------------------
DROP TABLE IF EXISTS `t_sjrz`;
CREATE TABLE `t_sjrz` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` bigint(20) DEFAULT NULL,
  `sjrq` datetime DEFAULT NULL,
  `sjxw` varchar(255) DEFAULT NULL,
  `cp_id` bigint(20) DEFAULT NULL,
  `yhhd_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3icdmmpwtrxd88u7qm3w4xr0r` (`cp_id`),
  KEY `FKr46b08u29o7vuleyr2a2pej3x` (`yhhd_id`),
  CONSTRAINT `FK3icdmmpwtrxd88u7qm3w4xr0r` FOREIGN KEY (`cp_id`) REFERENCES `t_cp` (`id`),
  CONSTRAINT `FKr46b08u29o7vuleyr2a2pej3x` FOREIGN KEY (`yhhd_id`) REFERENCES `t_yhhd` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=327 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sjrz
-- ----------------------------
INSERT INTO `t_sjrz` VALUES ('325', '4', '2019-06-05 00:00:00', 'scll', null, null);
INSERT INTO `t_sjrz` VALUES ('326', '3', '2019-06-05 00:00:00', 'zf', null, null);

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `loginpass` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `usertype` int(11) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `procince` varchar(255) DEFAULT NULL,
  `wxopenid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK50styg35vbtj9mrdd6nk8gwdh` (`wxopenid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '20', '2019-04-10 09:16:43', 'root', 'root', '139000000000', '1', '0', 'root', '1', null, null, null, null, '');
INSERT INTO `t_user` VALUES ('2', null, '2019-04-17 14:44:23', null, null, null, null, '0', null, null, '常州', '中国', '我是小土豆', '江苏', 'odN1v1grrVeUZE2EMcH_34Wj2muY');
INSERT INTO `t_user` VALUES ('3', null, '2019-04-24 14:53:02', null, null, null, null, '0', null, null, '', '', 'andy', '', 'odN1v1rJ91EF89wuyt6n2iPbRAL0');
INSERT INTO `t_user` VALUES ('4', null, '2019-04-24 15:02:04', null, null, null, null, '0', null, null, '徐州', '中国', '与君玉颜', '江苏', 'odN1v1uG0tHCCxVPiAwgJTxDuDt4');
INSERT INTO `t_user` VALUES ('5', null, '2019-04-24 15:02:05', null, null, null, null, '0', null, null, '', '百慕大', '异界的天乐君', '', 'odN1v1ibu_Mocf9eYUhFEQlxHB2A');
INSERT INTO `t_user` VALUES ('6', null, '2019-04-25 16:46:00', null, null, null, null, '0', null, null, '', '安提瓜岛和巴布达', 'L', '', 'odN1v1l699LPJPSZOuEKQXWo7zps');
INSERT INTO `t_user` VALUES ('7', null, '2019-04-29 17:04:35', null, null, null, null, '0', null, null, '常州', '中国', 'Homer 语辰', '江苏', 'odN1v1vZP4fxTV7xN2YuHChYA1Mo');
INSERT INTO `t_user` VALUES ('8', null, '2019-05-08 14:07:34', null, null, null, null, '0', null, null, '', '', 'lazy', '', 'odN1v1knmZ6JZStczWX3P50qNTtU');
INSERT INTO `t_user` VALUES ('9', null, '2019-05-08 17:07:22', null, null, null, null, '0', null, null, '成都', '中国', '蓝色的胖子', '四川', 'odN1v1nDhsr5WOlMAEYuOl4EXMY8');
INSERT INTO `t_user` VALUES ('10', null, '2019-05-14 10:07:30', null, null, null, null, '0', null, null, '常州', '中国', '吼吼吼。', '江苏', 'odN1v1jZLOsSUYy8GR6j5bApd_gM');
INSERT INTO `t_user` VALUES ('11', null, '2019-05-14 17:24:33', null, null, null, null, '0', null, null, '', '', '释梦的漂流人', '', 'odN1v1qnjcqybll1N7R6eABvFDEs');
INSERT INTO `t_user` VALUES ('12', null, '2019-05-14 17:27:39', null, null, null, null, '0', null, null, '常州', '中国', 'double2', '江苏', 'odN1v1nF8cX8CPZ7aPHLA-Gs8vHM');
INSERT INTO `t_user` VALUES ('13', null, '2019-05-30 16:25:27', null, null, null, null, '0', null, null, '', '阿鲁巴', '抽烟喝酒烫头', '', 'odN1v1v0RPCHqDJoFkirJ1wgqvC8');
INSERT INTO `t_user` VALUES ('14', null, '2019-05-30 16:34:49', null, null, null, null, '0', null, null, '常州', '中国', '半仙不是仙', '江苏', 'odN1v1gNm6R_guPgtnHT_kFkOVc0');

-- ----------------------------
-- Table structure for `t_user_orgs`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_orgs`;
CREATE TABLE `t_user_orgs` (
  `users_id` bigint(20) NOT NULL,
  `orgs_id` bigint(20) NOT NULL,
  KEY `FKcma3uatexedv5qf33ac2877ah` (`orgs_id`),
  KEY `FK5vs64vn7jsoi7segphuch6ucd` (`users_id`),
  CONSTRAINT `FK5vs64vn7jsoi7segphuch6ucd` FOREIGN KEY (`users_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FKcma3uatexedv5qf33ac2877ah` FOREIGN KEY (`orgs_id`) REFERENCES `t_org` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_orgs
-- ----------------------------
INSERT INTO `t_user_orgs` VALUES ('1', '1');
INSERT INTO `t_user_orgs` VALUES ('3', '4');
INSERT INTO `t_user_orgs` VALUES ('4', '4');
INSERT INTO `t_user_orgs` VALUES ('5', '4');
INSERT INTO `t_user_orgs` VALUES ('6', '4');
INSERT INTO `t_user_orgs` VALUES ('7', '4');
INSERT INTO `t_user_orgs` VALUES ('8', '4');
INSERT INTO `t_user_orgs` VALUES ('9', '4');
INSERT INTO `t_user_orgs` VALUES ('10', '4');
INSERT INTO `t_user_orgs` VALUES ('11', '4');
INSERT INTO `t_user_orgs` VALUES ('12', '4');
INSERT INTO `t_user_orgs` VALUES ('13', '4');
INSERT INTO `t_user_orgs` VALUES ('14', '4');

-- ----------------------------
-- Table structure for `t_user_roles`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_roles`;
CREATE TABLE `t_user_roles` (
  `users_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FKj47yp3hhtsoajht9793tbdrp4` (`roles_id`),
  KEY `FKp67oqi40xgs7j7ad5xqaxx857` (`users_id`),
  CONSTRAINT `FKj47yp3hhtsoajht9793tbdrp4` FOREIGN KEY (`roles_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKp67oqi40xgs7j7ad5xqaxx857` FOREIGN KEY (`users_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_roles
-- ----------------------------
INSERT INTO `t_user_roles` VALUES ('1', '1');
INSERT INTO `t_user_roles` VALUES ('2', '2');

-- ----------------------------
-- Table structure for `t_yhhd`
-- ----------------------------
DROP TABLE IF EXISTS `t_yhhd`;
CREATE TABLE `t_yhhd` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hdsm` varchar(255) DEFAULT NULL,
  `je` double DEFAULT NULL,
  `jsrq` datetime DEFAULT NULL,
  `ksrq` datetime DEFAULT NULL,
  `hdmc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yhhd
-- ----------------------------
INSERT INTO `t_yhhd` VALUES ('1', '转发', '0', '2019-04-11 09:01:28', '2019-04-11 09:01:28', '转发');
INSERT INTO `t_yhhd` VALUES ('2', '代理', '0', '2019-04-25 07:32:01', '2019-04-25 07:32:01', '代理');
INSERT INTO `t_yhhd` VALUES ('3', '会员', '200', '2019-04-25 07:32:01', '2019-04-25 07:32:01', '会员');
INSERT INTO `t_yhhd` VALUES ('4', '定制开发 ', '200', '2019-04-25 07:32:01', '2019-04-25 07:32:01', '定制开发');
INSERT INTO `t_yhhd` VALUES ('5', '设计师', '200', '2019-04-25 07:32:01', '2019-04-25 07:32:01', '设计师');

-- ----------------------------
-- Table structure for `t_yhq`
-- ----------------------------
DROP TABLE IF EXISTS `t_yhq`;
CREATE TABLE `t_yhq` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `je` double DEFAULT NULL,
  `jsrq` datetime DEFAULT NULL,
  `ksrq` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `yhhd_id` bigint(20) DEFAULT NULL,
  `yhm` varchar(255) DEFAULT NULL,
  `yxzt` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk77k0tdagqomrjkbnefb0l72y` (`user_id`),
  KEY `FK8daq0c7byb40pxd7h9dhjrhs4` (`yhhd_id`),
  CONSTRAINT `FK8daq0c7byb40pxd7h9dhjrhs4` FOREIGN KEY (`yhhd_id`) REFERENCES `t_yhhd` (`id`),
  CONSTRAINT `FKk77k0tdagqomrjkbnefb0l72y` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yhq
-- ----------------------------

-- ----------------------------
-- Table structure for `t_zfrz`
-- ----------------------------
DROP TABLE IF EXISTS `t_zfrz`;
CREATE TABLE `t_zfrz` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `zfsj` datetime DEFAULT NULL,
  `cp_id` bigint(20) DEFAULT NULL,
  `yhhd_id` bigint(20) DEFAULT NULL,
  `zfr_id` bigint(20) DEFAULT NULL,
  `llcs` bigint(20) DEFAULT NULL,
  `wybs` varchar(255) DEFAULT NULL,
  `cpdd_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkghv5ikpkkwj4aqxuska13loe` (`cp_id`),
  KEY `FKei76caraprr8b3u75ibcokp1t` (`yhhd_id`),
  KEY `FKrk9ulln1bumi50j47eshg5t4` (`zfr_id`),
  KEY `FK18m3ishpjmok1cv228wd3h1sn` (`cpdd_id`),
  CONSTRAINT `FK18m3ishpjmok1cv228wd3h1sn` FOREIGN KEY (`cpdd_id`) REFERENCES `t_cpdd` (`id`),
  CONSTRAINT `FKei76caraprr8b3u75ibcokp1t` FOREIGN KEY (`yhhd_id`) REFERENCES `t_yhhd` (`id`),
  CONSTRAINT `FKkghv5ikpkkwj4aqxuska13loe` FOREIGN KEY (`cp_id`) REFERENCES `t_cp` (`id`),
  CONSTRAINT `FKrk9ulln1bumi50j47eshg5t4` FOREIGN KEY (`zfr_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_zfrz
-- ----------------------------
INSERT INTO `t_zfrz` VALUES ('4', 'https://www.baidu.com', '2019-06-05 17:03:40', null, null, '2', '1', 'd', null);
INSERT INTO `t_zfrz` VALUES ('5', 'www.baidu.com', '2019-06-05 17:22:03', null, null, '4', '1', 'abc', null);

-- ----------------------------
-- Table structure for `t_zt`
-- ----------------------------
DROP TABLE IF EXISTS `t_zt`;
CREATE TABLE `t_zt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ztmc` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_zt
-- ----------------------------
INSERT INTO `t_zt` VALUES ('1', '状态名称', null);
INSERT INTO `t_zt` VALUES ('2', 'string', null);
INSERT INTO `t_zt` VALUES ('3', '状态1', '1');
