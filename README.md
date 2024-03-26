
## 项目介绍
木兰湾管理系统是用于管理个人消费、锻炼、音乐、阅读、健康、饮食、人生经历等各个衣食住行信息的系统，通过提醒、计划模块利用调度系统来统计分析执行情况。
并通过积分和评分体系来综合评估个人的总体状态。

系统可以说是一个个人助理系统，它主要解决三个问题：
* 我的计划(期望)是什么？
* 我要做什么？
* 我做了什么？

该系统是前后端分离的项目，当前项目mulanbay-server为后端API项目，只提供系统的api接口，整个系统必须要同时运行前端才能完整访问。

木兰湾管理系统前端项目：

VUE版本
* 基于Vue的前端(PC端)[mulanbay-ui-vue](https://gitee.com/mulanbay/mulanbay-ui-vue)
* 基于Vue的前端(移动端)[mulanbay-mobile-vue](https://gitee.com/mulanbay/mulanbay-mobile-vue)

Jquery版本(V3.0版本后不再维护，以VUE版本为主)
* 基于Jquery的前端(PC端)[mulanbay-ui-jquery](https://gitee.com/mulanbay/mulanbay-ui-jquery)
* 基于Jquery的前端(移动端)[mulanbay-mobile-jquery](https://gitee.com/mulanbay/mulanbay-mobile-jquery)

[木兰湾项目说明](https://gitee.com/mulanbay)

### 功能简介

* 基于RBAC的用户权限管理
* 支持分布式运行的调度功能
* 基于AHANLP的自然语言学习服务
* 提供消费、锻炼、音乐、阅读、健康、饮食、人生经历等常用模块
* 统一的日志管理及日志流分析
* 提供基于模板化的提醒、计划、图表、行为配置及分析
* 统一的日历管理，提供日历自动新增、完成功能
* 提供磁盘、CPU、内存的监控及报警，并可以自动恢复
* 数据库数据、备份文件自动清理
* 统一及强大的图表统计分析功能
* 基于微信公众号消息、邮件的消息提醒服务
* 基于错误代码的消息发送可配置化
* 基于Hibernate的配置化的查询便捷封装
* 提供可配置的个人积分和评分体系
* 提供多角度的用户行为分析
* 提供词云、相似度、智能问答等分析功能 

### 文档地址

木兰湾文档[https://www.yuque.com/mulanbay/rgvt6k/uy08n4](https://www.yuque.com/mulanbay/rgvt6k/uy08n4)

### 所用技术

* 前端：Vue、Jquery、Element UI、Echarts
* 后端：Spring Boot、Hibernate、Quartz、NLP、Redis & Jwt

| 核心依赖                | 版本          |
| ---------------------- | ------------- |
| Spring Boot            | 2.3.4.RELEASE |
| Hibernate              | 5.4.21.Final  |
| Quartz                 | 2.3.2         |

### 项目结构
``` lua
mulanbay-server
├── mulanbay-business    -- 通用业务类
├── mulanbay-common      -- 公共模块
├── mulanbay-persistent  -- 持久层基于hibernate的封装
├── mulanbay-pms         -- 木兰湾API接口层
├── mulanbay-schedule    -- 调度模块封装
├── mulanbay-web         -- 基于SpringMVC的一些封装

```

### 项目运行与部署

#### 本地运行

``` 
# Step 1：初始化数据库

1. 下载源代码
2. 在mysql中创建数据库，比如:mulanbay_db
3. 初始化数据库,执行mulanbay-pms工程docs目录下的sql文件：mulanbay_init.sql

# Step 2：修改配置文件

1. 在mulanbay-pms/src/main/resources/目录下新建application-local.properties文件，设置本地配置。
   其中Mysql数据库配置、Redis配置为必须配置，如果需要使用微信公众号的消息发送功能，需要配置.
2. 智能客服、词云、商品重复度、饮食重复度等需要用到AHANLP的自然语言处理，需要配置hanlp.properties，ahanlp.properties
  * hanlp.properties文件中需要设置根路径，如：root=D:/ws/AHANLP_base-1.3
  * ahanlp.properties文件中需要设置里面的各个配置项
  * 词云模块需要使用Python的wordcloud插件，安装命令：
    pip3 install wordcloud -i https://pypi.tuna.tsinghua.edu.cn/simple
  * NLP所需要的ahanlpData文件包，请到百度网盘下载：（链接：https://pan.baidu.com/s/101vlabHehB71va82G9U-9A 提取码：yaqm ）
    或者直接去原作者项目处下载：https://github.com/jsksxs360/AHANLP

# Step 3：打包&运行

1. 开发环境
  运行mulanbay-pms子工程下的cn.mulanbay.pms.web.Application

2. 正式环境
  * 进入到mulanbay-server目录，运行mvn clean package
  * 运行mulanbay-pms/target下的mulanbay-pms-3.0.jar文件

    `nohup java -jar mulanbay-pms/target/mulanbay-pms-3.0.jar &`

后端项目默认的端口是：8080

```

#### docker-compose运行

```
docker-compose up -d
```

### 环境变量

| 变量名 | 默认值 | 说明 |
| ----- | ----- | -----|
| PYTHON_PATH | /usr/bin/python3 |  |
| AHANLP_PATH | /AHANLP_base-1.3 | |
| AHANLP_WORD2VECTOR_MODEL | /AHANLP_base-1.3/data/model/Google_word2vec_zhwiki1710_300d.bin | |
| PICTURE_BASEURL | http://localhost:99/avatar | 图片地址前缀 |
| SYS_MOBILE_BASEURL | http://localhost:94 | 移动端基础路径 |
| SYS_WORDCLOUD_PIC_PATH | /uploads/wordcloud | 词云的图片目录 |
| SCHEDULE_ENABLE | true | 调度配置是否启动 |
| SCHEDULE_SUPPORT_DISTRI | false | 调度配置是否支持分布式任务 |
| SYS_BUYRECORD_STAT_USESTATE | true | 购买记录统计中是否启用商品类型里的可统计字段（如果启用了，则增加消费记录统计的复杂度） |
| SYS_PAGESEARCH_MAXPAGESIZE | 100 | 分页每页最大数 |
| REWORD_CREATE_BY_TEMPLATE_PERPOINTS | 10 | 根据模板新增的增加积分数（单个） |
| MYSQL_HOST | 127.0.0.1 |  |
| MYSQL_DATABASE | mulanbay |  |
| MYSQL_PORT | 3306 |  |
| MYSQL_USER | root |  |
| MYSQL_PWD | root |  |
| SECURITY_LOGIN_MAXFAIL | 5 | 最大登录失败次数 |
| SECURITY_PWD_SALT | abc123456 | MD5密码的盐值 |
| SECURITY_TOKEN_SECRET | abcdefghijklmnopqrstuvwxyz | 令牌密钥 |
| SECURITY_TOKEN_EXPIRE_TIME | 1800 | 令牌有效期（默认30分钟） |
| SECURITY_TOKEN_VERIFY_TIME | 1200 | 验证令牌有效期（默认20分钟） |
| MAIL_USERNAME |  | 邮件发件地址 |
| MAIL_PWD |  | qq邮箱采用授权码发送，非邮箱密码 |
| MAIL_SERVER_HOST |  |  |
| MAIL_SERVER_PORT |  |  |
| REDIS_SERVER_IP | 127.0.0.1 |  |
| REDIS_SERVER_PORT | 6379 |  |
| REDIS_SERVER_DB | 10 |  |
| REDIS_SERVER_PWD | | |
| REDIS_DEFAULT_EXPIRATION | 300 |  |
| WX_APPID |  |  |
| WX_SECRET |  |  |
| WX_TOKEN |  |  |
| WX_USER_MESSAGE_TEMPLATE_ID |  | 微信消息发送模板编号 |
| WX_ACCESS_AUTH_REDIRECT_URL |  |  |
| WX_OAURL |  | 公众号地址 |
| WX_OA_QRURL | /uploads/wechatqr.jpg | 公众号二维码本地地址 |
| NOTIFY_MESSAGE_EXPECT_SEND_TIME | 09:00 | 默认的消息提醒发送时间 |
| NOTIFY_VALIDATE_ERROR | false | 是否要通知表单验证类的消息提醒 |
| NOTIFY_MESSAGE_SEND_MAXFAIL | 3 | 消息最大发送失败次数 |
| NOTIFY_MESSAGE_SEND_LOCK | false | 消息发送是否需要锁定 |
| SHARES_WITH_CACHE | true | 股票相关 |
| SHARES_CACHE_SECONDS | 5 | 股票相关 |
| SHARES_MONITOR_TASKTRIGGERID | 30 | 股票价格监控的调度ID（根据实际来写） |
| SHARES_STAT_TASKTRIGGERID | 31 | 股票统计的调度ID（根据实际来写） |
| DATE_FORMAT | yyyy-MM-dd HH:mm:ss | 时间的json格式 |
| TIMEZONE | GMT+8 | 解决spring json解析相差8个小时的时区问题（针对POST方法，且时间为yyyy-MM-dd模式） |

### 挂载目录

/AHANLP_base-1.3

/AHANLP_base-1.3/data/model/Google_word2vec_zhwiki1710_300d.bin

/uploads/avatar

/uploads/wordcloud

/uploads/wechatqr.jpg

### 软件要求

| 软件                    | 版本          |
| ---------------------- | ------------- |
| JDK                    | 1.8+          |
| Nginx                  | 1.17+         |
| Redis                  | 6.0+          |
| Mysql                  | 8.0+          |

### 硬件要求
 内存4G+
 
## 系统架构

### 系统模块
![系统模块](https://images.gitee.com/uploads/images/2020/1116/153208_763552c9_352331.png "系统模块.png")

### 系统结构
![系统结构](https://images.gitee.com/uploads/images/2020/1116/153229_0e719916_352331.png "系统结构.png")

### 业务流程
![业务流程](https://images.gitee.com/uploads/images/2020/1116/153249_202177a7_352331.png "业务流程.png")

### 图表分类
![图表分类](https://images.gitee.com/uploads/images/2020/1116/153330_e6cdf020_352331.png "图表统计.png")

## 在线演示
暂未提供

## 技术交流
* QQ群：562502224

## 参考/集成项目

木兰湾管理系统参考、集成了一些项目，有些功能自己也只是一个搬运工，先感谢大家的开源。

* Jquery版本前端(移动)UI组件：[weui](https://gitee.com/yoby/weui)
* Jquery版本前端(移动)Tag方案：[Jquey tagEditor](https://goodies.pixabay.com/jquery/tag-editor/demo.html)
* 前端(PC)日历组件：[tui-calendar](https://github.com/nhn/tui.calendar)
* 自然语言学习：[AHANLP](https://github.com/jsksxs360/AHANLP)
* Vue版本前端(PC)：[RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue)，[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)
* Vue版本前端(PC)自动表单生成：[form-create](http://www.form-create.com/v2/element-ui/)
* 智能客服UI组件：[LiteWebChat_Frame](https://gitee.com/lyk003/LiteWebChat_Frame)

## Q&A
* Q：用户计划的时间线统计图没有数据？

  A: 计划的时间线数据需要日终调度程序来统计的，第二天凌晨后才会有当天的数据。
  
* Q：我的日历里面没有数据？

  A: 自动生成的日历也是需要日终调度程序来统计的，第二天凌晨后才会有数据。
  不过日历也可以手动创建，但是手动日历无法自动关闭。
    
## 项目展望

## 使用&授权

* 源代码100%开源
* 个人使用完全免费
* 公司内部使用免费，对外商业运营则需要授权
  
## 项目截图

### 基于Vue的PC端
<table>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/153409_2b13c53b_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/153455_ed3c4b64_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/153513_6448ba23_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/153529_a818b611_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/153547_c18e229c_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/153603_da9a31f2_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/1215/175924_d4f8a9ac_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/1215/175948_a77fa716_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163144_7da250d2_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163209_772602c4_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163237_935cfb98_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163258_4e3143a0_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163342_5eae1415_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163359_69c7a4db_352331.png"/></td>
    </tr>
</table>

### 基于Vue的移动端
<table>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0225/134154_6c5f78ad_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0225/134216_58f63cf3_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0225/134236_7c81bf14_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163914_91f69093_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0225/134321_56b72e90_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0225/134338_e1766f98_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163524_9286315d_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163541_d2c998a4_352331.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163618_3737b37e_352331.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2021/0324/163637_7f904b6c_352331.png"/></td>
    </tr>
</table>

### 微信公众号消息推送

<table>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/154050_af85354a_352331.jpeg"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/154104_31b29a07_352331.jpeg"/></td>
    </tr>

</table>
