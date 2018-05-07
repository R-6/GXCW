# 共享车位 Android App

基于百度地图sdk开发包的lbs应用，本人的毕业设计

共享车位Android客户端，以摩拜单车设计为参考原型，实现定位，导航，用户登陆注册，查看历史记录等。

项目的后台服务器只能在本地运行，本demo着重实现用户界面，逻辑交互；大部分用户信息，车位信息，历史信息使用静态数据，因此仅供参考

Material
======================
模块 | 描述 | 效果图
--- | --- | ---
主界面 | 地图上选择车位，弹出详细信息栏 | <img src="/Screenshots/地图1.gif" width="49%"> <img src="/Screenshots/地图2.gif" width="49%"> 
登录&注册 |用户登录注册及修改个人信息界面|<img src="/Screenshots/登录.gif" width="32%"> <img src="/Screenshots/注册.gif" width="32%"> <img src="/Screenshots/修改个人信息.gif" width="32%"> 
我的车位 |查看及编辑我的车位信息|<img src="/Screenshots/修改车位信息.gif" width="32%"> <img src="/Screenshots/滑动查看车位.gif" width="32%"><img src="/Screenshots/个人车位修改.gif" width="32%"> 

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


测试

```
这个开发者很懒，暂时没写下什么测试……
```

## License
[MIT](LICENSE)
# GXCW
