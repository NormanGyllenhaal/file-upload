### java springmvc 上传插件 file upload

[![Build Status](https://travis-ci.org/NormanGyllenhaal/file-upload.svg?branch=master)](https://travis-ci.org/NormanGyllenhaal/file-upload)
- maven 依赖
```xml
<dependency>
       <groupId>top.javatool.fileuplod</groupId>
       <artifactId>file-upload</artifactId>
       <version>1.0</version>
  </dependency>
```
        
- 插件功能  
   单个或多个文件上传  
   同步异步文件处理  
   校验文件后缀    
   校验文件头  
   保存文件到指定目录，并返回文件的url  
   支持图片压缩  
 
- 使用
```xml
<bean id="fileupload" class="top.javatool.fileupload.FileUpload"/>
```