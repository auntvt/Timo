#{title}实体标题
#{entity}实体url
#{pid}父菜单ID
#{subPid}子菜单ID
INSERT INTO `sys_menu` (`title`, `pid`, `pids`, `icon`, `type`, `url`, `sort`, `status`) VALUES ('#{title}管理', #{pid}, '0,#{pid}', NULL, 2, '/#{entity}/index', 1, 1);
INSERT INTO `sys_menu` (`title`, `pid`, `pids`, `icon`, `type`, `url`, `sort`, `status`) VALUES ('添加', #{subPid}, '0,#{pid},#{subPid}', NULL, 3, '/#{entity}/add', NULL, 1);
INSERT INTO `sys_menu` (`title`, `pid`, `pids`, `icon`, `type`, `url`, `sort`, `status`) VALUES ('编辑', #{subPid}, '0,#{pid},#{subPid}', NULL, 3, '/#{entity}/exit', NULL, 1);
INSERT INTO `sys_menu` (`title`, `pid`, `pids`, `icon`, `type`, `url`, `sort`, `status`) VALUES ('详细', #{subPid}, '0,#{pid},#{subPid}', NULL, 3, '/#{entity}/detail', NULL, 1);
INSERT INTO `sys_menu` (`title`, `pid`, `pids`, `icon`, `type`, `url`, `sort`, `status`) VALUES ('添加', #{subPid}, '0,#{pid},#{subPid}', NULL, 3, '/#{entity}/add', NULL, 1);