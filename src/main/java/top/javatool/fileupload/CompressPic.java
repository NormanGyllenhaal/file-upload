package top.javatool.fileupload;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;

/**
 * Created by admin on 2017/2/17.
 */

public class CompressPic {


    private String filePath;


    private String filePrefix;

    public CompressPic() {
    }

    public CompressPic(String filePath, String filePrefix) {
        this.filePath = filePath;
        this.filePrefix = filePrefix;
    }

    public  String thumbnailsCompressPic(String url) throws IOException {
        int i = url.lastIndexOf("/");
        String fileName = url.substring(i).replace("/","");
        String imageUrl = url.substring(0, i).concat("/");
        String s = url.substring(url.lastIndexOf("/", i - 1)).replaceFirst("/", "");
        StringBuilder sb = new StringBuilder().append(filePath).append(filePrefix).append(s);
        Thumbnails.of(sb.toString())
                .size(256, 256)
                .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        return imageUrl + Rename.PREFIX_DOT_THUMBNAIL.apply(fileName,null);
    }



    public  void deleteCompressPic(String compressImageUrl){
        int i = compressImageUrl.lastIndexOf("/");
        StringBuilder sb = new StringBuilder().append(filePath).append(filePrefix)
                .append(compressImageUrl.substring(compressImageUrl.lastIndexOf("/",i-1)).replaceFirst("/",""));
        File file = new File(sb.toString());
        file.delete();
    }



}
