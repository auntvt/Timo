-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.23 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 timo.sys_action_log 结构
CREATE TABLE IF NOT EXISTS `sys_action_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) DEFAULT NULL COMMENT '日志名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '日志类型',
  `ipaddr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `clazz` varchar(255) DEFAULT NULL COMMENT '产生日志的类',
  `method` varchar(255) DEFAULT NULL COMMENT '产生日志的方法',
  `model` varchar(255) DEFAULT NULL COMMENT '产生日志的表',
  `record_id` bigint(20) DEFAULT NULL COMMENT '产生日志的数据id',
  `message` text COMMENT '日志消息',
  `create_date` datetime DEFAULT NULL COMMENT '记录时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '产生日志的用户',
  PRIMARY KEY (`id`),
  KEY `FKg3p1utwpm133f87x17h4o21wd` (`create_by`),
  CONSTRAINT `FKg3p1utwpm133f87x17h4o21wd` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_action_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_action_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_action_log` ENABLE KEYS */;

-- 导出  表 timo.sys_dept 结构
CREATE TABLE IF NOT EXISTS `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `pid` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `pids` varchar(255) DEFAULT NULL COMMENT '所有父级编号',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`),
  KEY `FKifwd1h4ciusl3nnxrpfpv316u` (`create_by`),
  KEY `FK83g45s1cjqqfpifhulqhv807m` (`update_by`),
  CONSTRAINT `FK83g45s1cjqqfpifhulqhv807m` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKifwd1h4ciusl3nnxrpfpv316u` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_dept 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` (`id`, `title`, `pid`, `pids`, `sort`, `remark`, `create_date`, `update_date`, `create_by`, `update_by`, `status`) VALUES
	(1, '总公司', 0, '[0]', 1, '', '2018-12-02 17:41:23', '2018-12-02 18:07:03', 1, 1, 1),
	(2, '技术部门', 1, '[0],[1]', 1, '', '2018-12-02 17:51:04', '2018-12-02 18:07:03', 1, 1, 1),
	(3, '市场部门', 1, '[0],[1]', 2, '', '2018-12-02 17:51:42', '2018-12-02 18:07:03', 1, 1, 1),
	(4, '研发部门', 1, '[0],[1]', 4, '', '2018-12-02 17:51:55', '2018-12-05 20:07:28', 1, 1, 1),
	(5, '测试部门', 1, '[0],[1]', 3, '', '2018-12-02 17:52:07', '2018-12-05 20:07:28', 1, 1, 1);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;

-- 导出  表 timo.sys_dict 结构
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '字典名称',
  `name` varchar(255) DEFAULT NULL COMMENT '字典键名',
  `type` tinyint(4) DEFAULT NULL COMMENT '字典类型',
  `value` text COMMENT '字典键值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`),
  KEY `FKag4shuprf2tjot9i1mhh37kk6` (`create_by`),
  KEY `FKoyng5jlifhsme0gc1lwiub0lr` (`update_by`),
  CONSTRAINT `FKag4shuprf2tjot9i1mhh37kk6` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKoyng5jlifhsme0gc1lwiub0lr` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_dict 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`id`, `title`, `name`, `type`, `value`, `remark`, `create_date`, `update_date`, `create_by`, `update_by`, `status`) VALUES
	(3, '数据状态', 'DATA_STATUS', 2, '1:正常\r\n2:冻结\r\n3:删除', '', '2018-10-05 16:03:11', '2018-10-05 16:11:41', 1, 1, 1),
	(5, '字典类型', 'DICT_TYPE', 2, '1:字符值\r\n2:键值对\r\n3:枚举类', '', '2018-10-05 20:08:55', '2018-10-13 12:52:24', 1, 1, 1),
	(6, '用户性别', 'USER_SEX', 2, '1:男\r\n2:女', '', '2018-10-05 20:12:32', '2018-10-05 20:12:32', 1, 1, 1),
	(7, '菜单类型', 'MENU_TYPE', 2, '1:一级菜单\r\n2:子级菜单\r\n3:不是菜单', '', '2018-10-05 20:24:57', '2018-10-13 13:56:05', 1, 1, 1),
	(8, '搜索栏状态', 'SEARCH_STATUS', 2, '1:正常\r\n2:冻结', '', '2018-10-06 19:41:32', '2018-10-18 19:22:03', 1, 1, 1),
	(9, '日志类型', 'LOG_TYPE', 2, '1:业务\r\n2:登录\r\n3:系统', '', '2018-10-14 20:36:19', '2018-10-15 10:34:56', 1, 1, 1);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;

-- 导出  表 timo.sys_file 结构
CREATE TABLE IF NOT EXISTS `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `mime` varchar(255) DEFAULT NULL COMMENT 'MIME文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `md5` varchar(255) DEFAULT NULL COMMENT 'MD5值',
  `sha1` varchar(255) DEFAULT NULL COMMENT 'SHA1值',
  `create_by` bigint(20) DEFAULT NULL COMMENT '上传者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FKkkles8yp0a156p4247cc22ovn` (`create_by`),
  CONSTRAINT `FKkkles8yp0a156p4247cc22ovn` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_file 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
