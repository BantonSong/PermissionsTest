package com.start.permissiontest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PermissionFragment extends Fragment {
    private RequestPermissionsListner listner;

    public void setListner(RequestPermissionsListner listner) {
        this.listner = listner;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] permissions = getArguments().getStringArray("permissions");
        requestPermission(permissions);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static void request(Activity activty, String[] permissions, RequestPermissionsListner listner) {
        //步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager = activty.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //步骤二：用add()方法加上Fragment的对象rightFragment
        PermissionFragment fragment = new PermissionFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("permissions", permissions);
        fragment.setArguments(bundle);
        transaction.add(fragment, "PermissionFragment");

        fragment.setListner(listner);

        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermission(String[] permissions) {
        List<String> permissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PermissionChecker.PERMISSION_GRANTED) {
                permissionList.add(permission);
            } else {
                Toast.makeText(getActivity(), permission + "权限已赋予！", Toast.LENGTH_SHORT).show();
            }
        }

        if (!permissionList.isEmpty()) {
            requestPermissions(permissionList.toArray(new String[]{}), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                listner.onResult(permissions, grantResults);
                break;
        }
    }
}
