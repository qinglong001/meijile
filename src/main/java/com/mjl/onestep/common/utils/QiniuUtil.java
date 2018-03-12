package com.mjl.onestep.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
/**
 * @author fcj
 * 七牛云存储的工具类
 */
@Component
public class QiniuUtil{
	
	

    /**
     * 先删除到之前上传的图片 然后上传新的图片
     * @param file 文件流
     * @param key 文件key
     * @param oldKey 之前的文件key
     * @return
     */
    public   void removeImg(String oldKey) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.huadong());
        //...其他参数参考类注释
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create("k1gYoMEgJEkThTjrYNB6MvSZ0XWW1Zahb_bFfeQH","_7GAB20cMxiUhna-DPhUQS52-mBs-mIUs1L-sBPB");
            BucketManager bucketManager = new BucketManager(auth, cfg);
            try {
            	//执行删除
              bucketManager.delete("one-step", oldKey);
            	//执行上传
            } catch (QiniuException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 将图片上传到七牛云
     * @param file
     * @param key 保存在空间中的名字，如果为空会使用文件的hash值为文件名
     * @return
     */
    public   String uploadImg(FileInputStream file, String key) {
    	
        Configuration cfg = new Configuration(Zone.huadong());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            String secretKey = "_7GAB20cMxiUhna-DPhUQS52-mBs-mIUs1L-sBPB";
            String accessKey = "k1gYoMEgJEkThTjrYNB6MvSZ0XWW1Zahb_bFfeQH";
            Auth auth = Auth.create( accessKey, secretKey );
            String bucket = "one-step";
            String upToken = auth.uploadToken( bucket );
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String returnPathKey = putRet.key;
                return returnPathKey;
            } catch (QiniuException ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}