INSERT INTO `sys_file` (`id`, `name`, `path`, `mime`, `size`, `md5`, `sha1`, `create_by`, `create_date`) VALUES
	(41, '44d8d8faf65b4b7a9d2addbceca04719.jpg', '/upload/picture/20181207/44d8d8faf65b4b7a9d2addbceca04719.jpg', 'image/jpeg', 20028, '89345293b4217b7abd4cb239cd6d9448', 'c7a1006fda886c6da001b2f4ddd4342cd3a791d9', 1, '2018-12-07 01:45:41'),
	(42, '8681f62fb5e9436794af274d733023cc.jpg', '/upload/picture/20181207/8681f62fb5e9436794af274d733023cc.jpg', 'image/jpeg', 15042, 'f2941f8bcb866c996c542c1f8660e8c2', '1d834f5e73c2224031fff1eb94d045123f6aa62c', 1, '2018-12-07 01:46:05'),
	(43, 'aeb7371e621f4cbaad4035ef06408e07.jpg', '/upload/picture/20181207/aeb7371e621f4cbaad4035ef06408e07.jpg', 'image/jpeg', 14920, '749f06cca25b497ed3778f23f8f86169', 'e44e13f21f2981970d8e991c154e7971c8646bfa', 1, '2018-12-07 01:49:27'),
	(44, '1f7767bff6dd4fe0ac4aa8720c6e5a47.png', '/upload/picture/20181207/1f7767bff6dd4fe0ac4aa8720c6e5a47.png', 'image/png', 60894, '4ea94eac706a4887a2e0aff362f73eaa', '67bb77f310341d31a9800c350f2d15396ffac3c3', 1, '2018-12-07 01:49:34'),
	(45, '7233fbd0dd374f7cb6fa5872a87029c0.jpg', '/upload/images/20181207/7233fbd0dd374f7cb6fa5872a87029c0.jpg', 'image/jpeg', 888414, '3d932ba207206df79d67cd2befe446d4', '50722998a3665eadec22bce30b0853123690dba9', 1, '2018-12-07 01:51:17'),
	(46, 'dfb2598b0f2e4a129b1379641697e5e9.jpg', '/upload/images/20181207/dfb2598b0f2e4a129b1379641697e5e9.jpg', 'image/jpeg', 611314, 'a8c0bdd497e602277c195afddadc42e0', '581a790cea7712364e2c65f392ccf568b3b00634', 1, '2018-12-07 01:56:30'),
	(47, 'd668556e88074a289815b63f019771e8.jpg', '/upload/images/20181207/d668556e88074a289815b63f019771e8.jpg', 'image/jpeg', 3210554, 'd0348f9fdc838345225893540d4f4c86', 'b8d9bb58d4e7425f0d36ee3c2775e48ae7f984ef', 1, '2018-12-07 01:56:30'),
	(48, 'a215c323e1e646838e52db4af824b6c0.jpg', '/upload/images/20181207/a215c323e1e646838e52db4af824b6c0.jpg', 'image/jpeg', 330100, '4d133d7ce8a80e2a823963ebf8d7ba8a', '492e52d205ca2a667cbd4ec78bf435fc68c63ad9', 1, '2018-12-07 02:08:33'),
	(49, 'd1e43304bf504597928229645b14326e.jpg', '/upload/images/20181207/d1e43304bf504597928229645b14326e.jpg', 'image/jpeg', 499979, '25ed5cc684fd6b589d409754978ff8ca', '25ec8df4753932ccd2b98c6d35a57474efe859f7', 1, '2018-12-07 02:08:41'),
	(50, '65db6654411e415894ddbbbe8c4330c7.jpg', '/upload/images/20181207/65db6654411e415894ddbbbe8c4330c7.jpg', 'image/jpeg', 74536, 'cc5b27b59e717a3a3334fda16489d1cd', '641f43dfce92cc19d771bdb8cceab1953a414927', 1, '2018-12-07 02:16:59'),
	(51, 'ad5d3b408c4149afa5af6accdbc85c0f.jpg', '/upload/images/20181207/ad5d3b408c4149afa5af6accdbc85c0f.jpg', 'image/jpeg', 106833, 'ae58c66eb37c46f97877ff9cac660b39', 'd640a7330ff78c06076d68af805110f314f94b4d', 1, '2018-12-07 02:17:01'),
	(52, '3c6d252aaad04936952cac7623274979.jpg', '/upload/images/20181207/3c6d252aaad04936952cac7623274979.jpg', 'image/jpeg', 1135181, 'b73e44a2a04f539b7bf6a7f12928989d', '5f5c1df60fb8ae309adf7512c06b4fa13103fde7', 1, '2018-12-07 02:19:31');
