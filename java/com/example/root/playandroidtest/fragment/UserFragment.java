package com.example.root.playandroidtest.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.activity.AboutActivity;
import com.example.root.playandroidtest.activity.Test;
import com.example.root.playandroidtest.activity.UserCollectActivity;
import com.example.root.playandroidtest.activity.UserLoginActivity;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.util.PreferUtils;
import com.example.root.playandroidtest.util.T;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Root on 2018/3/13.
 * 首页三个界面的User界面总布局
 */
public class UserFragment extends Fragment {
//public class UserFragment extends Fragment implements View.OnClickListener {

    private LinearLayout ll_user_info;
    private CircleImageView user_photo;
    private TextView tv_userName;
    private TextView tv_logou;

    //三个CardView布局
    private CardView cv_collect;
    private CardView cv_about;
    private CardView cv_login_logout;

    //有关底部菜单的控件初始化
    private Dialog mCameraDialog;
    private CardView btn_open_camera;
    private CardView btn_choose_img;
    private CardView btn_cancel;

    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private SharedPreferences preferences;

    public UserFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        Check_ISLogin();
        initView(rootView);
        doClick();
        return rootView;
    }

    private void Check_ISLogin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        if (AppConst.IS_LOGIN) {        //检测到登陆状态为true
                            preferences = getActivity().getSharedPreferences("config", 0);

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_logou.setText("退出登陆");
                                    tv_logou.setTextColor(getResources().getColor(R.color.red_light));
                                    tv_userName.setText(preferences.getString("username", "Test"));
                                }
                            });

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    //初始化各个控件
    private void initView(View view) {
        cv_about = (CardView) view.findViewById(R.id.cv_about);
        cv_collect = (CardView) view.findViewById(R.id.cv_collect);
        cv_login_logout = (CardView) view.findViewById(R.id.cv_login_logout);
        ll_user_info = (LinearLayout) view.findViewById(R.id.ll_user_info);
        user_photo = (CircleImageView) view.findViewById(R.id.iv_photo);
        tv_userName = (TextView) view.findViewById(R.id.tv_name);
        tv_logou = (TextView) view.findViewById(R.id.tv_logou);
        //new一个dialog，加载style（不是布局）
        mCameraDialog = new Dialog(getActivity(), R.style.BottomDialog);
    }

    //点击响应事件的实现
    private void doClick() {
        //about界面的跳转
        cv_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it_about = new Intent(MyApplication.getContext(), AboutActivity.class);
                startActivity(it_about);
            }
        });

        //点击用户收藏按钮的监听
        cv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //当点击收藏按钮时，先判断用户是否登陆
                if (PreferUtils.getBoolean(MyApplication.getContext(), AppConst.IS_LOGIN_KEY, false) == false) {
                    //判断boolean值是否为空，默认值为空
                    T.showShort(getContext(),"请先登录");
                    return;
                } else {
                    //用户已经登陆
                    Intent collect_intent = new Intent(MyApplication.getContext(), UserCollectActivity.class);
                    startActivity(collect_intent);
                }
            }
        });

        tv_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setDialog();
                if (PreferUtils.getBoolean(MyApplication.getContext(), AppConst.IS_LOGIN_KEY, false) == true){
                    //说明已经登陆
                    T.showShort(getContext(),"您已登陆");
//                    return;
                } else {
                    Intent login_intent = new Intent(MyApplication.getContext(), UserLoginActivity.class);
                    startActivity(login_intent);
                }
//                Intent intent = new Intent(MyApplication.getContext();
            }
        });

        //判断是否登陆的点击事件
        cv_login_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferUtils.getBoolean(getContext(), AppConst.IS_LOGIN_KEY, false) == false) {
                    //说明没有登陆
                    //tv_logou.setText("尚未登陆");
                    T.showShort(getContext(),"对不起，您还没有登陆");
                } else {
//                    tv_logou.setText("退出登陆");
//                    tv_logou.setTextColor(getResources().getColor(R.color.colorAccent));
                    T.showShort(getContext(),"对不起，您已经退出登陆");
                }
            }
        });

        tv_logou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDialog();
            }
        });

        user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击头像按钮，首先判断是否登陆
                //如果登陆，则弹出两个选择，拍照或者从相册选择
                //如果没有登陆则不响应
                T.showShort(getContext(),"点击了头像");
                setDialog();
            }
        });
    }

    //弹出一个对话框，询问是否退出登陆
    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("确认退出吗？")
                .setMessage("点击下面的按钮做出选择！")
                .setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //点击确认退出后跳转页面
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //点击取消按钮后返回原来页面

            }
        });
        builder.show();

    }

    //底部菜单栏，弹出一个选择，提供是选择拍照还是从相册选择图片
    private void setDialog() {
        //mCameraDialog = new Dialog(getContext(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        btn_choose_img = root.findViewById(R.id.btn_choose_img);
        btn_open_camera = root.findViewById(R.id.btn_open_camera);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        //最终加载显示视图
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        //设置dialog的显示位置
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
        BottomClick();
    }

    private void BottomClick() {
        btn_choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择照片按钮
//                T.showShort(MyApplication.getContext(), "请选择照片");
                mCameraDialog.dismiss();
                BottomOpenAlum();

            }
        });

        btn_open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //拍照按钮
//                T.showShort(MyApplication.getContext(), "即将打开相机");
                mCameraDialog.dismiss();
                BottomOpenCamera();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.showShort(getContext(),"取消");
                mCameraDialog.dismiss();
            }
        });
    }

    //调用摄像头拍照
    private void BottomOpenCamera() {
        //创建File对象，用于存储拍照后的照片
        File outputImage = new File(MyApplication.getContext().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 24) {  //判断操作系统是否低于Android7.0
            imageUri = FileProvider.getUriForFile(MyApplication.getContext(), "com.example.root.playandroidtest.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    //带返回值的拍照调用结果判断
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        //把拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(MyApplication.getContext()
                                .getContentResolver().openInputStream(imageUri));
                        user_photo.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (requestCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4系统以上的
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下的操作系统
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            default:
                break;
        }
    }

    //调用相册选择头像
    private void BottomOpenAlum() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            openAlbum();
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("images/*");
        startActivityForResult(intent, CHOOSE_PHOTO);  //打开相册
    }

    //使用运行时权限的判定结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    T.showShort(getContext(),"You denied the permission");
                }
                break;
            default:
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getContext(), uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("aom.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }

        displayImage(imagePath);  //根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContext().getContentResolver().query(uri, null,selection,null,null);
        if (cursor != null) {
            if (cursor.moveToNext()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            user_photo.setImageBitmap(bitmap);
        } else {
            T.showShort(getContext(),"Failed to get image！Please try again!");
        }
    }

}
