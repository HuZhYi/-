/*
Navicat MySQL Data Transfer

Source Server         : hzy
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : game

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-04-19 19:51:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for player
-- ----------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `playerId` int(8) NOT NULL AUTO_INCREMENT COMMENT '玩家id',
  `playerName` varchar(8) DEFAULT NULL COMMENT '名称',
  `playerPwd` varchar(8) DEFAULT NULL COMMENT '密码',
  `victory` int(8) DEFAULT '0' COMMENT '胜',
  `defeated` int(8) DEFAULT '0' COMMENT '败',
  `roomId` int(8) DEFAULT NULL COMMENT '房间id',
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of player
-- ----------------------------
INSERT INTO `player` VALUES ('1', '玩家1', '123', '0', '0', null);
INSERT INTO `player` VALUES ('2', '玩家2', '123', '0', '0', null);
INSERT INTO `player` VALUES ('3', '玩家3', '123', '0', '0', null);

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `roomId` int(8) NOT NULL AUTO_INCREMENT COMMENT '房间id',
  `roomName` varchar(16) DEFAULT '' COMMENT '房间名',
  `roomContent` varchar(16) DEFAULT '' COMMENT '房间描述',
  `isFull` int(2) DEFAULT NULL,
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------

-- ----------------------------
-- Table structure for unit
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `HP` int(11) DEFAULT NULL,
  `atteck` int(11) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of unit
-- ----------------------------
INSERT INTO `unit` VALUES ('1', '建筑', '作战实验室', '800', '0', '2000', '2', '解锁高级单位');
INSERT INTO `unit` VALUES ('2', '建筑', '兵营', '800', '0', '1000', '1', '生产部队');
INSERT INTO `unit` VALUES ('3', '建筑', '发电厂', '800', '0', '1500', '1', '每回合提供资源');
INSERT INTO `unit` VALUES ('4', '防御塔', '哨戒炮', '500', '100', '600', '2', '廉价的防御塔');
INSERT INTO `unit` VALUES ('5', '防御塔', '磁暴电塔', '600', '400', '800', '2', '对装甲部队会造成150%暴击');
INSERT INTO `unit` VALUES ('6', '防御塔', '核弹发射井', '1500', '1000', '2200', '3', '直接攻击玩家后自爆');
INSERT INTO `unit` VALUES ('7', '士兵', '天启坦克', '1000', '300', '2000', '3', '对生命值低于30%单位造成斩杀');
INSERT INTO `unit` VALUES ('8', '士兵', '磁能坦克', '600', '300', '1000', '2', '对目标及其两边的单位造成75%伤害');
INSERT INTO `unit` VALUES ('9', '士兵', 'V3火箭车', '300', '300', '1000', '2', '攻击当前行所有单位');
