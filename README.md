# PermissionsTest
   <br/>
根据郭霖在CSDN直播中讲解，实现了Android6.0以上的运行时权限如何运用。
   <br/>
主要有以下几种方式：
   <br/>
1、直接在Acitivity中检查、请求权限、回调；
   <br/>
2、通过透明Activity的方式完成封装，在透明Activity中进行检查、请求、并回调到请求的Activity中；
   <br/>
3、通过透明的、不占任何地方的Fragment的方式进行封装；
   <br/>
4、把所有方法提到BaseActivity中，其它Activity继承BaseActivity；
   <br/>
5、使用开源库[RxPermissions](https://github.com/BantonSong/RxPermissions)，他们封装的不错，不过得集成RxJava。
   <br/>
