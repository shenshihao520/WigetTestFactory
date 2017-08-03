package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 自制serializable 输出类
 * Created by shenshihao520 on 2017/8/2.
 */

public class CustomOutputStream {
    File file = null;
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;

    /*
        此处需要传入一个要存放序列化文件的输出路径 还有要序列化的obj
     */
    public CustomOutputStream (String path,Object obj)
    {
        file = new File(path);
        if(file != null)
        {
            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                if(oos != null)
                {
                    oos.writeObject(obj);
                    oos.flush();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                  try {
                      if(oos != null)
                      {
                          oos.close();
                      }
                      if(fos != null)
                      {
                          fos.close();
                      }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }

        }
    }
}
