package main;

import java.io.IOException;

import HTTPClient.ParseException;
import menu.Menu;
import req.AccessToken;
import tool.MessageTool;
import tool.WeixinTool;

public class Manager {
	
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		try {
			AccessToken token = WeixinTool.getAccessToken();
			if(null != token)
			{
				System.out.println("tokenΪ"+token.getToken());
				System.out.println("��Чʱ��Ϊ"+token.getExpiresIn());
				
				//Menu menu = WeixinTool.initMenu();
				//WeixinTool.createMenu(token.getToken(), menu);
				
				//String mediaId = WeixinTool.upload("./image/9d82d158ccbf6c8105190695ba3eb13533fa40a4.jpg",token.getToken(),MessageTool.MESSAGE_IMAGE);
				//System.out.println(mediaId);
			}else{
				System.out.println("��ȡtoken����");
			}
		} catch (ParseException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
