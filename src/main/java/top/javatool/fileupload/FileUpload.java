package top.javatool.fileupload;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;
import top.javatool.fileupload.exception.FileTypeException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 上传工具类
 * Created by yang peng on 2016/8/9.
 */

public class FileUpload {



    private String filePath;


    private String filePrefix;


    private String fileType;


    private String fileHeader;


    private String ip;


    private String host;


    private static  final String HTTP = "http";

    private static  final int HTTP_PORT = 80;

    private static  final int HTTPS_PORT = 443;

    private static  final String HTTPS = "https";


    /**
     * save file and return image url
     * @param file
     * @param request
     * @return
     * @throws FileTypeException
     * @throws IOException
     */

    public String handleImg(MultipartFile file, HttpServletRequest request) throws FileTypeException, IOException {
        checkFileType(file);
        String fileName = getFileName(file);
        String datePath = getDatePath();
        File f = new File(filePath.concat(datePath));
        if (!f.exists()) {
            f.mkdirs();
        }
        String pathConcatFile = datePath.concat(fileName);
        saveFile(filePath.concat(pathConcatFile), file);
        String urlPath = getUrlPath(request,pathConcatFile);
        return urlPath;
    }


    /**
     * let filePrefix concat date
     * @return
     */
    private String getDatePath(){
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(filePrefix).append(date).append(File.separator);
        return stringBuilder.toString();
    }

    /**
     * create a random file name
     * rule now date + random 6 num + file extension name
     *
     * @param headImg
     * @return
     */
    private String getFileName(MultipartFile headImg) {
        // 获取图片的文件名
        String fileName = headImg.getOriginalFilename();
        // 获取图片的扩展名
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 新的图片文件名 = 获取时间戳+"."图片扩展名
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis()).append(RandomStringUtils.randomNumeric(6)).append(".").append(extensionName);
        return sb.toString();
    }


    /**
     * save file
     *
     * @return
     */
    private void saveFile(String fileName, MultipartFile file) throws IOException {
        file.transferTo(new File(fileName));
    }


    /**
     * get the file url path
     *
     * @return
     */
    private String getUrlPath( HttpServletRequest request,String  pathConcatFile) throws
            UnknownHostException {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getScheme()).append(getHost(request)).append(getPort(request)).append(pathConcatFile);
        return sb.toString();
    }


    /**
     *
     * @param request
     * @return
     * @throws UnknownHostException
     */
    private String getHost(HttpServletRequest request) throws UnknownHostException {
        if(StringUtils.isNotEmpty(ip)){
            InetAddress address = InetAddress.getByName(ip);
            String host = address.getHostName();
            return host;
        }
        if(StringUtils.isNotEmpty(host)){
            return host;
        }
        return request.getServerName();
    }


    /**
     *
     * @param request
     * @return
     */
    private String getPort(HttpServletRequest request){
        int serverPort = request.getServerPort();
        String scheme = request.getScheme();
        if(scheme.equals(HTTP)){
            if(serverPort==HTTP_PORT){
                return StringUtils.EMPTY;
            }
        }
        if(scheme.equals(HTTPS)){
            if(serverPort==HTTPS_PORT){
                return StringUtils.EMPTY;
            }
        }
        return String.valueOf(serverPort);
    }



    /**
     * 根据文件的输入流获取文件头信息
     *
     * @return 文件头信息
     */
    private String getFileHeader(InputStream is) {
        byte[] b = new byte[4];
        if (is != null) {
            try {
                is.read(b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytesToHexString(b);
    }


    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }


    /**
     * 判断上传的文件是否合法
     * （一）、第一：检查文件的扩展名，
     * (二）、 第二：检查文件的MIME类型 。
     *
     */
    private void checkFileType(MultipartFile file) throws IOException, FileTypeException {
        String fileName = file.getOriginalFilename();
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        if(StringUtils.isNoneEmpty(fileType)){
            Set<String> fileTypeSet = new HashSet<String>(Arrays.asList(fileType.split(",")));
            if(!fileTypeSet.contains(extensionName)){
                throw new FileTypeException(extensionName + " extension name is forbid");
            }
        }
        if(StringUtils.isNoneEmpty(fileHeader)){
            Set<String> fileHeaderSet = new HashSet<String>(Arrays.asList(fileHeader.split(",")));
            String header = getFileHeader(file.getInputStream());
            if(!fileHeaderSet.contains(header)){
                throw new FileTypeException(header + "file header is forbid");
            }
        }
    }


}
