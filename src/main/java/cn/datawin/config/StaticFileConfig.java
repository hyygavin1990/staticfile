package cn.datawin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 配置静态本地映射地址
 */
@Configuration
public class StaticFileConfig extends WebMvcConfigurerAdapter {
    @Value("${file.uploadPath}")
    private String uploadPath;
    @Value("${file.uploadPrefix}")
    private String uploadPrefix;
    @Value("${robot.innerpath}")
    private String bootInnerPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String osName = System.getProperty("os.name");
        //需要区分操作系统
        if(osName.toLowerCase().indexOf("windows")>-1){
            registry.addResourceHandler(uploadPath + "/**").addResourceLocations("file:"  + uploadPath);
            registry.addResourceHandler(bootInnerPath + "/**").addResourceLocations("file:" + bootInnerPath);
        }else {
            registry.addResourceHandler(uploadPath + "/**").addResourceLocations("file:" + uploadPath);
            registry.addResourceHandler(bootInnerPath + "/**").addResourceLocations("file:" + bootInnerPath);
        }
        super.addResourceHandlers(registry);
    }

}