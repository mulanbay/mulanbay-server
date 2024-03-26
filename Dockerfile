FROM openjdk:8-jdk-alpine
# 环境变量
ENV PYTHON_PATH="/usr/bin/python"
ENV AHANLP_PATH="/AHANLP_base-1.3"
ENV AHANLP_WORD2VECTOR_MODEL="/AHANLP_base-1.3/data/model/Google_word2vec_zhwiki1710_300d.bin"

# 系统
ENV PICTURE_BASEURL="http://localhost:99/avatar"
ENV SYS_MOBILE_BASEURL="http://localhost:94"
ENV SYS_WORDCLOUD_PIC_PATH="/uploads/wordcloud"
ENV SYS_BUYRECORD_STAT_USESTATE=true
ENV SYS_PAGESEARCH_MAXPAGESIZE=100
ENV SCHEDULE_ENABLE=true
ENV SCHEDULE_SUPPORT_DISTRI=false
ENV REWORD_CREATE_BY_TEMPLATE_PERPOINTS=10

# 数据库
ENV MYSQL_HOST="127.0.0.1"
ENV MYSQL_DATABASE=mulanbay
ENV MYSQL_PORT=3306
ENV MYSQL_USER=root
ENV MYSQL_PWD=root

# 安全
ENV SECURITY_LOGIN_MAXFAIL=5
ENV SECURITY_PWD_SALT=abc123456
ENV SECURITY_TOKEN_SECRET=abcdefghijklmnopqrstuvwxyz
ENV SECURITY_TOKEN_EXPIRE_TIME=1800
ENV SECURITY_TOKEN_VERIFY_TIME=1200

# 邮箱
ENV MAIL_USERNAME=""
ENV MAIL_PWD=""
ENV MAIL_SERVER_HOST=""
ENV MAIL_SERVER_PORT=""

# Redis
ENV REDIS_SERVER_IP="127.0.0.1"
ENV REDIS_SERVER_PORT=6379
ENV REDIS_SERVER_DB=10
ENV REDIS_SERVER_PWD= 
ENV REDIS_DEFAULT_EXPIRATION=300

# 微信公众号相关
ENV WX_APPID=""
ENV WX_SECRET=""
ENV WX_TOKEN=""
ENV WX_USER_MESSAGE_TEMPLATE_ID=""
ENV WX_ACCESS_AUTH_REDIRECT_URL=""
ENV WX_OAURL=""
ENV WX_OA_QRURL="/uploads/wechatqr.jpg"

# 消息提醒
ENV NOTIFY_MESSAGE_EXPECT_SEND_TIME="09:00"
ENV NOTIFY_VALIDATE_ERROR=false
ENV NOTIFY_MESSAGE_SEND_MAXFAIL=3
ENV NOTIFY_MESSAGE_SEND_LOCK=false

# 股票相关
ENV SHARES_WITH_CACHE=true
ENV SHARES_CACHE_SECONDS=5
ENV SHARES_MONITOR_TASKTRIGGERID=30
ENV SHARES_STAT_TASKTRIGGERID=31

# 日期格式
ENV DATE_FORMAT="yyyy-MM-dd HH:mm:ss"
ENV TIMEZONE="GMT+8"

# 设置阿里云的镜像源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
    apk update

# 安装python
RUN apk add --no-cache python3 \
    && export PYTHON_PATH=$(which python3) \
    && ln -s $(which python3) /usr/bin/python \
    && echo "Python 3 path: $PYTHON_PATH" \
    && ln -s $(which pip3) /usr/bin/pip

# 可选：升级 pip
# RUN apk add --no-cache python3-setuptools \
#     && easy_install-3.6 pip
RUN pip install --upgrade pip

# 安装词云
# RUN pip install wordcloud -i https://pypi.tuna.tsinghua.edu.cn/simple

# 安装 fontconfig 和 freetype
RUN apk add --no-cache fontconfig freetype

# 创建字体目录的链接
RUN mkdir -p /usr/share/fonts/jdk-fonts \
    && ln -s /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback /usr/share/fonts/jdk-fonts/fallback

# 添加arial、Courier字体
COPY fonts/truetype/* /usr/lib/jvm/java-1.8-openjdk/jre/lib/fonts/fallback

# 更新字体缓存
RUN fc-cache -fv

# 设置环境变量，使得Java应用程序能够找到字体
ENV JAVA_FONTS_DIR=/usr/share/fonts/jdk-fonts

# 添加程序包
ADD mulanbay-pms/target/mulanbay-pms-3.0.jar /mulanbay/mulanbay-pms-3.0.jar

# 挂载目录
VOLUME /AHANLP_base-1.3
VOLUME /uploads/avatar
VOLUME /uploads/wordcloud
VOLUME /uploads/wechatqr.jpg

# 暴露端口
EXPOSE 8080

# 指定工作目录
WORKDIR /mulanbay

# 指定启动命令
CMD java -jar mulanbay-pms-3.0.jar ; sleep 999999d