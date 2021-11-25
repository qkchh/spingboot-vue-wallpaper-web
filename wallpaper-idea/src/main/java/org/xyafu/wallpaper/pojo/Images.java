package org.xyafu.wallpaper.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Images {
    private String imageId;
    private String imageAuthor;
    private String imageName;
    private String imageSrc;
    private int imageDeleteState;
    private String imageTags;
}
