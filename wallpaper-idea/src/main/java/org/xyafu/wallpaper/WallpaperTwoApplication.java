package org.xyafu.wallpaper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan("org.xyafu.wallpaper.mapper")
@CrossOrigin
public class WallpaperTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WallpaperTwoApplication.class, args);
    }

}
