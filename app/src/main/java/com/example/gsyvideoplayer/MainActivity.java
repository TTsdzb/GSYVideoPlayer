package com.example.gsyvideoplayer;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gsyvideoplayer.databinding.ActivityMainBinding;
import com.example.gsyvideoplayer.utils.JumpUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.player.PlayerFactory;
import com.shuyu.gsyvideoplayer.player.SystemPlayerManager;
import com.shuyu.gsyvideoplayer.utils.Debuger;

import permissions.dispatcher.PermissionUtils;
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ActivityMainBinding binding;

    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View rootView = binding.getRoot();
        setContentView(rootView);

        Debuger.enable();

        binding.clearCache.setOnClickListener(this);
        binding.inputType.setOnClickListener(this);
        binding.changeCore.setOnClickListener(this);


        boolean hadPermission = PermissionUtils.hasSelfPermissions(this, permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hadPermission) {
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, 1110);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean sdPermissionResult = PermissionUtils.verifyPermissions(grantResults);
        if (!sdPermissionResult) {
            Toast.makeText(this, "没获取到sd卡权限，无法播放本地视频哦", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View view) {
        Log.e("################# ","################# ");
        switch (view.getId()) {
            case R.id.input_type:
                JumpUtils.gotoInput(this);
                break;
            case R.id.change_core:
                i += 1;
                if (i % 3 == 0) {
                    PlayerFactory.setPlayManager(IjkPlayerManager.class);
                    binding.changeCore.setText("IJK 内核");
                } else if (i % 3 == 1) {
                    PlayerFactory.setPlayManager(Exo2PlayerManager.class);
                    binding.changeCore.setText("EXO 内核");
                } else if (i % 3 == 2) {
                    PlayerFactory.setPlayManager(SystemPlayerManager.class);
                    binding.changeCore.setText("系统 内核");
                }
                break;
            case R.id.clear_cache:
                //清理缓存
                GSYVideoManager.instance().clearAllDefaultCache(MainActivity.this);
                break;
        }
    }
}
