/*
Navicat MySQL Data Transfer

Source Server         : syp-test
Source Server Version : 50616
Source Host           : rm-wz936477ppnwe9zamfo.mysql.rds.aliyuncs.com:3306
Source Database       : shiro-web

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2020-08-12 09:16:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_parent_id` (`parent_id`),
  KEY `idx_sys_organization_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '总公司', '0', '0/', '1');
INSERT INTO `sys_organization` VALUES ('2', '分公司1', '1', '0/1/', '1');
INSERT INTO `sys_organization` VALUES ('3', '分公司2', '1', '0/1/', '1');
INSERT INTO `sys_organization` VALUES ('4', '分公司11', '2', '0/1/2/', '1');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(100) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  `sort` int(11) DEFAULT '0',
  `icon_cls` varchar(255) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '系统管理', 'menu', '0', '0/', '', '1', '3', 'database');
INSERT INTO `sys_permission` VALUES ('11', '组织机构管理', 'menu', '1', '0/1/', '/organization', '2', '1', null);
INSERT INTO `sys_permission` VALUES ('12', '组织机构新增', 'button', '11', '0/1/11/', 'organization:create', '1', '1', null);
INSERT INTO `sys_permission` VALUES ('13', '组织机构修改', 'button', '11', '0/1/11/', 'organization:update', '1', '2', null);
INSERT INTO `sys_permission` VALUES ('14', '组织机构删除', 'button', '11', '0/1/11/', 'organization:delete', '1', '3', null);
INSERT INTO `sys_permission` VALUES ('15', '组织机构查看', 'button', '11', '0/1/11/', 'organization:view', '1', '4', null);
INSERT INTO `sys_permission` VALUES ('21', '用户管理', 'menu', '1', '0/1/', '/user', '1', '2', '');
INSERT INTO `sys_permission` VALUES ('22', '用户新增', 'button', '21', '0/1/21/', '/user/add', '1', '1', null);
INSERT INTO `sys_permission` VALUES ('23', '用户修改', 'button', '21', '0/1/21/', '/user/update', '1', '2', null);
INSERT INTO `sys_permission` VALUES ('24', '用户冻结', 'button', '21', '0/1/21/', '/user/locked', '1', '3', null);
INSERT INTO `sys_permission` VALUES ('25', '用户查看', 'button', '21', '0/1/21/', '/user/list', '1', '4', null);
INSERT INTO `sys_permission` VALUES ('26', '更改密码', 'button', '21', '0/1/21/', '/user/changePassword', '0', '5', null);
INSERT INTO `sys_permission` VALUES ('27', '更新用户角色', 'button', '21', null, '/user/updateRole', '1', '6', null);
INSERT INTO `sys_permission` VALUES ('31', '权限管理', 'menu', '1', '0/1/', '/permission', '1', '4', null);
INSERT INTO `sys_permission` VALUES ('32', '权限新增', 'button', '31', '0/1/31/', '/permission/add', '1', '1', null);
INSERT INTO `sys_permission` VALUES ('33', '权限修改', 'button', '31', '0/1/31/', '/permission/update', '1', '2', null);
INSERT INTO `sys_permission` VALUES ('34', '权限冻结', 'button', '31', '0/1/31/', '/permission/locked', '1', '3', null);
INSERT INTO `sys_permission` VALUES ('35', '权限查看', 'button', '31', '0/1/31/', '/permission/listAll', '1', '4', null);
INSERT INTO `sys_permission` VALUES ('36', '我的权限', 'button', '31', '0/1/31/', '/permission/listMyMenu', '1', '5', null);
INSERT INTO `sys_permission` VALUES ('37', '权限详情', 'button', '31', '0/1/31/', '/permission/detail', '1', '6', null);
INSERT INTO `sys_permission` VALUES ('38', '角色权限集合', 'button', '41', null, '/role/listPermissionByRoleId', '1', '7', null);
INSERT INTO `sys_permission` VALUES ('39', '用户权限集合', 'button', '21', null, '/user/listPermissionByUserId', '1', '8', null);
INSERT INTO `sys_permission` VALUES ('41', '角色管理', 'menu', '1', '0/1/', '/role', '1', '3', '');
INSERT INTO `sys_permission` VALUES ('42', '角色新增', 'button', '41', '0/1/41/', '/role/addRolePermission', '1', '1', null);
INSERT INTO `sys_permission` VALUES ('43', '角色修改', 'button', '41', '0/1/41/', '/role/updateRolePermission', '1', '2', null);
INSERT INTO `sys_permission` VALUES ('44', '角色冻结', 'button', '41', '0/1/41/', '/role/locked', '1', '3', null);
INSERT INTO `sys_permission` VALUES ('45', '角色查看', 'button', '41', '0/1/41/', '/role/list', '1', '4', null);
INSERT INTO `sys_permission` VALUES ('46', '用户拥有角色', 'button', '41', null, '/role/listByUserId', '1', '5', null);
INSERT INTO `sys_permission` VALUES ('55', '系统运行日志', 'menu', '0', null, '', '1', '1', 'database');
INSERT INTO `sys_permission` VALUES ('56', '请求接口日志', 'menu', '55', null, '/log/httpRequestLog', '1', '1', '');
INSERT INTO `sys_permission` VALUES ('57', '服务故障日志', 'menu', '55', null, '/log/serverExceptionLog', '1', '2', '');
INSERT INTO `sys_permission` VALUES ('58', '业务异常日志', 'menu', '55', null, '/log/businessAbnormalLog', '1', '3', '');
INSERT INTO `sys_permission` VALUES ('59', '分配角色', 'button', '21', null, '/user/toUpdateRole', '0', '4', '');
INSERT INTO `sys_permission` VALUES ('60', '日志统计分析', 'menu', '0', null, '', '1', '2', 'database');
INSERT INTO `sys_permission` VALUES ('61', '服务故障频率', 'menu', '60', null, '/logStatistics/serverExceptionStatistics', '1', '1', '');
INSERT INTO `sys_permission` VALUES ('62', '缓慢接口统计', 'menu', '60', null, '/log/httpRequestSlowLog', '1', '2', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '超级管理员', '1');
INSERT INTO `sys_role` VALUES ('8', '普通用户', '查看日志', '1');
INSERT INTO `sys_role` VALUES ('9', 'test', '测试角色', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1413 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('468', '4', '1');
INSERT INTO `sys_role_permission` VALUES ('469', '4', '31');
INSERT INTO `sys_role_permission` VALUES ('470', '4', '32');
INSERT INTO `sys_role_permission` VALUES ('471', '4', '33');
INSERT INTO `sys_role_permission` VALUES ('472', '4', '34');
INSERT INTO `sys_role_permission` VALUES ('473', '4', '35');
INSERT INTO `sys_role_permission` VALUES ('474', '4', '36');
INSERT INTO `sys_role_permission` VALUES ('475', '4', '37');
INSERT INTO `sys_role_permission` VALUES ('476', '4', '38');
INSERT INTO `sys_role_permission` VALUES ('477', '4', '39');
INSERT INTO `sys_role_permission` VALUES ('498', '6', '1');
INSERT INTO `sys_role_permission` VALUES ('499', '6', '51');
INSERT INTO `sys_role_permission` VALUES ('500', '6', '52');
INSERT INTO `sys_role_permission` VALUES ('501', '6', '54');
INSERT INTO `sys_role_permission` VALUES ('502', '6', '53');
INSERT INTO `sys_role_permission` VALUES ('503', '7', '1');
INSERT INTO `sys_role_permission` VALUES ('504', '7', '21');
INSERT INTO `sys_role_permission` VALUES ('505', '7', '22');
INSERT INTO `sys_role_permission` VALUES ('506', '7', '23');
INSERT INTO `sys_role_permission` VALUES ('507', '7', '24');
INSERT INTO `sys_role_permission` VALUES ('508', '7', '25');
INSERT INTO `sys_role_permission` VALUES ('509', '7', '26');
INSERT INTO `sys_role_permission` VALUES ('510', '7', '27');
INSERT INTO `sys_role_permission` VALUES ('511', '7', '41');
INSERT INTO `sys_role_permission` VALUES ('512', '7', '42');
INSERT INTO `sys_role_permission` VALUES ('513', '7', '43');
INSERT INTO `sys_role_permission` VALUES ('514', '7', '44');
INSERT INTO `sys_role_permission` VALUES ('515', '7', '45');
INSERT INTO `sys_role_permission` VALUES ('516', '7', '46');
INSERT INTO `sys_role_permission` VALUES ('517', '7', '31');
INSERT INTO `sys_role_permission` VALUES ('518', '7', '32');
INSERT INTO `sys_role_permission` VALUES ('519', '7', '33');
INSERT INTO `sys_role_permission` VALUES ('520', '7', '34');
INSERT INTO `sys_role_permission` VALUES ('521', '7', '35');
INSERT INTO `sys_role_permission` VALUES ('522', '7', '36');
INSERT INTO `sys_role_permission` VALUES ('523', '7', '37');
INSERT INTO `sys_role_permission` VALUES ('524', '7', '38');
INSERT INTO `sys_role_permission` VALUES ('525', '7', '39');
INSERT INTO `sys_role_permission` VALUES ('533', '2', '1');
INSERT INTO `sys_role_permission` VALUES ('534', '2', '41');
INSERT INTO `sys_role_permission` VALUES ('535', '2', '42');
INSERT INTO `sys_role_permission` VALUES ('536', '2', '43');
INSERT INTO `sys_role_permission` VALUES ('537', '2', '44');
INSERT INTO `sys_role_permission` VALUES ('538', '2', '45');
INSERT INTO `sys_role_permission` VALUES ('539', '2', '46');
INSERT INTO `sys_role_permission` VALUES ('548', '5', '1');
INSERT INTO `sys_role_permission` VALUES ('549', '5', '11');
INSERT INTO `sys_role_permission` VALUES ('550', '5', '12');
INSERT INTO `sys_role_permission` VALUES ('551', '5', '13');
INSERT INTO `sys_role_permission` VALUES ('552', '5', '14');
INSERT INTO `sys_role_permission` VALUES ('553', '5', '15');
INSERT INTO `sys_role_permission` VALUES ('560', '3', '1');
INSERT INTO `sys_role_permission` VALUES ('561', '3', '21');
INSERT INTO `sys_role_permission` VALUES ('562', '3', '25');
INSERT INTO `sys_role_permission` VALUES ('704', '8', '55');
INSERT INTO `sys_role_permission` VALUES ('705', '8', '56');
INSERT INTO `sys_role_permission` VALUES ('706', '8', '57');
INSERT INTO `sys_role_permission` VALUES ('707', '8', '58');
INSERT INTO `sys_role_permission` VALUES ('708', '8', '1');
INSERT INTO `sys_role_permission` VALUES ('709', '8', '21');
INSERT INTO `sys_role_permission` VALUES ('710', '8', '24');
INSERT INTO `sys_role_permission` VALUES ('711', '8', '25');
INSERT INTO `sys_role_permission` VALUES ('712', '8', '27');
INSERT INTO `sys_role_permission` VALUES ('1339', '9', '55');
INSERT INTO `sys_role_permission` VALUES ('1340', '9', '56');
INSERT INTO `sys_role_permission` VALUES ('1341', '9', '57');
INSERT INTO `sys_role_permission` VALUES ('1342', '9', '58');
INSERT INTO `sys_role_permission` VALUES ('1343', '9', '1');
INSERT INTO `sys_role_permission` VALUES ('1344', '9', '21');
INSERT INTO `sys_role_permission` VALUES ('1345', '9', '25');
INSERT INTO `sys_role_permission` VALUES ('1346', '9', '41');
INSERT INTO `sys_role_permission` VALUES ('1347', '9', '45');
INSERT INTO `sys_role_permission` VALUES ('1380', '1', '12');
INSERT INTO `sys_role_permission` VALUES ('1381', '1', '55');
INSERT INTO `sys_role_permission` VALUES ('1382', '1', '56');
INSERT INTO `sys_role_permission` VALUES ('1383', '1', '57');
INSERT INTO `sys_role_permission` VALUES ('1384', '1', '58');
INSERT INTO `sys_role_permission` VALUES ('1385', '1', '13');
INSERT INTO `sys_role_permission` VALUES ('1386', '1', '60');
INSERT INTO `sys_role_permission` VALUES ('1387', '1', '61');
INSERT INTO `sys_role_permission` VALUES ('1388', '1', '62');
INSERT INTO `sys_role_permission` VALUES ('1389', '1', '1');
INSERT INTO `sys_role_permission` VALUES ('1390', '1', '21');
INSERT INTO `sys_role_permission` VALUES ('1391', '1', '22');
INSERT INTO `sys_role_permission` VALUES ('1392', '1', '23');
INSERT INTO `sys_role_permission` VALUES ('1393', '1', '24');
INSERT INTO `sys_role_permission` VALUES ('1394', '1', '25');
INSERT INTO `sys_role_permission` VALUES ('1395', '1', '27');
INSERT INTO `sys_role_permission` VALUES ('1396', '1', '39');
INSERT INTO `sys_role_permission` VALUES ('1397', '1', '41');
INSERT INTO `sys_role_permission` VALUES ('1398', '1', '42');
INSERT INTO `sys_role_permission` VALUES ('1399', '1', '43');
INSERT INTO `sys_role_permission` VALUES ('1400', '1', '44');
INSERT INTO `sys_role_permission` VALUES ('1401', '1', '45');
INSERT INTO `sys_role_permission` VALUES ('1402', '1', '46');
INSERT INTO `sys_role_permission` VALUES ('1403', '1', '38');
INSERT INTO `sys_role_permission` VALUES ('1404', '1', '31');
INSERT INTO `sys_role_permission` VALUES ('1405', '1', '32');
INSERT INTO `sys_role_permission` VALUES ('1406', '1', '33');
INSERT INTO `sys_role_permission` VALUES ('1407', '1', '34');
INSERT INTO `sys_role_permission` VALUES ('1408', '1', '35');
INSERT INTO `sys_role_permission` VALUES ('1409', '1', '36');
INSERT INTO `sys_role_permission` VALUES ('1410', '1', '37');
INSERT INTO `sys_role_permission` VALUES ('1411', '1', '14');
INSERT INTO `sys_role_permission` VALUES ('1412', '1', '15');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `role_ids` varchar(100) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`),
  KEY `idx_sys_user_organization_id` (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', null, 'admin', '5e095a08011786aaa8eecb8bd096a7a1', 'c08b45f4dba59048b00d920cd4829a87', '1', '0');
INSERT INTO `sys_user` VALUES ('2', '1', 'test', '5987964431e387f0470ef677a1231595', '44c90e3838bb087bb7d9dbe17a388168', '1', '0');
INSERT INTO `sys_user` VALUES ('3', null, 'zhang', '76ea8805abdb15e71c0f7ff7a653de5b', '645b20bba7d349982c267c8b4ebd24a1', null, '0');
INSERT INTO `sys_user` VALUES ('5', null, 'lisi2', '', 'a1e935b74101e260645d7183cdf88b5e', null, '0');
INSERT INTO `sys_user` VALUES ('7', null, 'yucong', 'c69a0bc5755dcfbdb4778e9376b909e6', '1e7a35cca1ea1a3e0a7dd63b3b10b0ea', null, '0');
INSERT INTO `sys_user` VALUES ('8', null, 'aaa', '', 'f2743a761e86c32a37be77fa19432238', null, '1');
INSERT INTO `sys_user` VALUES ('9', null, '1', '', '345e237d503cad8c8499dc6a28e4cd04', null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('9', '8', '2');
INSERT INTO `sys_user_role` VALUES ('33', '3', '1');
INSERT INTO `sys_user_role` VALUES ('34', '3', '2');
INSERT INTO `sys_user_role` VALUES ('35', '3', '3');
INSERT INTO `sys_user_role` VALUES ('152', '2', '6');
INSERT INTO `sys_user_role` VALUES ('153', '2', '7');
INSERT INTO `sys_user_role` VALUES ('164', '1', '1');
INSERT INTO `sys_user_role` VALUES ('165', '1', '8');
INSERT INTO `sys_user_role` VALUES ('166', '1', '9');
INSERT INTO `sys_user_role` VALUES ('176', '7', '9');
