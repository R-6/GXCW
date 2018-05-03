This code is no longer being maintained.

项目已经不再维护，开源的目的更多是给新手一个参考 Demo

# GXCW Android App
共享车位Android客户端，以摩拜单车设计为参考原型，实现定位，导航，用户登陆注册，查看历史记录等。


**预览**：
!["预览"](https://github.com/R-6/GXCW/raw/master/Screenshots/微信图片_20180414010808.jpg)

**主要功能**：

- [x] 用户模块
    - [x] 登录
    - [x] 注册
    - [x] 修改个人信息
- [x] 地图模块
    - [x] 实时定位
    - [x] 附近车位
    - [x] 动态导航
- [x] 车位管理
    - [x] 查看我的车位
    - [x] 修改车位信息
- [x] 历史停车记录
    - [x] 查看历史记录
- [x] 分享app

**其他**：

- [x] 天气查询
- [x] 常用电话
- [x] 公交路线
- [x] 校历查询
- [x] 学校新闻
- [x] 四六级查询
- [x] 明信片查询
- [x] 随机音乐
- [x] 气象雷达
- [x] 网页游戏
- [x] 莞香广科论坛
- [x] 客服留言
- [x] 合作信息

补充说明：

- 依赖外部 API 的操作使用客服接口异步回复，需要通过微信认证
- 正方教务系统与图书馆查询均使用模拟登陆
- 字典、正则匹配关键词，避免过多的条件语句嵌套
- 场景状态，支持上下文回复
- 全局保存、刷新微信 access_token
- 关键词兼容繁体、全角空格
- 长文本的回复使用图文信息进行排版
- 前端 UI 使用 WeUI 统一风格

## 快速开始

安装 MySQL、Redis
```
略
```

安装依赖

```
pip install -r requirements.txt
```

创建配置文件
```
cp instance/config.example instance/config.py
vi instance/config.py
```

初始化数据库

```
# into Python shell
>>> from main.models import db
>>> db.create_all()
```

运行

```
python run.py
```

运行队列任务

```
celery -A main.celery worker --beat -l info
```

测试

```
这个开发者很懒，暂时没写下什么测试……
```

部署

```
# using gunicorn
pip install gunicorn

# run
gunicorn -w 3 run:app -p wechat.pid -b 127.0.0.1:8000 -D --log-level warning --error-logfile gunicorn-error.log

# reload
kill -HUP `cat wechat.pid`
```

## License
[MIT](LICENSE)
# GXCW
这是一个基于百度地图api的lbs应用"共享车位"，页面布局参考摩拜单车,是本人的大学毕业设计
项目使用了QMUI团队的UI组件
