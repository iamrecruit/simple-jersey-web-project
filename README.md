# Java Web 基础项目（基于Jersey，MyBatis）

=======

## 依赖

- Jdk 1.8
- Maven
- MySQL(root用户密码需要为空,需要设置默认编码为utf-8)

## 使用

IDE使用

用IntelliJ, 点击菜单上的 File -> Import Project... -> 选择本地该项目文件夹下的pom.xml 就可以导入成功。

## 准备环境

初始化数据库

建库
    
    mysql -uroot
    create database spike_test;
    exit

建表

    mvn clean compile flyway:migrate

导入数据(在pom.xml所在文件夹执行下列命令)

    
    mysql -uroot -Dspike_test < src/main/resources/db/seed/init-spike.sql

## 启动

命令行启动

    mvn clean compile exec:java

这样执行的话，不打印异常，如果想打印异常的话，需要配置java.util.logging.config.file，这样的话，需要配置log.conf的全路径，比如当项目路径在 /home/userName/develop/simple-jersey-web-project
那么需要打印的命令就是：

    mvn clean compile exec:java -Djava.util.logging.config.file=/home/userName/develop/simple-jersey-web-project/src/main/resources/log.conf


## 其他
    
test/specs 下面是一个验收测试的样例，不影响使用，后面的课程会讲到。

