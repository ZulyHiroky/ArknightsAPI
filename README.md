# 警告！！

**本项目中包含：**

​	**HanYuPinYin**

​	**命名不规范**

​	**代码重复**

基本操作，无需吐槽

# TIPS：

本项目主要逻辑均基于SpringBoot+MyBatis+MySQL实现，采用机器人框架->调用API->返回数据->发送消息的方式。

项目主要实现机器人的自定义事件触发、自动回复。

感谢大佬提供的[机器人框架](https://github.com/OPQBOT/OPQ/wiki)技术支持，有关框架的部署请参考上述文档。

# 项目结构

main：

​	java：

​		config:Spring配置文件

​		controller：控制层

​		dao：Mapper文件，mapping的interface

​		job：定时任务

​		model：数据封装bean类

​		service：服务类，主要计算逻辑

​		util：工具类，存放静态方法

​		vo：返回类型封装

​	resource：

​		lua：lua插件留底

​		mapping：mapping文件，SQL语句

​		sql：数据库结构及数据留底

# 项目部署：

拷贝项目目录中sql目录下的SQL脚本，在mysql中执行，并在application.yml中配置你的mysql。

将项目打包运行，记录运行url（一般是127.0.0.1:8086）

根据上文机器人WIKI链接，部署并启动OPQ机器人，将lua中的插件复制到Plugin目录下，若机器人框架和spring项目不在同一台机器运行，请保证两台机器的网络畅通。

修改lua插件第6行url地址为自己的api地址。

群聊中发送"十连 [卡池名]"触发事件，卡池名为空或找不到卡池则默认抽取常规池。

抽卡的垫刀数是根据抽卡人qq记录的，因此会记录QQ，涉及部分隐私信息，虽已将信息加密，但仍有泄露风险，请酌情使用或修改源码。

我国有一套完善的法律制度，请不要使用本项目传播不能过审的信息，应当遵守当地法律法规。

