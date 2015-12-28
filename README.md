# Java Web 基础项目（基于Jersey，MyBatis）

=======

## 依赖

- Jdk 1.8
- Maven
- MySQL(root用户密码需要为空,需要设置默认编码为utf-8)

## 使用

IDE使用

用IntelliJ, 点击菜单上的 File -> Import Project... -> 选择本地该项目文件夹下的pom.xml 就可以导入成功。

命令行执行

    mvn clean compile exec:java
    

初始化数据库

建库
    
    mysql -uroot
    create database spike_test;
    exit

建表

    mvn flyway:migrate

导入数据(在pom.xml所在文件夹执行下列命令)

    
    mysql -uroot -Dspike_test < src/main/resources/db/seed/init-spike.sql
    
 
## 其他
    
test/specs 下面是一个验收测试的样例，不影响使用，后面的课程会讲到。