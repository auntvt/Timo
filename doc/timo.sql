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
) ENGINE=InnoDB AUTO_INCREMENT=488 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_action_log 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `sys_action_log` DISABLE KEYS */;
INSERT INTO `sys_action_log` (`id`, `name`, `type`, `clazz`, `method`, `model`, `record_id`, `message`, `create_date`, `create_by`) VALUES
	(476, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-06 23:49:06', 1),
	(477, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 00:55:42', 1),
	(478, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 00:56:18', 1),
	(479, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 01:00:56', 1),
	(480, '系统异常', 3, 'com.linln.admin.core.exception.ResultExceptionHandler', 'runtimeException', NULL, NULL, 'org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type \'net.sf.ehcache.CacheManager\' available\n	org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:346)\n	org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:333)\n	org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1107)\n	com.linln.core.utils.SpringContextUtil.getBean(SpringContextUtil.java:41)\n	com.linln.admin.system.controller.MainController.main(MainController.java:57)\n	com.linln.admin.system.controller.MainController$$FastClassBySpringCGLIB$$bc0e5c6f.invoke(<generated>)\n	org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:746)\n	org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)\n	org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)\n	org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)\n	org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\n	org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:92)\n	org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\n	org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)\n	com.linln.admin.system.controller.MainController$$EnhancerBySpringCGLIB$$7b0b783f.main(<generated>)\n	sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	java.lang.reflect.Method.invoke(Method.java:498)\n	org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:209)\n	org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:136)\n	org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)\n	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877)\n	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783)\n	org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)\n	org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)\n	org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)\n	org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)\n	javax.servlet.http.HttpServlet.service(HttpServlet.java:635)\n	org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)\n	javax.servlet.http.HttpServlet.service(HttpServlet.java:742)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\n	org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n	org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n	org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n	org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\n	org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)\n	org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\n	org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\n	org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\n	org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\n	org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)\n	org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493)\n	org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)\n	org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)\n	org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)\n	org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\n	org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:800)\n	org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:800)\n	org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1471)\n	org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	java.lang.Thread.run(Thread.java:748)', '2018-11-07 14:20:54', 1),
	(481, '系统异常', 3, 'com.linln.admin.core.exception.ResultExceptionHandler', 'runtimeException', NULL, NULL, 'org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type \'org.springframework.cache.CacheManager\' available\n	org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:346)\n	org.springframework.beans.factory.support.DefaultListableBeanFactory.getBean(DefaultListableBeanFactory.java:333)\n	org.springframework.context.support.AbstractApplicationContext.getBean(AbstractApplicationContext.java:1107)\n	com.linln.core.utils.SpringContextUtil.getBean(SpringContextUtil.java:41)\n	com.linln.admin.system.controller.MainController.main(MainController.java:57)\n	com.linln.admin.system.controller.MainController$$FastClassBySpringCGLIB$$bc0e5c6f.invoke(<generated>)\n	org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)\n	org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:746)\n	org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)\n	org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)\n	org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)\n	org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\n	org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:92)\n	org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:185)\n	org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)\n	com.linln.admin.system.controller.MainController$$EnhancerBySpringCGLIB$$eed867a4.main(<generated>)\n	sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	java.lang.reflect.Method.invoke(Method.java:498)\n	org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:209)\n	org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:136)\n	org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102)\n	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877)\n	org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783)\n	org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n	org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991)\n	org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)\n	org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)\n	org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)\n	javax.servlet.http.HttpServlet.service(HttpServlet.java:635)\n	org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:851)\n	javax.servlet.http.HttpServlet.service(HttpServlet.java:742)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)\n	org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)\n	org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)\n	org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)\n	org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:449)\n	org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)\n	org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)\n	org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)\n	org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)\n	org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)\n	org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.HttpPutFormContentFilter.doFilterInternal(HttpPutFormContentFilter.java:109)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)\n	org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)\n	org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n	org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n	org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)\n	org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n	org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493)\n	org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)\n	org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:81)\n	org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)\n	org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:342)\n	org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:800)\n	org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n	org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:800)\n	org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1471)\n	org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n	java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)\n	java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)\n	org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n	java.lang.Thread.run(Thread.java:748)', '2018-11-07 14:25:46', 1),
	(482, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 14:43:45', 1),
	(483, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 14:46:50', 1),
	(484, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 14:47:58', 1),
	(485, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 16:14:42', 1),
	(486, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-07 18:24:46', 1),
	(487, '用户登录', 2, 'com.linln.admin.system.controller.MainController', 'login', NULL, NULL, '后台登录成功', '2018-11-08 22:23:24', 1);
/*!40000 ALTER TABLE `sys_action_log` ENABLE KEYS */;

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
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_file 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `sys_file` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;

-- 正在导出表  timo.sys_menu 的数据：~54 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `title`, `pid`, `pids`, `url`, `icon`, `type`, `sort`, `remark`, `create_date`, `update_date`, `create_by`, `update_by`, `status`) VALUES
	(1, '菜单管理', 2, '[0],[2]', '/menu/index', '', 2, 2, '', '2018-09-29 00:02:10', '2018-10-31 19:43:18', 1, 1, 1),
	(2, '系统管理', 0, '[0]', '#', 'fa fa-cog', 1, 2, '', '2018-09-29 00:05:50', '2018-11-01 11:51:08', 1, 1, 1),
	(3, '添加', 1, '[0],[2],[1]', '/menu/add', NULL, 3, 1, NULL, '2018-09-29 00:06:57', '2018-09-29 00:06:59', 1, NULL, 1),
	(4, '角色管理', 2, '[0],[2]', '/role/index', '', 2, 3, '', '2018-09-29 00:08:14', '2018-10-31 19:43:18', 1, 1, 1),
	(5, '添加', 4, '[0],[2],[4]', '/role/add', NULL, 3, 1, NULL, '2018-09-29 00:09:04', '2018-09-29 00:09:09', NULL, NULL, 1),
	(6, '主页', 0, '[0]', '/index', 'layui-icon layui-icon-home', 1, 1, '', '2018-09-29 00:09:56', '2018-11-01 11:51:08', NULL, 1, 1),
	(7, '用户管理', 2, '[0],[2]', '/user/index', '', 2, 1, '', '2018-09-29 00:43:50', '2018-10-31 19:43:09', 1, 1, 1),
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
	(21, '字典管理', 2, '[0],[2]', '/dict/index', '', 2, 4, '', '2018-10-05 00:55:52', '2018-10-31 19:27:06', 1, 1, 1),
	(22, '字典添加', 21, '[0],[2],[21]', '/dict/add', '', 3, 1, '', '2018-10-05 00:56:26', '2018-10-05 00:56:26', 1, 1, 1),
	(23, '字典编辑', 21, '[0],[2],[21]', '/dict/edit', '', 3, 2, '', '2018-10-05 00:57:08', '2018-10-05 00:57:08', 1, 1, 1),
	(24, '字典详细', 21, '[0],[2],[21]', '/dict/detail', '', 3, 3, '', '2018-10-05 00:57:42', '2018-10-05 01:04:38', 1, 1, 1),
	(25, '修改状态', 21, '[0],[2],[21]', '/dict/status', '', 3, 4, '', '2018-10-05 00:58:27', '2018-10-05 00:58:36', 1, 1, 1),
	(26, '行为日志', 2, '[0],[2]', '/actionLog/index', '', 2, 5, '', '2018-10-14 16:52:01', '2018-10-31 18:01:17', 1, 1, 1),
	(27, '日志详细', 26, '[0],[2],[26]', '/actionLog/detail', '', 3, 1, '', '2018-10-14 21:07:11', '2018-10-18 20:32:01', 1, 1, 1),
	(28, '日志删除', 26, '[0],[2],[26]', '/actionLog/delete', '', 3, 2, '', '2018-10-14 21:08:17', '2018-10-14 21:08:17', 1, 1, 1),
	(30, '开发中心', 0, '[0]', '#', 'fa fa-gavel', 1, 3, '', '2018-10-19 16:38:23', '2018-11-01 11:51:08', 1, 1, 1),
	(31, '代码生成', 30, '[0],[30]', '/code/index', '', 2, 1, '', '2018-10-19 16:39:04', '2018-10-19 17:48:48', 1, 1, 1),
	(95, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:12:00', '2018-11-01 11:18:19', 1, 1, 3),
	(96, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:18:52', '2018-11-01 11:21:29', 1, 1, 3),
	(97, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:21:34', '2018-11-01 11:21:54', 1, 1, 3),
	(98, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:22:01', '2018-11-01 11:23:05', 1, 1, 3),
	(99, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:23:23', '2018-11-01 11:28:55', 1, 1, 3),
	(100, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:29:27', '2018-11-01 11:36:01', 1, 1, 3),
	(101, '添加', 100, '[0],[100]', '/order/add', NULL, 3, 4, NULL, '2018-11-01 11:29:27', '2018-11-01 11:36:01', 1, 1, 3),
	(102, '编辑', 100, '[0],[100]', '/order/edit', NULL, 3, 1, NULL, '2018-11-01 11:29:27', '2018-11-01 11:36:01', 1, 1, 3),
	(103, '详细', 100, '[0],[100]', '/order/detail', NULL, 3, 2, NULL, '2018-11-01 11:29:27', '2018-11-01 11:36:01', 1, 1, 3),
	(104, '改变状态', 100, '[0],[100]', '/order/status', NULL, 3, 3, NULL, '2018-11-01 11:29:27', '2018-11-01 11:36:01', 1, 1, 3),
	(105, '订单管理', 0, '[0]', '/order/index', '', 1, 1, '', '2018-11-01 11:36:13', '2018-11-01 11:37:05', 1, 1, 3),
	(106, '添加', 105, '[0],[105]', '/order/add', NULL, 3, 4, NULL, '2018-11-01 11:36:13', '2018-11-01 11:37:05', 1, 1, 3),
	(107, '编辑', 105, '[0],[105]', '/order/edit', NULL, 3, 1, NULL, '2018-11-01 11:36:13', '2018-11-01 11:37:05', 1, 1, 3),
	(108, '详细', 105, '[0],[105]', '/order/detail', NULL, 3, 2, NULL, '2018-11-01 11:36:13', '2018-11-01 11:37:05', 1, 1, 3),
	(109, '改变状态', 105, '[0],[105]', '/order/status', NULL, 3, 3, NULL, '2018-11-01 11:36:13', '2018-11-01 11:37:05', 1, 1, 3),
	(110, 'order', 0, '[0]', '/order/index', NULL, 1, 4, NULL, '2018-11-01 11:51:08', '2018-11-01 16:44:59', 1, 1, 3),
	(111, '添加', 110, '[0],[110]', '/order/add', NULL, 3, 4, NULL, '2018-11-01 11:51:08', '2018-11-01 16:44:59', 1, 1, 3),
	(112, '编辑', 110, '[0],[110]', '/order/edit', NULL, 3, 1, NULL, '2018-11-01 11:51:08', '2018-11-01 16:44:59', 1, 1, 3),
	(113, '详细', 110, '[0],[110]', '/order/detail', NULL, 3, 2, NULL, '2018-11-01 11:51:08', '2018-11-01 16:44:59', 1, 1, 3),
	(114, '改变状态', 110, '[0],[110]', '/order/status', NULL, 3, 3, NULL, '2018-11-01 11:51:08', '2018-11-01 16:44:59', 1, 1, 3),
	(115, '微信商城', 0, '[0]', '/goods/index', NULL, 1, 4, NULL, '2018-11-01 16:48:58', '2018-11-01 16:49:51', 1, 1, 3),
	(116, '添加', 115, '[0],[115]', '/goods/add', NULL, 3, 4, NULL, '2018-11-01 16:48:58', '2018-11-01 16:49:51', 1, 1, 3),
	(117, '编辑', 115, '[0],[115]', '/goods/edit', NULL, 3, 1, NULL, '2018-11-01 16:48:59', '2018-11-01 16:49:51', 1, 1, 3),
	(118, '详细', 115, '[0],[115]', '/goods/detail', NULL, 3, 2, NULL, '2018-11-01 16:48:59', '2018-11-01 16:49:51', 1, 1, 3),
	(119, '改变状态', 115, '[0],[115]', '/goods/status', NULL, 3, 3, NULL, '2018-11-01 16:48:59', '2018-11-01 16:49:51', 1, 1, 3);
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
	(1, '管理员', 'admin', '', '2018-09-29 00:12:40', '2018-11-01 16:48:59', 1, 1, 1),
	(2, '用户组', 'user', '', '2018-09-30 16:04:32', '2018-09-30 16:24:41', 1, 1, 1),
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

-- 正在导出表  timo.sys_role_menu 的数据：~59 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
	(1, 1),
	(1, 2),
	(2, 2),
	(3, 2),
	(1, 3),
	(1, 4),
	(3, 4),
	(1, 5),
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
	(3, 11),
	(1, 12),
	(3, 12),
	(1, 13),
	(3, 13),
	(1, 14),
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
	(1, 31);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 timo.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '密码盐',
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
INSERT INTO `sys_user` (`id`, `username`, `nickname`, `password`, `salt`, `picture`, `sex`, `email`, `phone`, `is_role`, `remark`, `create_date`, `update_date`, `status`) VALUES
	(1, 'admin', '超级管理员', '66b54a28f47a458b2415f5aa675e4bfa872625c2475a9b6e40073188d532a352', 'tz69pk', '/images/user.jpg', '1', '10086@163.com', '10086', 1, '超级管理员【admin】', '2018-08-09 23:00:03', '2018-11-06 23:48:53', 1),
	(2, 'user1', '用户1', '38d92c239dae522f04698c48ead2b2dec1149fb014a0098aabaa820ec7aad7c9', 'ZEmxcr', '/images/user.jpg', '1', '10086@qq.com', '10086', 1, '啦啦啦啦啦啦啦', '2018-09-30 16:25:22', '2018-10-30 15:01:03', 1),
	(3, 'user2', '用户2', '919c34c7ec14595de58e93c2bc652004424a4b28fe76e5de0884103110609cc8', '3XzDVs', '/images/user.jpg', '1', 'linln@qq.com', '10086', 1, '啦啦啦', '2018-09-30 16:26:07', '2018-10-30 15:01:03', 1);
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
