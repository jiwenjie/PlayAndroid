package com.example.root.playandroidtest.util;

import android.content.Context;
import android.widget.Toast;

import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.bean.ArticleBean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Root on 2018/3/18.
 * 用于缓存获取的数据到本地（Cache和DiskCache）
 */

public class CacheDataUtil {

    /**
     * 设置缓存有效期是6小时
     * 可以专门把失效事件作为一个参数，提供不同失效事件场景定制缓存失效事件
     * 这个是默认缓存有效期的事件
     */
    private final static int CACHE_TIME_DEFAULT = 1000*60*20*6;  //6小时
    /**
     * 文件名为键，缓存有效时间为值，可以对每一个缓存文件设置不同的有效事件
     */
    private final static HashMap<String, Integer> mMap = new HashMap<String, Integer>();
    /**
     * 缓存文件的后缀
     */
//    private static final String CACHE_EXTENSION = ".ser";
    private static final String CACHE_EXTENSION = ".JackTxt";

//    public static

    private static final String CACHE_SHARED_PREFERENCES = "cache_sharedpreferences";

    /**
     * 不带时间的缓存，就使用默认的缓存有效时间CACHE_TIME_DEFAULT
     * @param AppContext
     * @param fileName
     * @param list
     * @return
     */
    public static boolean saveListObject(Context AppContext, String fileName, List<ArticleBean> list) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = AppContext.openFileOutput(fileName+CACHE_EXTENSION, AppContext.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            //循环取出数据写入磁盘
            for (ArticleBean beans : list) {
                oos.writeObject(beans);
            }
            oos.flush();
//            DebugLog.v("response 文件 "+fileName+CACHE_EXTENSION +" 已缓存");
            Toast.makeText(AppContext, "文件已经缓存", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 增加一个参数，可以为每一个缓存文件设置自己的失效时间
     * @param AppContext
     * @param fileName
     * @param list
     * @param validMinutes
     * @return
     */
    public static boolean saveListObject(Context AppContext, String fileName, List<ArticleBean> list, int validMinutes) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        mMap.put(fileName, validMinutes);

        try {
            fos = AppContext.openFileOutput(fileName + CACHE_EXTENSION, AppContext.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);

            //循环取出数据写入磁盘
            for (ArticleBean beans : list) {
                oos.writeObject(beans);
            }
            oos.flush();
            Toast.makeText(AppContext, "文件已经缓存", Toast.LENGTH_SHORT).show();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 未完成读取操作 （有关对象的读取）
     * @param AppContext
     * @param file
     * @return
     */
    public static List<ArticleBean> readListObject(Context AppContext, String file) {

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<ArticleBean> beanList = new ArrayList<>();

        try {
            fis = AppContext.openFileInput(file+CACHE_EXTENSION);
            ois = new ObjectInputStream(fis);
            ArticleBean bean = (ArticleBean) ois.readObject();
            beanList.add(bean);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanList;
    }

    //写入数据
    public static void WriteNetDataToFile(String fileName,String data, int validMinutes) {
        mMap.put(fileName, validMinutes);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(AppConst.CACHE_PATH_ONE + "/" + fileName + CACHE_EXTENSION);
            fos.write(data.getBytes());
            fos.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void WriteStringToFile2(String filePath, String data) {
        try {
            FileWriter fw = new FileWriter(AppConst.CACHE_PATH_ONE + "/" + filePath + CACHE_EXTENSION, true);
            BufferedWriter bw = new BufferedWriter(fw);
            //bw.append("在已有的基础上添加字符串");
            bw.write(data);// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*InputStreamReader+BufferedReader读取字符串  ， InputStreamReader类是从字节流到字符流的桥梁*/

    /* 按行读对于要处理的格式化数据是一种读取的好方式 */
    public static String readNetForFile(String fileName) {
        int len=0;
        StringBuffer str=new StringBuffer("");

        try {
            File file = getFile(fileName);
            FileInputStream is=new FileInputStream(file);
            InputStreamReader isr= new InputStreamReader(is);
            BufferedReader in= new BufferedReader(isr);
            String line=null;

            while( (line=in.readLine())!=null ) {
                if(len != 0)  {             // 处理换行符的问题
                    str.append("\r\n"+line);
                } else {
                    str.append(line);
                }

                len++;
            }
            in.close();
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str.toString();
    }


    //获得File的初始化
    public static File getFile(String fileName) {
        File file=new File(AppConst.CACHE_PATH_ONE + "/" + fileName + CACHE_EXTENSION);

        return file;
    }


    /**
     * 检查此文件的缓存是否已经失效
     */
    public static boolean isCacheDataFailure(Context AppContext,String cacheFileName) {
        boolean failure = false;
        int cache_time= CACHE_TIME_DEFAULT;
        if(null!=mMap.get(cacheFileName)){
            /*换算成毫秒*/
            cache_time = mMap.get(cacheFileName)*60*1000;
        }
        File data = AppContext.getFileStreamPath(cacheFileName + CACHE_EXTENSION );
        if (data.exists() && (System.currentTimeMillis() - data.lastModified()) > cache_time)
            failure = true;
        else if (!data.exists())
            failure = true;
        return failure;
    }

    /**
     * 清除所有的缓存
     * @param mContext
     */
    public static void clearAllCache(Context mContext){
//        clearJsonCache(mContext);
        File filesDir = mContext.getFilesDir();

        // 实现FilenameFilter接口的类实例可用于过滤器文件名
        FilenameFilter filter = new FilenameFilter() {
            // 测试指定文件是否应该包含在某一文件列表中。
            public boolean accept(File dir, String name) {
                return name.endsWith(CACHE_EXTENSION);
            }
        };
        // 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录
        String[] cacheFileList=filesDir.list(filter);
        File temp;
        for(String s:cacheFileList){
            temp = mContext.getFileStreamPath(s);
            if(temp.delete()){
                Toast.makeText(mContext, "文件已经删除", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "文件删除失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
