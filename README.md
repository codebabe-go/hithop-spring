# hithop-spring
学习spring过程中的打怪升级, 期间会设计到`分布式`, `大数据`, `rpc`, `消息队列`...

## 介绍
1. 所有的单元测试用的都是junit & mockito
2. 常见的分布式系统搭建
    - webapp: nginx
    - rpc(dubbo): zk
    - mq: 切分队列进行下发
    - local server: 自定义规则下发, 如corpId
    * 后三个都是借助zk, 各自注册, zk上存储服务器信息, 然后让zk去下发    

## 目的
1. 学习spring在工程项目中的应用
2. 了解用maven构建一个较大规模java项目的过程
3. 借助spring来了解一些生态, 包括分布式和大数据
4. 微服务也是其中一个重点
5. 模块化单元测试, 规范代码
6. 尽可能在该服务中用到多种语言, 初步计划有java, Scala, golang