# PermissionsTest
根据郭霖在CSDN直播中讲解，实现了Android6.0以上的运行时权限如何运用。
主要有以下几种方式：
1、直接在Acitivity中检查、请求权限、回调；
2、通过透明Activity的方式完成封装，在透明Activity中进行检查、请求、并回调到请求的Activity中；
3、通过透明的、不占任何地方的Fragment的方式进行封装；
4、把所有方法提到BaseActivity中，其它Activity继承BaseActivity；
5、使用开源库RxPermissions（https://github.com/BantonSong/RxPermissions），他们封装的不错，不过的集成RxJava。
