package com.cvicse.paas.jasypt.web;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JasyptLoginController {
	
	/**
	 * 页面跳转
	 * @return
	 */
    @RequestMapping({"/","code.html"})
    public String codePage(){
        return "code";
    }
    
    /**
     * 页面数据提交请求返回数据
     * @param encryption
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping("/get")
	public String getCode(String encryption,String password) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        // 设置密钥
        config.setPassword(encryption);
        // 设置加密方法
        config.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setConfig(config);
        // 加密
        String encryptStr = encryptor.encrypt(password);
        return encryptStr;
	}
}
