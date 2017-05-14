### java springmvc 上传插件 file upload

[![Build Status](https://travis-ci.org/NormanGyllenhaal/file-upload.svg?branch=master)](https://travis-ci.org/NormanGyllenhaal/file-upload)
        
- 插件功能  
   单个或多个文件上传  
   同步异步文件处理  
   校验文件后缀    
   校验文件头  
   保存文件到指定目录，并返回文件的url  
   支持图片压缩  
 
- 在spring mvc 中使用
1. 首先加入maven依赖 ，已加入maven中央仓库

- maven 依赖
```xml
<dependency>
       <groupId>top.javatool.fileuplod</groupId>
       <artifactId>file-upload</artifactId>
       <version>1.0</version>
  </dependency>
```
2. bean 注入
```xml
<bean id="fileupload" class="top.javatool.fileupload.FileUpload"
p:filePath="/data/www" 
p:filePrefix="test／" 
p:fileType=".jpg,.gif,.png,.bmp,.jpeg" 
p:fileHeader="FFD8FF,89504E47,47494638,49492A00,424D"
p:host="www.test.com"
p:ip=""
/>
```

3. 代码中使用
```java
@org.springframework.beans.factory.annotation.Autowired
private FileUpload fileUpload;

  
@org.springframework.web.bind.annotation.RequestMapping(value = "upload")
public void testUpload(MultipartFile headImg,HttpServletRequest request){
    //保存文件到指定路径并返回图片url
    String imageUrl = fileUpload.saveFile(file,requet);
    //http://www.test.com/test/yyyy-MM-dd/随机数字（时间戳+6位随机数).文件后缀
}
```

4. 参数说明

- filePath 文件保存到的服务器路径，如不存在自动创建（必须）
- filePrefix 文件保存到的服务器路径前缀 就是在filePath 之后加上相应的路径  
- fileType 允许上传的文件后缀，如不符合返回异常   
- fileHeader 允许的文件头 
- host 图片保存的域名 
- ip 提供 ip到host 的转换 可以在在本机配置host 文件配置 相应规则  
如 0.0.0.0 www.test.com     
这样在进行多个域名服务器时部署时会比较方便  如果ip存在则不启用host


  