package cn.datawin.controller;

import cn.datawin.entity.CommonResponse;
import com.alibaba.fastjson.JSONObject;
import jodd.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by satroler on 17-4-6.
 */
@RestController
public class UploadController extends BaseController{
    @Value("${file.uploadPath}")
    private String uploadPath;
    @Value("${file.urlPath}")
    private String urlPath;
    @Value("${file.uploadPrefix}")
    private String uploadPrefix ;
    @Value("${robot.innerpath}")
    private String innerpath;



    @RequestMapping(value = "/upload", method = RequestMethod.POST, name = "上传某个流文件")
    public CommonResponse upload(MultipartFile file, @RequestParam("folder") String folder){
        if(file == null){
            return buildErrorResponse("上传文件不能为空");
        }

        //需要区分操作系统
        boolean isWin = this.isWin();
        String sp = "/";

        //文件夹路径
        String folderDate = new DateTime().toString("yyyyMMdd");
        String destPath = "";
        if(isWin){
            destPath = uploadPrefix +  uploadPath +  folder + sp+ folderDate;
        }else {
            destPath = uploadPath +  folder + sp + folderDate;
        }
        //判断是否生成文件夹
        File tempFile = new File(destPath);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        //扩展名
        String extName = extractExtensionFromContentType(file.getOriginalFilename());
        //生成文件名
        String fileName = new ObjectId().toString()+"."+extName;
        //本地全路径
        String localFullPath = destPath + sp +fileName;

        //写入到本地
        try {
            file.transferTo(new File(localFullPath));
            //设置返回jsonstr
            JSONObject jsonObject = new JSONObject();

            //返回路径
            if(isWin){
                jsonObject.put("path",urlPath + StringUtils.substringAfter(localFullPath,":"));
            }else {
                jsonObject.put("path",urlPath + localFullPath);
            }
            return buildSuccessResponse(jsonObject.toString());
        } catch (IOException e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/uploadWithDir", method = RequestMethod.POST, name = "上传带目录")
    public CommonResponse uploadWithDir(MultipartFile file, @RequestParam("name") String name, @RequestParam("path") String path){
        if(file == null || StringUtil.isBlank(path)){
            return buildErrorResponse("上传文件不能为空");
        }
        //需要区分操作系统
        String sp = "/";

        //判断是否生成文件夹
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        //本地全路径
        String localFullPath = path + sp +name;

        //写入到本地
        try {
            file.transferTo(new File(localFullPath));
            return buildSuccessResponse(localFullPath);
        } catch (IOException e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/store", method = RequestMethod.POST, name = "存储文件")
    public CommonResponse store(MultipartFile file, @RequestParam("name") String name, @RequestParam("path") String path){
        if(file == null || StringUtil.isBlank(path) || !path.startsWith("/home/datawin")){
            return buildErrorResponse("上传文件不能为空");
        }
        //需要区分操作系统
        String sp = "/";

        //判断是否生成文件夹
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        //本地全路径
        String localFullPath = path + sp + name;

        //写入到本地
        try {
            file.transferTo(new File(localFullPath));
            return buildSuccessResponse(localFullPath);
        } catch (IOException e) {
            return buildErrorResponse(e.getMessage());
        }
    }

    private String extractExtensionFromContentType(String fileName) {
        return StringUtils.substringAfterLast(fileName, ".");
    }

    private boolean isWin(){
        String osName = System.getProperty("os.name");
        if(osName.toLowerCase().indexOf("windows")>-1){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        System.out.println(new DateTime().toString("yyyyMMdd"));
    }
}
