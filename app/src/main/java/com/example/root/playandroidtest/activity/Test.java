package com.example.root.playandroidtest.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;

/**
 * Created by Root on 2018/3/21.
 */

public class Test extends AppCompatActivity {

    private Button test_bottom;
    //有关底部菜单的控件初始化
    private Dialog mCameraDialog;
    private CardView btn_open_camera;
    private CardView btn_choose_img;
    private CardView btn_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        test_bottom = (Button) findViewById(R.id.test_bottom);
//        test_bottom.setOnClickListener(this);
        test_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
////            case R.id.test_bottom:
////                //弹出对话框
////                setDialog();
////                break;
//            case R.id.btn_choose_img:
//                //选择照片按钮
//                Toast.makeText(this, "请选择照片", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_open_camera:
//                //拍照按钮
//                Toast.makeText(this, "即将打开相机", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_cancel:
//                //取消按钮
//                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
//                mCameraDialog.dismiss();
//                break;
//
//        }
//    }


    //底部菜单栏，弹出一个选择，提供是选择拍照还是从相册选择图片
    private void setDialog() {
        mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        btn_choose_img = root.findViewById(R.id.btn_choose_img);
        btn_open_camera = root.findViewById(R.id.btn_open_camera);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
}