/*!40000 ALTER TABLE `sys_file` ENABLE KEYS */;

-- 导出  表 timo.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `pid` bigint(20) DEFAULT NULL COMMENT '父级编号',
  `pids` varchar(255) DEFAULT NULL COMMENT '所有父级编号',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL地址',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型（1:一级菜单,2:子级菜单,3:不是菜单）',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`),
  KEY `FKoxg2hi96yr9pf2m0stjomr3we` (`create_by`),
  KEY `FKsiko0qcr8ddamvrxf1tk4opgc` (`update_by`),
  CONSTRAINT `FKoxg2hi96yr9pf2m0stjomr3we` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKsiko0qcr8ddamvrxf1tk4opgc` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_menu 的数据：~37 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `title`, `pid`, `pids`, `url`, `icon`, `type`, `sort`, `remark`, `create_date`, `update_date`, `create_by`, `update_by`, `status`) VALUES
	(1, '菜单管理', 2, '[0],[2]', '/menu/index', '', 2, 3, '', '2018-09-29 00:02:10', '2018-11-13 21:48:58', 1, 1, 1),
	(2, '系统管理', 0, '[0]', '#', 'fa fa-cog', 1, 2, '', '2018-09-29 00:05:50', '2018-11-01 11:51:08', 1, 1, 1),
	(3, '添加', 1, '[0],[2],[1]', '/menu/add', NULL, 3, 1, NULL, '2018-09-29 00:06:57', '2018-09-29 00:06:59', 1, NULL, 1),
	(4, '角色管理', 2, '[0],[2]', '/role/index', '', 2, 2, '', '2018-09-29 00:08:14', '2018-11-13 21:48:58', 1, 1, 1),
	(5, '添加', 4, '[0],[2],[4]', '/role/add', NULL, 3, 1, NULL, '2018-09-29 00:09:04', '2018-09-29 00:09:09', NULL, NULL, 1),
	(6, '主页', 0, '[0]', '/index', 'layui-icon layui-icon-home', 1, 1, '', '2018-09-29 00:09:56', '2018-11-01 11:51:08', NULL, 1, 1),
	(7, '用户管理', 2, '[0],[2]', '/user/index', '', 2, 1, '', '2018-09-29 00:43:50', '2018-11-13 21:48:58', 1, 1, 1),
	(8, '编辑', 1, '[0],[2],[1]', '/menu/edit', NULL, 3, 2, NULL, '2018-09-29 00:57:33', '2018-09-29 00:57:38', 1, 1, 1),
	(9, '详细', 1, '[0],[2],[1]', '/menu/detail', '', 3, 3, '', '2018-09-29 01:03:00', '2018-09-29 01:03:00', 1, 1, 1),
	(10, '修改状态', 1, '[0],[2],[1]', '/menu/status', '', 3, 4, '', '2018-09-29 01:03:43', '2018-09-29 01:03:43', 1, 1, 1),
	(11, '编辑', 4, '[0],[2],[4]', '/role/edit', '', 3, 2, '', '2018-09-29 01:06:13', '2018-09-29 01:06:13', 1, 1, 1),
	(12, '授权', 4, '[0],[2],[4]', '/role/auth', '', 3, 3, '', '2018-09-29 01:06:57', '2018-09-29 01:06:57', 1, 1, 1),
	(13, '详细', 4, '[0],[2],[4]', '/role/detail', '', 3, 4, '', '2018-09-29 01:08:00', '2018-09-29 01:08:00', 1, 1, 1),
	(14, '修改状态', 4, '[0],[2],[4]', '/role/status', '', 3, 5, '', '2018-09-29 01:08:22', '2018-09-29 01:08:22', 1, 1, 1),
	(15, '编辑', 7, '[0],[2],[7]', '/user/edit', '', 3, 2, '', '2018-09-29 21:17:17', '2018-09-29 21:17:17', 1, 1, 1),
	(16, '添加', 7, '[0],[2],[7]', '/user/add', '', 3, 1, '', '2018-09-29 21:17:58', '2018-09-29 21:17:58', 1, 1, 1),
	(17, '修改密码', 7, '[0],[2],[7]', '/user/pwd', '', 3, 3, '', '2018-09-29 21:19:40', '2018-09-29 21:19:40', 1, 1, 1),
	(18, '权限分配', 7, '[0],[2],[7]', '/user/role', '', 3, 4, '', '2018-09-29 21:20:35', '2018-09-29 21:20:35', 1, 1, 1),
	(19, '详细', 7, '[0],[2],[7]', '/user/detail', '', 3, 5, '', '2018-09-29 21:21:00', '2018-09-29 21:21:00', 1, 1, 1),
	(20, '修改状态', 7, '[0],[2],[7]', '/user/status', '', 3, 6, '', '2018-09-29 21:21:18', '2018-09-29 21:21:18', 1, 1, 1),
	(21, '字典管理', 2, '[0],[2]', '/dict/index', '', 2, 5, '', '2018-10-05 00:55:52', '2018-12-02 16:38:08', 1, 1, 1),
	(22, '字典添加', 21, '[0],[2],[21]', '/dict/add', '', 3, 1, '', '2018-10-05 00:56:26', '2018-10-05 00:56:26', 1, 1, 1),
	(23, '字典编辑', 21, '[0],[2],[21]', '/dict/edit', '', 3, 2, '', '2018-10-05 00:57:08', '2018-10-05 00:57:08', 1, 1, 1),
	(24, '字典详细', 21, '[0],[2],[21]', '/dict/detail', '', 3, 3, '', '2018-10-05 00:57:42', '2018-10-05 01:04:38', 1, 1, 1),
	(25, '修改状态', 21, '[0],[2],[21]', '/dict/status', '', 3, 4, '', '2018-10-05 00:58:27', '2018-10-05 00:58:36', 1, 1, 1),
	(26, '行为日志', 2, '[0],[2]', '/actionLog/index', '', 2, 6, '', '2018-10-14 16:52:01', '2018-12-05 18:06:36', 1, 1, 1),
	(27, '日志详细', 26, '[0],[2],[26]', '/actionLog/detail', '', 3, 1, '', '2018-10-14 21:07:11', '2018-11-08 22:51:11', 1, 1, 1),
	(28, '日志删除', 26, '[0],[2],[26]', '/actionLog/delete', '', 3, 2, '', '2018-10-14 21:08:17', '2018-11-08 22:51:11', 1, 1, 1),
	(30, '开发中心', 0, '[0]', '#', 'fa fa-gavel', 1, 4, '', '2018-10-19 16:38:23', '2018-11-28 21:22:39', 1, 1, 1),
	(31, '代码生成', 30, '[0],[30]', '/code/index', '', 2, 1, '', '2018-10-19 16:39:04', '2018-11-10 19:34:42', 1, 1, 1),
	(125, '表单构建', 30, '[0],[30]', '/dev/build', '', 2, 2, '表单构建工具', '2018-11-25 16:12:23', '2018-11-25 16:12:23', 1, 1, 1),
	(136, '部门管理', 2, '[0],[2]', '/dept/index', '', 2, 4, '', '2018-12-02 16:33:23', '2018-12-02 16:38:08', 1, 1, 1),
	(137, '添加', 136, '[0],[2],[136]', '/dept/add', '', 3, 1, '', '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(138, '编辑', 136, '[0],[2],[136]', '/dept/edit', NULL, 3, 2, NULL, '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(139, '详细', 136, '[0],[2],[136]', '/dept/detail', NULL, 3, 3, NULL, '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(140, '改变状态', 136, '[0],[2],[136]', '/dept/status', NULL, 3, 4, NULL, '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(141, '三级目录', 26, '[0],[2],[26]', '/actionLog/index', '', 2, 3, '', '2018-12-05 16:42:43', '2018-12-05 18:06:44', 1, 1, 3),
	(142, '按钮1', 141, '[0],[2],[26],[141]', 'client/add', '', 3, 1, '', '2018-12-05 16:44:45', '2018-12-05 18:06:44', 1, 1, 3),
	(143, '按钮2', 141, '[0],[2],[26],[141]', '/wuwu', 'wuwu-icon', 3, 2, '', '2018-12-05 16:50:23', '2018-12-05 18:06:44', 1, 1, 3),
	(144, '三级目录2', 26, '[0],[2],[26]', '/wuwu2', '', 2, 4, '', '2018-12-05 17:57:18', '2018-12-05 18:06:52', 1, 1, 3),
	(145, '城建', 26, '[0],[2],[26]', '#', 'fa fa-gavel', 2, 3, '', '2018-12-05 19:40:11', '2018-12-05 19:40:54', 1, 1, 3),
	(146, '数据接口', 30, '[0],[30]', '/dev/swagger', '', 2, 3, '', '2018-12-09 21:11:11', '2018-12-09 21:11:11', 1, 1, 1);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 timo.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '角色名称（中文名）',
  `name` varchar(255) DEFAULT NULL COMMENT '标识名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新用户',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`),
  KEY `FKdkwvv0rm6j3d5l6hwsy2dplol` (`create_by`),
  KEY `FKrouqqi3f2bgc5o83wdstlh6q4` (`update_by`),
  CONSTRAINT `FKdkwvv0rm6j3d5l6hwsy2dplol` FOREIGN KEY (`create_by`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKrouqqi3f2bgc5o83wdstlh6q4` FOREIGN KEY (`update_by`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_role 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `title`, `name`, `remark`, `create_date`, `update_date`, `create_by`, `update_by`, `status`) VALUES
	(1, '管理员', 'admin', '', '2018-09-29 00:12:40', '2018-12-05 19:50:16', 1, 1, 1),
	(2, '用户组', 'user', '', '2018-09-30 16:04:32', '2018-11-30 23:46:05', 1, 1, 1),
	(3, '用户组2', 'user2', '', '2018-09-30 16:24:20', '2018-10-18 21:28:39', 1, 1, 1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 timo.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKf3mud4qoc7ayew8nml4plkevo` (`menu_id`),
  CONSTRAINT `FKf3mud4qoc7ayew8nml4plkevo` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `FKkeitxsgxwayackgqllio4ohn5` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_role_menu 的数据：~66 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 2),
	(3, 2),
	(1, 3),
	(1, 4),
	(2, 4),
	(3, 4),
	(1, 5),
	(2, 5),
	(3, 5),
	(1, 6),
	(2, 6),
	(3, 6),
	(1, 7),
	(2, 7),
	(3, 7),
	(1, 8),
	(1, 9),
	(1, 10),
	(1, 11),
	(2, 11),
	(3, 11),
	(1, 12),
	(2, 12),
	(3, 12),
	(1, 13),
	(2, 13),
	(3, 13),
	(1, 14),
	(2, 14),
	(3, 14),
	(1, 15),
	(2, 15),
	(3, 15),
	(1, 16),
	(2, 16),
	(3, 16),
	(1, 17),
	(2, 17),
	(3, 17),
	(1, 18),
	(2, 18),
	(3, 18),
	(1, 19),
	(2, 19),
	(3, 19),
	(1, 20),
	(2, 20),
	(3, 20),
	(1, 21),
	(1, 22),
	(1, 23),
	(1, 24),
	(1, 25),
	(1, 26),
	(1, 27),
	(1, 28),
	(1, 30),
	(1, 31),
	(1, 125),
	(1, 136),
	(1, 137),
	(1, 138),
	(1, 139),
	(1, 140);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 timo.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '密码盐',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `picture` varchar(255) DEFAULT NULL COMMENT '头像',
  `sex` varchar(255) DEFAULT NULL COMMENT '性别（1:男,2:女）',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `is_role` tinyint(4) DEFAULT NULL COMMENT '是否拥有角色',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`),
  KEY `FKb3pkx0wbo6o8i8lj0gxr37v1n` (`dept_id`),
  CONSTRAINT `FKb3pkx0wbo6o8i8lj0gxr37v1n` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `password`, `salt`, `dept_id`, `picture`, `sex`, `email`, `phone`, `is_role`, `remark`, `create_date`, `update_date`, `status`) VALUES
	(1, 'admin', '超级管理员', '673b87294c3fc7978cba2faf4599bcad176c223b5b15dffd9cfb196c4aad2bae', 'UW7TsX', 4, '/upload/picture/20181207/1f7767bff6dd4fe0ac4aa8720c6e5a47.png', '2', '10086@163.com', '10086', 1, '', '2018-08-09 23:00:03', '2018-12-08 20:21:38', 1),
	(2, 'linln', '小懒虫', '38d92c239dae522f04698c48ead2b2dec1149fb014a0098aabaa820ec7aad7c9', 'ZEmxcr', 2, '/images/user-picture.jpg', '1', '10086@qq.com', '10086', 1, '啦啦啦啦啦啦啦', '2018-09-30 16:25:22', '2018-12-09 00:17:57', 1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 timo.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_user_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
	(2, 2),
	(2, 3);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
