package main;

import java.io.IOException;

import HTTPClient.ParseException;
import menu.Menu;
import req.AccessToken;
import tool.MessageTool;
import tool.WeixinTool;

public class Manager {
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		try {
			AccessToken token = WeixinTool.getAccessToken();
			if(null != token)
			{
				System.out.println("token为"+token.getToken());
				System.out.println("有效时间为"+token.getExpiresIn());
				
				//Menu menu = WeixinTool.initMenu();
				//WeixinTool.createMenu(token.getToken(), menu);
				
				//String mediaId = WeixinTool.upload("./image/9d82d158ccbf6c8105190695ba3eb13533fa40a4.jpg",token.getToken(),MessageTool.MESSAGE_IMAGE);
				//System.out.println(mediaId);
			}else{
				System.out.println("获取token错误");
			}
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
