### Nacos 安装

- Nacos 本机启动命名：nohup sh startup.sh -m standalone >out.txt &
- 单机上集群：
    >  java.lang.IllegalStateException: unable to find local peer: c9c1b4e91d2a:8848, all peers: [192.168.24.139:8850, 192.168.24.139:8849, 192.168.24.139:8848]
 
    需要通过nacos.inetutils参数，可以指定Nacos使用的网卡和IP地址。
    - nacos.inetutils.ip-address=192.168.24.139 #ip-address参数可以直接设置nacos的ip
    - nacos.inetutils.use-only-site-local-interfaces=true #use-only-site-local-interfaces参数可以让nacos使用局域网ip，这个在nacos部署的机器有多网卡时很有用，可以让nacos选择局域网网卡