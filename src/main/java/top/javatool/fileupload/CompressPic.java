package top.javatool.fileupload;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.IOException;

/**
 * Created by admin on 2017/2/17.
 */
public class CompressPic {



    public static String thumbnailsCompressPic(String filePath, String url,Integer width,Integer height) throws
            IOException {
        int i = url.lastIndexOf("/");
        String fileName = url.substring(i).replace("/","");
        String imageUrl = url.substring(0, i).concat("/");
        Thumbnails.of(filePath.concat(fileName))
                .size(width, height)
                .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        return imageUrl.concat(Rename.PREFIX_DOT_THUMBNAIL.apply(fileName,null));
    }



}
