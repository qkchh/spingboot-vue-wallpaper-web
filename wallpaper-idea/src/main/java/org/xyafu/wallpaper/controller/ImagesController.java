package org.xyafu.wallpaper.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xyafu.wallpaper.mapper.ImagesMapper;
import org.xyafu.wallpaper.pojo.Images;
import org.xyafu.wallpaper.pojo.ResultInfo;
import org.xyafu.wallpaper.pojo.User;
import org.xyafu.wallpaper.utils.AlibabaOss;
import org.xyafu.wallpaper.utils.UuidUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/images")
@CrossOrigin
public class ImagesController {

    @Autowired
    private ImagesMapper imagesMapper;

    @PostMapping("/upload")
    @ResponseBody
    public ResultInfo upload(@RequestPart("photos") MultipartFile[] photos,
                             String[] tags,
                             HttpSession session){
        Images image=new Images();
        String username="无名氏";
        if(photos.length > 0){
            for (MultipartFile photo : photos) {

                if(!photo.isEmpty()){
//                    System.out.println(photo.isEmpty());
//                    System.out.println(photo.getOriginalFilename());
//                    System.out.println(String.format("%.2f",photo.getSize()/1048576.0));
//                    System.out.println(photo.getName());
////                    System.out.println(photo.getResource());
//                    System.out.println(photo.getContentType().split("/")[1]);
////                    System.out.println(photo.getBytes());
//                    try {
//                        BufferedImage bufferedImage=ImageIO.read(photo.getInputStream());
//                        System.out.println(bufferedImage.getWidth());
//                        System.out.println(bufferedImage.getHeight());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    补全image内容上传数据库
                    User user = (User) session.getAttribute("user");
                    if(user!=null){
                        username=user.getUserName();
                    }
                    String imageId = UuidUtil.getUuid();
                    image.setImageId(imageId);
                    image.setImageAuthor(username);
                    image.setImageSrc(imageId+"."+photo.getContentType().split("/")[1]);
                    image.setImageName(photo.getOriginalFilename());
                    image.setImageDeleteState(1);

                    String t="";
                    for (String tag : tags) {
                        t=t+tag+"#";
                    }
                    image.setImageTags(t);

                    int insert = imagesMapper.insert(image);
                    System.out.println(insert);
                    try {
//                        创建临时文件
                        File file = null;
                        try {
                            file = File.createTempFile("tmp", null);
                            photo.transferTo(file);
                            file.deleteOnExit();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        AlibabaOss.uploadImages(file,image.getImageSrc(),session);
                        return new ResultInfo(true,"上传成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    List<Images> images = imagesMapper.selectList(null);
                    images.forEach(System.out::println);
                }
            }
        }
        return new ResultInfo(false,"上传失败");
    }

    /**
     * 获取实时长传进度
     * @param request
     * @returnuplaod
     */
    @RequestMapping ("/upload/percent")
    @ResponseBody
    public int getUploadPercent(HttpServletRequest request){
        HttpSession session=request.getSession();
        System.out.println(session.getAttribute("percent"));
        System.out.println(session.getId());
        if(session.getAttribute("percent") == null ){
            return -1;
        }
        int percent =  (int) session.getAttribute("percent");
        return percent;
    }
    /**
     * 重置上传进度
     * @param request
     * @return
     */
    @RequestMapping ("/percent/reset")
    @ResponseBody
    public int resetPercent(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("percent",0);
        return 1;
    }


    /**
     * 获取图片
     */
    @GetMapping("getImages")
    public ResultInfo getImages(){

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("image_delete_state",1);
        List list = imagesMapper.selectList(wrapper);
        if(list!=null){
            return new ResultInfo(true,list,"查询成功");
        }
        return new ResultInfo(false,"查询失败");
    }


    /**
     * 获取5张图片
     * @return
     */
    @GetMapping("getFiveImages")
    public ResultInfo getFiveImages(){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("image_delete_state",1);
        wrapper.orderByDesc("image_id");
        wrapper.last("limit 0,5");
        List list = imagesMapper.selectList(wrapper);
        if(list.size()>=5){
            return new ResultInfo(true,list,"查询成功");
        }
       return new ResultInfo(false,"查询失败");
    }


//    /**
//     * 搜索图片
//     * @return
//     */
//    @GetMapping("queryImage/{query}")
//    public ResultInfo queryImage(@PathVariable("query") String query){
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("image_delete_state",1);
//        wrapper.like("image_tags",query);
//        List list = imagesMapper.selectList(wrapper);
//        System.out.println(query);
//        System.out.println(list);
//        if(list!=null){
//            return new ResultInfo(true,list,"查询成功");
//        }
//        return new ResultInfo(false,"查询失败");
//    }

}
