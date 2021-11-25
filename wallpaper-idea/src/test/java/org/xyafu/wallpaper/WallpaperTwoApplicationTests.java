package org.xyafu.wallpaper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xyafu.wallpaper.mapper.ImagesMapper;
import org.xyafu.wallpaper.pojo.Images;

import java.util.List;

@SpringBootTest
class WallpaperTwoApplicationTests {

    @Autowired
    ImagesMapper imagesMapper;
    @Test
    void contextLoads() {

        List<Images> images = imagesMapper.selectList(null);
        images.forEach(System.out::println);
    }

}
