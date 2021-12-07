# 系统模板
## 说明
### 建库语句
```
CREATE DATABASE `demo` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```
### start.sh
```
nohup java -jar atls-1.0.0.jar --server.port=28081 --spring.profiles.active=235 -Xms1024m -Xmx1536m -XX:PermSize=128M -XX:MaxPermSize=256M &

tail -f nohup.out
```
### shutdown.sh
```$xslt
pid=`ps -ef|grep "java -jar"|grep -v grep|awk '{print $2}'`

kill -9 $pid
```
