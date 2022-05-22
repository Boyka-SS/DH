本次Android 主要做了两件事：

智辅安行App

本系统借助智能手表物理硬件，可以测量佩戴者的体温，心率，血压数据，并且手表将所测量的数据上传兔盯云平台，在这里，Android主要做了两件事：数据获取+数据展示

<img src="C:\Users\HP\Desktop\未命名文件.png" alt="未命名文件" style="zoom:50%;" />

1. 数据获取

   如上图，手表所测数据均上传到兔盯云健康数据平台，本地Android采用眼下最火热的网络请求框架——Okhttp3+Retrofit

   当然项目的开始时候，因为OKGO技术文档更加通俗易懂，并没有选择Retrofit技术，但是后期在请求数据时，发现需要对每个请求进行拦截添加身份识别token，OKGO没有简单便捷优雅的处理方式，使用OKGO写出的代码又过于冗余复杂，并且有时候难以理解，所以干脆放弃了OKGO，换上Retrofit重构了项目网络请求的内容。

   开发的时候，并没有上述说的那么简单，而且在重构替换OKGO的时候，为了方便观察网络请求结果，添加了LoggingInterceptor依赖，拦截request head 和response body，并将它们打印。

   实际编码时，遇到诸多困难，要在使用Java语言请求网络的经验为零的情况下，面对Retrofit技术束手无策。Retrofit官网介绍的代码片段过于零碎独立，整体的使用流程很不明确。但好在，存在如 简书、掘金、CSDN以及博客园等各种技术网站上，有诸多前辈的学习笔记。通过翻阅大量技术博客，并在github网站上，瞻仰了大佬的retrofit学习demo，最终解决了retrofit使用问题。对于请求回来的数据，使用GSON解析，搭配Android studio的GsonFormatPlus插件，快速创建本地的pojo对象。

   > 
   >
   > 在本项目中，com.jinke.driverhealth.data包下，除了远端数据还有一些本地数据，这里暂时先说远端数据获取步骤：
   >
   > 1. ServiceCreator.java
   >
   >    在本类中，存在url属性，通过ServiceCreator构造方法，传入每次网络请求的BaseUrl，然后需要定义一个create方法，初始化多个不同的OkHttpClient，然后借助该client创建专属retrofit对象。
   >
   > 2. api+beans+worker

   

2. 数据展示

   对于数据展示，使用的SmartRefreshLayout技术，对所得数据进行陈列展示。SmartRefreshLayout是对RecyclerView更好的封装。其中就包括，条目点击事件，侧滑点击事件，上拉或下滑刷新动作，都有简单方便的封装。对于SmartRefreshLayout的操作更多可以在官网查看，并且官网提高了友好的代码demo

   

**权限申请问题**

Android 将权限分为不同的类型，包括安装时权限、运行时权限和特殊权限。每种权限类型都指明了当系统授予应用该权限后，应用可以访问的受限数据范围以及应用可以执行的受限操作范围。

Android6.0以后，对于安装时权限需要请求用户同意，本App对于通讯录获取，修改，打电话等受限操作都需要动态申请

**高德定位问题**

项目中引入高德定位SDK，这里遇到了app签名打包问题，在app使用定位的过程中，总是会报Invalid User Name问题，查阅高德交流平台，获知是app签名问题，于是翻了10几篇博客，最终成功打包签名APK，解决bug。

**deprecated use api**

在项目开发中，在Android中，遇到了很多的过时的api，典型的有startActivityForResult。当我第一次发现该API废弃时，顺着源码的注释结合网上博客，找到了该api的替换方案——Activity Result API。讲一些API的接口统一化，实现特定功能的时候，能够变得非常简单。
