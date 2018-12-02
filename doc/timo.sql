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
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_action_log 的数据：~60 rows (大约)
/*!40000 ALTER TABLE `sys_action_log` DISABLE KEYS */;
INSERT INTO `sys_action_log` (`id`, `name`, `type`, `ipaddr`, `clazz`, `method`, `model`, `record_id`, `message`, `create_date`, `create_by`) VALUES
	(68, '用户登录', 2, '127.0.0.1', 'com.linln.admin.system.controller.LoginController', 'login', NULL, NULL, '后台登录成功', '2018-12-03 00:15:57', 1);
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
	(4, '研发部门', 1, '[0],[1]', 3, '', '2018-12-02 17:51:55', '2018-12-02 18:07:03', 1, 1, 1),
	(5, '测试部门', 1, '[0],[1]', 4, '', '2018-12-02 17:52:07', '2018-12-02 18:07:03', 1, 1, 1);
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_file 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
INSERT INTO `sys_file` (`id`, `name`, `path`, `mime`, `size`, `md5`, `sha1`, `create_by`, `create_date`) VALUES
	(1, '40477cf148154b7697d288d438801442.jpg', '/upload/images/20181128/40477cf148154b7697d288d438801442.jpg', 'image/jpeg', 330100, '4d133d7ce8a80e2a823963ebf8d7ba8a', '492e52d205ca2a667cbd4ec78bf435fc68c63ad9', 1, '2018-11-28 21:55:58'),
	(2, 'c6daf8d92ce94e0f88e938257727edf2.jpg', '/upload/images/20181128/c6daf8d92ce94e0f88e938257727edf2.jpg', 'image/jpeg', 499979, '25ed5cc684fd6b589d409754978ff8ca', '25ec8df4753932ccd2b98c6d35a57474efe859f7', 1, '2018-11-28 22:05:02'),
	(3, '35c8ea92578543719b1254d74de3298b.jpg', '/upload/images/20181128/35c8ea92578543719b1254d74de3298b.jpg', 'image/jpeg', 9772, '7977a3517d78c7cca4a8e0e11155238f', '486bf89a63791094c8ba85531489bb3fb8aa3016', 1, '2018-11-28 22:05:39'),
	(4, 'bcc0a0f471474aae8d8ef59832541f22.jpg', '/upload/images/20181128/bcc0a0f471474aae8d8ef59832541f22.jpg', 'image/jpeg', 3210554, 'd0348f9fdc838345225893540d4f4c86', 'b8d9bb58d4e7425f0d36ee3c2775e48ae7f984ef', 1, '2018-11-28 22:05:46'),
	(5, '48a294ffa426493e8f6276418e724394.jpg', '/upload/images/20181128/48a294ffa426493e8f6276418e724394.jpg', 'image/jpeg', 888414, '3d932ba207206df79d67cd2befe446d4', '50722998a3665eadec22bce30b0853123690dba9', 1, '2018-11-28 22:06:29'),
	(6, 'c67961d3c6c048489c10a6b47613e868.jpg', '/upload/images/20181128/c67961d3c6c048489c10a6b47613e868.jpg', 'image/jpeg', 106833, 'ae58c66eb37c46f97877ff9cac660b39', 'd640a7330ff78c06076d68af805110f314f94b4d', 1, '2018-11-28 22:08:45'),
	(7, '8adf9efed7f8439f9dce3930ca60918c.png', '/upload/images/20181128/8adf9efed7f8439f9dce3930ca60918c.png', 'image/png', 2725559, '711f6f8de6b920613a28445e15e87e8c', 'a1be2d80a7702b9216acfcaf66fcd4d7bce2808d', 1, '2018-11-28 22:10:34'),
	(8, 'd5b23dfa82d34806acff15a52aa5c434.jpg', '/upload/images/20181128/d5b23dfa82d34806acff15a52aa5c434.jpg', 'image/jpeg', 611314, 'a8c0bdd497e602277c195afddadc42e0', '581a790cea7712364e2c65f392ccf568b3b00634', 1, '2018-11-28 22:12:51'),
	(9, '2ffe0c3fa23e409c8fd413533749393e.jpg', '/upload/images/20181128/2ffe0c3fa23e409c8fd413533749393e.jpg', 'image/jpeg', 74536, 'cc5b27b59e717a3a3334fda16489d1cd', '641f43dfce92cc19d771bdb8cceab1953a414927', 1, '2018-11-28 22:33:07'),
	(10, 'eba4ef24fa9f4ebb895a1784a3b87d3a.jpg', '/upload/images/20181128/eba4ef24fa9f4ebb895a1784a3b87d3a.jpg', 'image/jpeg', 1135181, 'b73e44a2a04f539b7bf6a7f12928989d', '5f5c1df60fb8ae309adf7512c06b4fa13103fde7', 1, '2018-11-28 22:33:07');
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
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_menu 的数据：~36 rows (大约)
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
	(26, '行为日志', 2, '[0],[2]', '/actionLog/index', '', 2, 6, '', '2018-10-14 16:52:01', '2018-12-02 16:38:08', 1, 1, 1),
	(27, '日志详细', 26, '[0],[2],[26]', '/actionLog/detail', '', 3, 1, '', '2018-10-14 21:07:11', '2018-11-08 22:51:11', 1, 1, 1),
	(28, '日志删除', 26, '[0],[2],[26]', '/actionLog/delete', '', 3, 2, '', '2018-10-14 21:08:17', '2018-11-08 22:51:11', 1, 1, 1),
	(30, '开发中心', 0, '[0]', '#', 'fa fa-gavel', 1, 4, '', '2018-10-19 16:38:23', '2018-11-28 21:22:39', 1, 1, 1),
	(31, '代码生成', 30, '[0],[30]', '/code/index', '', 2, 1, '', '2018-10-19 16:39:04', '2018-11-10 19:34:42', 1, 1, 1),
	(125, '表单构建', 30, '[0],[30]', '/dev/build', '', 2, 2, '表单构建工具', '2018-11-25 16:12:23', '2018-11-25 16:12:23', 1, 1, 1),
	(136, '部门管理', 2, '[0],[2]', '/dept/index', '', 2, 4, '', '2018-12-02 16:33:23', '2018-12-02 16:38:08', 1, 1, 1),
	(137, '添加', 136, '[0],[2],[136]', '/dept/add', '', 3, 1, '', '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(138, '编辑', 136, '[0],[2],[136]', '/dept/edit', NULL, 3, 2, NULL, '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(139, '详细', 136, '[0],[2],[136]', '/dept/detail', NULL, 3, 3, NULL, '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1),
	(140, '改变状态', 136, '[0],[2],[136]', '/dept/status', NULL, 3, 4, NULL, '2018-12-02 16:33:23', '2018-12-02 16:38:37', 1, 1, 1);
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
	(1, '管理员', 'admin', '', '2018-09-29 00:12:40', '2018-12-02 16:33:23', 1, 1, 1),
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_user 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `password`, `salt`, `dept_id`, `picture`, `sex`, `email`, `phone`, `is_role`, `remark`, `create_date`, `update_date`, `status`) VALUES
	(1, 'admin', '超级管理员', '673b87294c3fc7978cba2faf4599bcad176c223b5b15dffd9cfb196c4aad2bae', 'UW7TsX', 2, '/upload/picture/20181119/e8b429165e6744918a1330f7eac8044e.jpg', '1', '10086@163.com', '10086', 1, '', '2018-08-09 23:00:03', '2018-12-03 00:00:19', 1),
	(2, 'user1', '用户1', '38d92c239dae522f04698c48ead2b2dec1149fb014a0098aabaa820ec7aad7c9', 'ZEmxcr', 5, '/images/user-picture.jpg', '2', '10086@qq.com', '10086', 1, '啦啦啦啦啦啦啦', '2018-09-30 16:25:22', '2018-12-02 23:46:44', 1),
	(3, 'user2', '用户2', '919c34c7ec14595de58e93c2bc652004424a4b28fe76e5de0884103110609cc8', '3XzDVs', 2, '/images/user-picture.jpg', '2', 'linln@qq.com', '10086', 1, '啦啦啦', '2018-09-30 16:26:07', '2018-12-02 23:46:49', 1);
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

-- 正在导出表  timo.sys_user_role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
	(1, 1),
	(2, 2),
	(3, 2),
	(2, 3);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
