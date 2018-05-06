# GXCW Android App

基于百度地图sdk开发包的lbs应用，本人的毕业设计

共享车位Android客户端，以摩拜单车设计为参考原型，实现定位，导航，用户登陆注册，查看历史记录等。

项目的后台服务器只能在本地运行，本demo着重实现用户界面，逻辑交互；大部分用户信息，车位信息，历史信息使用静态数据，因此仅供参考

Material
======================
模块 | 描述 | 效果图
--- | --- | ---
主界面 | [Apache License V2](https://www.apache.org/licenses/LICENSE-2.0) |  <img src="/Screenshots/微信图片_20180414010808.jpg" width="39%"> <img src="/Screenshots/地图.jif" width="39%"> 
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

- [x] 三段弹出式BottomSheet
- [x] 动态显示车位图片
- [x] 3d地图
- [x] 车位信息动态背景
- [x] 地图动画效果
- [x] GitHub项目链接
- [x] 应用设置
- [x] 开发者信息


补充说明：

- 依赖百度定位api，需要打开gps来获取更精准的定位数据，地图的加载需要消耗数据流量
- 后台服务器位于本地，未上传到云端，用户暂时不可以实现登录及注册，因此设置了一个上帝账号：tesla，密码随意
- 车位信息在后台服务器中，app只设置了2个静态车位信息，可长按地图添加车位
- 分享功能暂未实现，只实现界面布局

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
