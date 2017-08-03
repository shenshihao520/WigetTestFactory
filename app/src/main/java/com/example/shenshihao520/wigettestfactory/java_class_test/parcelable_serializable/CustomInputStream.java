package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 自制serializable 输入类
 * Created by shenshihao520 on 2017/8/2.
 */

public class CustomInputStream {
    File file = null;
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    Object obj = null;
    /*
        此处需要传入对于输出的 文件路径
     */
    public Object CustomInputStreamBuild (String path)
    {
        file = new File(path);

        if(file.exists())
        {
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                if(ois != null)
                {
                    obj = ois.readObject();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(ois != null)
                    {
                        ois.close();
                    }
                    if(fis != null)
                    {
                        fis.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
        return obj;
    }
}
