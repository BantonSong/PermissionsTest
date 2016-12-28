package com.start.permissiontest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra("permissionList");
        requestPermission(permissions);
    }

    public static void request(Activity activty, String[] permissions) {
        Intent intent = new Intent(activty, PermissionActivity.class);
        intent.putExtra("permissionList", permissions);
        activty.startActivityForResult(intent, 1000);
    }

    public void requestPermission(String[] permissions) {
        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PermissionChecker.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
                Toast.makeText(this, permission + "权限已赋予！", Toast.LENGTH_SHORT).show();
            }
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
                Intent intent = new Intent();
                intent.putExtra("permissions", permissions);
                intent.putExtra("grantResults", grantResults);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
