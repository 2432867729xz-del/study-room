# 校园自习室座位预约与签到系统

一个基于 JavaWeb 的校园自习室座位预约与签到管理系统，供期末课程设计使用。

## 项目介绍

本系统面向学校自习室场景，提供座位浏览、在线预约、到馆签到、取消预约等功能，同时为管理员提供座位管理、预约记录查看、数据统计等后台能力。

## 技术栈

| 层次 | 技术 |
|------|------|
| 语言 | Java 21 |
| Web 规范 | Jakarta Servlet 6.0（**jakarta.servlet，不是 javax**） |
| 页面 | JSP + JSTL |
| 服务器 | Apache Tomcat 10.1 |
| 构建工具 | Maven |
| 数据库 | MySQL 8.x |
| 架构模式 | MVC 分层（bean → dao → service → servlet → jsp） |

## 环境要求

- JDK 21
- Maven 3.8+
- Tomcat 10.1.x
- MySQL 8.x
- IntelliJ IDEA（推荐）

## 项目结构

```
study-room/
├── pom.xml                    # Maven 依赖配置
├── study_room.sql             # 数据库建表脚本
├── README.md
└── src/main/
    ├── java/com/example/
    │   ├── bean/              # 实体类：User、Seat、Reservation
    │   ├── dao/               # 数据访问层：UserDao
    │   ├── service/           # 业务逻辑层：UserService
    │   ├── servlet/           # 控制器：LoginServlet、RegisterServlet、AdminServlet、LogoutServlet
    │   ├── filter/            # 权限过滤器：LoginFilter
    │   └── util/              # 工具类：DBUtil
    └── webapp/
        ├── login.jsp          # 登录页
        ├── register.jsp       # 注册页
        ├── admin/             # 管理员页面
        ├── student/           # 学生页面
        └── WEB-INF/web.xml
```

## 数据库配置

1. 启动 MySQL，执行根目录下的 `study_room.sql` 脚本，自动创建 `study_room` 数据库及表，并插入初始数据。
2. 修改 `src/main/java/com/example/util/DBUtil.java` 中的数据库密码为你自己的 MySQL root 密码：
   ```java
   private static final String PASSWORD = "你的MySQL密码";
   ```

### 初始账号

| 账号 | 密码 | 角色 |
|------|------|------|
| admin | admin123 | 管理员 |
| zhangsan | 123456 | 普通用户 |

## 部署步骤

### 方式一：IDEA 中配置 Tomcat 运行

1. IDEA 打开项目，等待 Maven 依赖下载完成。
2. 顶部菜单 `Run → Edit Configurations`，点击 `+` 选择 `Tomcat Server → Local`。
3. 配置你的 Tomcat 10.1 安装目录。
4. 切换到 `Deployment` 标签，点击 `+` 选择 `study-room:war exploded`，Application context 填 `/study-room`。
5. 点击运行，浏览器自动打开 `http://localhost:8080/study-room/login.jsp`。

### 方式二：打包 war 部署

1. IDEA 右侧 Maven 面板 → `Lifecycle` → 双击 `package`。
2. 把 `target/study-room.war` 复制到 Tomcat 的 `webapps` 目录。
3. 运行 `bin/startup.bat`（Windows）或 `bin/startup.sh`（Linux/Mac）。
4. 浏览器访问 `http://localhost:8080/study-room/login.jsp`。

## 功能模块

### 普通用户
- 注册 / 登录 / 退出登录
- 浏览自习室座位（空闲/已预约状态）
- 预约座位（选择日期和时段）
- 我的预约列表
- 到馆签到、取消预约

### 管理员
- 数据仪表盘（总座位数、已预约数、今日预约数、用户数）
- 座位管理（新增、删除座位）
- 查看全部预约记录
- 权限控制（普通用户无法访问管理员页面）

## 权限说明

系统通过 `LoginFilter` 统一拦截所有请求：
- 未登录用户访问任何页面都会被重定向到登录页。
- 登录后根据角色跳转不同首页。
- 普通用户访问 `/admin/` 下的路径会被拦截并跳回学生首页。
