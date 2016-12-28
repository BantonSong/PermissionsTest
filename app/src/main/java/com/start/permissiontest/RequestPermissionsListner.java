package com.start.permissiontest;

import android.support.annotation.NonNull;

public interface RequestPermissionsListner {
    void onResult(@NonNull String[] permissions, @NonNull int[] grantResults);
}
