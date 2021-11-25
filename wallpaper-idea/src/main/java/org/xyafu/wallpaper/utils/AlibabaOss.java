package org.xyafu.wallpaper.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;

public final class AlibabaOss {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private static String bucketName = "wallpaper-one";


    /**
     * 上传文件到阿里巴巴oss储存库
     * @param file 图片文件
     * @param imageName 图片名称
     */
    public  static void uploadImages(File file, String imageName, HttpSession session){
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
//        String objectName = "wallpaper/VE033.jpg";
//        File file=new File("E:/图片/已发布/紫罗兰的永恒花园/VE033.jpg");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
//        String content = "Hello OSS";
//        ossClient.putObject(bucketName, "wallpaper/"+imageName, inputStream);
            // 上传文件的同时指定了进度条参数。
        ossClient.putObject(new PutObjectRequest(bucketName, "wallpaper/"+imageName, file).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener(session)));

        // 关闭OSSClient。
        ossClient.shutdown();
//        File file=new File("/staci/VE033.jpg");
//        System.out.println(file.getPath());
    }

    public void deleteImage(String imageName){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketName, imageName);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
