
## 项目介绍
木兰湾是用于管理个人消费、锻炼、音乐、阅读、健康、饮食、人生经历等各个衣食住行信息的系统，通过提醒、计划模块利用调度系统来统计分析执行情况。
并通过积分和评分体系来综合评估个人的总体状态。

系统可以说是一个个人助理系统，它主要解决三个问题：
* 我的计划(期望)是什么？
* 我要做什么？
* 我做了什么？

基于以上三个问题，我们是否可以去思考以下两个问题：
* 我是怎么样的人？（用户画像）
* 我将会怎么样？ （机器学习）

 **基于SpringBoot3和Vue3新版的项目地址：** 

后端项目：
* 服务器端[mulanbay-server3](https://gitee.com/mulanbay/mulanbay-server3),发行版:[releases](https://gitee.com/mulanbay/mulanbay-server3/releases)

前端项目：
* PC端[mulanbay-ui-vue3](https://gitee.com/mulanbay/mulanbay-ui-vue3),发行版:[releases](https://gitee.com/mulanbay/mulanbay-ui-vue3/releases)

 **该项目基于SpringBoot2和Vue2** 

该系统是前后端分离的项目，当前项目mulanbay-server为后端API项目，只提供系统的api接口，整个系统必须要同时运行前端才能完整访问。

木兰湾算法端项目：
* 基于sklearn的机器学习(python)[mulanbay-sklearn](https://gitee.com/mulanbay/mulanbay-sklearn)

(对于数据预测，mulanbay-sklearn负责算法，生成pmml模型文件，java端mulanbay-server通过jpmml库加载模型文件对业务数据进行预测)

木兰湾前端项目：

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
* 基于sklearn的机器学习对一些数据进行预测

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
├── mulanbay-ai          -- 机器学习模块，数据预测
├── mulanbay-business    -- 通用业务类
├── mulanbay-common      -- 公共模块
├── mulanbay-persistent  -- 持久层基于hibernate的封装
├── mulanbay-pms         -- 木兰湾API接口层
├── mulanbay-schedule    -- 调度模块封装
├── mulanbay-web         -- 基于SpringMVC的一些封装

```

### 项目运行与部署
``` 
# Step 1：初始化数据库

1. 下载源代码
2. 在mysql中创建数据库，比如:mulanbay_db
3. 初始化数据库,执行mulanbay-pms工程docs目录下的sql文件：mulanbay_init.sql

注意：
* mulanbay_init.sql里面的数据只有原始的空数据，默认各个初始化配置数据需要自己手动添加(因为每个人的需求不一样)。
* 如果想快速的使用系统可以在菜单“权限管理-用户管理”，选择对该用户(比如mulanbay用户)进行"初始化数据"（初始化数据是以root用户的初始配置为模板，所以不要针对root用户进行初始化数据或格式化数据）.
* 系统有两个用户root,mulanbay,都包括所有权限，推荐使用mulanbay用户登录进行业务逻辑操作
* v3.4版本后不再提供mulanbay_init_data.sql文件。

附1：数据库导入方法：
1. 进入mysql终端
mysql -u root -p
2. 创建数据库
create database mulanbay_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
3. 选择数据库
use mulanbay_db
4. 导入数据库
source /xx/xx/xx/mulanbay_init.sql(数据库文件绝对路径)

附2：数据库升级方法：
如果你已经安装过本系统，已经有自己的一些业务数据，不想重新导入数据库，可以采用升级方法：
1. 执行数据库表结构更新语句
source /xx/xx/xx/mulanbay_change.sql
2. 执行数据库配置数据更新语句
source /xx/xx/xx/mulanbay_config_update.sql

# Step 2：修改配置文件

1. 在mulanbay-pms/src/main/resources/目录下复制application-local-template.properties文件并重命名为application-local.properties，设置本地配置。
   其中Mysql数据库配置、Redis配置为必须配置，如果需要使用微信公众号的消息发送功能，需要配置.
2. 智能客服、词云、商品重复度、饮食重复度等需要用到AHANLP的自然语言处理，需要配置hanlp.properties，ahanlp.properties
  * hanlp.properties文件中需要设置根路径，如：root=D:/ws/AHANLP_base-1.3
  * ahanlp.properties文件中需要设置里面的各个配置项
  * 词云模块需要使用Python的wordcloud插件（3.0版本及以后不需要，词云修改为echarts实现），安装命令：
    pip3 install wordcloud -i https://pypi.tuna.tsinghua.edu.cn/simple
  * NLP所需要的ahanlpData文件包，请到百度网盘下载：（链接: https://pan.baidu.com/s/1demdX1GjhMiJqM58bJzriQ 提取码: gqcs ）
    或者直接去原作者项目处下载：https://github.com/jsksxs360/AHANLP/blob/master/github/w2v.md （请使用V20210720版本）
    说明：
    【模型】Google_word2vec_zhwiki210720_300d.bin
    【语料】zhwiki_210720_preprocessed.simplied.zip

# Step 3：打包&运行

1. 开发环境
  运行mulanbay-pms子工程下的cn.mulanbay.pms.web.Application

2. 正式环境
  * 进入到mulanbay-server目录，运行mvn clean package
  * 运行mulanbay-pms/target下的mulanbay-pms-3.0.jar文件

后端项目默认的端口是：8080，项目路径为api，完整的路径地址：http://localhost:8080/api/

# Step 4：用户数据初始化

系统默认包含两个用户admin和mulanbay，密码都是123456.admin用户主要是维护使用，一般以mulanbay用户登录。

mulanbay用户默认情况下是没有任何业务数据的，可以在"权限管理/用户管理"里对mulanbay用户进行"初始化数据"，系统可以显示mulanbay用户基础的配置数据。

# Step 5：机器学习模型文件pmml导入
如果想要使用数据预测功能，需要从mulanbay-sklearn项目中model文件夹下拷贝模型文件到服务器目录，服务器目录配置项为ml.pmml.modelPath。
模型文件也可以通过VUE版本的PC端页面进行上传。


```

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

木兰湾参考、集成了一些项目，有些功能自己也只是一个搬运工，先感谢大家的开源。

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
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/154050_af85354a_352331.jpeg "Screenshot_20201015_150843_com.tencent.mm.jpg"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/1116/154104_31b29a07_352331.jpeg "Screenshot_20201015_150911_com.tencent.mm.jpg"/></td>
    </tr>

</table>

### 鸣谢
谢谢JetBrains公司一直以来对木兰湾项目的支持，提供了全系列产品的免费license。

<img src="https://foruda.gitee.com/images/1712229322943756523/d28d99c2_352331.png" width="100px" height="100px">

JetBrains开源支持计划：https://jb.gg/OpenSourceSupport.
