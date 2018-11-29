# 用Springboot实现一个仿天猫项目
## 介绍
从[How2J的Java教程](http://how2j.cn)模仿学习，主要为了巩固Java的知识点和框架的使用。
主要是偏后端学习+前端js简单使用，前端界面直接用现成的。
## 使用
由于项目是基于springboot框架，也就是maven风格，所以导入项目直接打开pom.xml即可。并通过入口类SpringbootApplication启动(内置tomcat)。
## 技术简述
### 前后端技术
|功能|使用|
| ------------- |:-------------:|
|核心框架|springboot+springmvc|
|数据库|mysql |
|持久层框架| mybatis|
|JS|vue+jquery+axios|

`在后台开发的时候在ssm教程上尝试使用springboot，到前台开发时更新了springboot教程。所以前台部分新增Thymeleaf,shiro,redis等。`

### mybatis插件
|工具|功能|介绍|
| ------------- |-------------|-------------|
|PageHelper|分页|PageHelper是一款犀利的Mybatis分页插件，使用了这个插件之后，分页开发起来更加简单容易。|
|Mybatis Generator|重构|用逆向工程的方式，首先保证数据库里有表，然后通过Mybatis Generator生成pojo, mapper和xml。 可以节约大家的时间，提高开发效率，降低出错几率。|

## 模块及功能
* 1.后台(finished)  
* 1.1 分类管理
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E5%88%86%E7%B1%BB%E7%AE%A1%E7%90%86.png)<br>
* 1.1.1 属性管理  
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E5%B1%9E%E6%80%A7%E7%AE%A1%E7%90%86.png)<br>
* 1.1.2 产品管理
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E4%BA%A7%E5%93%81%E7%AE%A1%E7%90%86.png)<br>
* 1.2 用户管理  
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86.png)<br>
* 1.3 订单管理
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E8%AE%A2%E5%8D%95%E7%AE%A1%E7%90%86.png)<br>

* 2.前台(ing)
* 2.1 首页
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E9%A6%96%E9%A1%B5.png)<br>
* 2.2 注册页
![avatar](https://github.com/GaryLeeeee/tmall_springboot/blob/master/%E6%95%88%E6%9E%9C%E5%9B%BE/%E6%B3%A8%E5%86%8C%E9%A1%B5.png)<br>






