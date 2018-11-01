# Timo

#### 项目介绍
TIMO后台管理系统，基于SpringBoot 2 + jpa + thymeleaf + shiro 开发的通用型后台管理，采用分模块的方式便于开发和维护，目前已开发的功能：权限管理、字典管理、日志记录、代码生成功能，为快速开发后台管理提供解决方案！

#### 技术选型

- 后端技术：SpringBoot + Jpa + Thymeleaf + Shiro 


- 前端技术：Layui + Jquery  + zTree + Font-awesome

#### 项目结构

```
├─admin		--后台管理模块
│	│
│	├─java
│	│	├─com.linln.admin.core----------------后台模块核心模块
│	│	└─com.linln.admin.system----------------后台模块系统模块
│	│	
│	└─resources
│		├─static----------------静态资源目录
│		└─templates----------------前端模板目录
│			├─common----------------公共模板目录
│			└─system----------------系统模板目录
│
├─boot		--引导模块
|	|
|	├─java
|	|	└─com.linln.BootApplication----------------启动项目入口
|	|
|	└─resources
|		└─application.properties----------------项目配置文件
|	
├─core		--核心模块
|	
└─devtools	--开发中心模块
```


#### 安装教程

- ##### 环境及插件要求

   - Jdk8+
   - Mysql5.5+
   - Maven
   - Lombok

- ##### 导入项目

   - IntelliJ IDEA：Import Project -> Import Project from external model -> Maven
   - Eclipse：Import -> Exising Mavne Project


- ##### 运行项目

  - 通过Java应用方式运行boot模块下的com.linln.BootApplication.java文件
  - 数据库配置：数据库名称timo   用户root    密码root
  - 访问地址：http://localhost:8080/
  - 默认帐号密码：admin/123456

#### 使用说明

1. 文档：doc/使用文档.docx
2. SQL文件：doc/timo.sql

#### 预览图

![项目结构图](./doc/images/项目结构图.jpg)

![登录页面](./doc/images/登录页面.jpg)

![用户管理](./doc/images/用户管理.jpg)

![菜单管理](./doc/images/菜单管理.jpg)

![字典管理](./doc/images/字典管理.jpg)

![行为日志](./doc/images/行为日志.jpg)

![代码生成](./doc/images/代码生成.jpg)

