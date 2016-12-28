package com.start.permissiontest;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 运行时权限注意：
 * 1、必须在Manifest文件中声明
 * 2、targetSdkVersion必须在23之上
 * 3、权限在低版本上已经赋予，则升级后默认赋予
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnClick4(View view) {
        RxPermissions rxPermissions = new RxPermissions(this);

        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS
        };
        rxPermissions.requestEach(permissions)
                .subscribe(
                        new Action1<Permission>() {
                            @Override
                            public void call(Permission permission) {
                                if (permission.granted) {
                                    Toast.makeText(MainActivity.this,
                                            "Granted permission",
                                            Toast.LENGTH_SHORT).show();
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // Denied permission without ask never again
                                    Toast.makeText(MainActivity.this,
                                            "Denied permission without ask never again",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // Denied permission with ask never again
                                    // Need to go to the settings
                                    Toast.makeText(MainActivity.this,
                                            "Permission denied, can't enable the camera",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable t) {
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                            }
                        });
    }

    public void btnClick3(View view) {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS
        };
        requestBasePermissions(permissions);
    }

    @Override
    public void onPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onPermissionsResult(permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PermissionChecker.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, permissions[i] + "权限已赋予！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, permissions[i] + "权限已拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnClick2(View view) {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS
        };
        RequestPermissionsListner listner = new RequestPermissionsListner() {
            @Override
            public void onResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PermissionChecker.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this, permissions[i] + "权限已赋予！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, permissions[i] + "权限已拒绝！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        PermissionFragment.request(this, permissions, listner);
    }

    public void btnClick1(View view) {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS
        };

        PermissionActivity.request(this, permissions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String[] permissions = data.getStringArrayExtra("permissions");
        int[] grantResults = data.getIntArrayExtra("grantResults");
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PermissionChecker.PERMISSION_GRANTED) {
                Toast.makeText(this, permissions[i] + "权限已赋予！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, permissions[i] + "权限已拒绝！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void btnClick(View view) {
        List<String> permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        } else {
            Toast.makeText(this, "CAMERA权限已赋予！", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PermissionChecker.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CALL_PHONE);
        } else {
            Toast.makeText(this, "CALL_PHONE权限已赋予！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:123456"));
            startActivity(intent);
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[]{}), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PermissionChecker.PERMISSION_GRANTED) {
                        Toast.makeText(this, permissions[i] + "权限已赋予！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, permissions[i] + "权限已拒绝！", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
