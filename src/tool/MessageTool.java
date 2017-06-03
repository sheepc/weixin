package tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import res.Article;
import res.ImageMessage;
import res.NewsMessage;
import res.TextMessage;


public class MessageTool {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";
	
	//将xml转换为map
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws DocumentException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element e:list)
		{
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	//将文本消息对象转换为xml
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 组装文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageTool.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}
	
	//将图文消息对象转换为xml
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 图文消息的组装
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message = null;
		List<Article> newsList = new ArrayList<Article>();
		NewsMessage newsMessage = new NewsMessage();
		
		Article article = new Article();
		article.setTitle("21点游戏介绍");
		article.setDescription("21点[1]  又名黑杰克（Black Jack），起源于法国，已流传到世界各地。现在在世界各地的赌场中都可以看到二十一点，随着互联网的发展，二十一点开始走向网络时代。该游戏由2到6个人玩，使用除大小王之外的52张牌，游戏者的目标是使手中的牌的点数之和不超过21点且尽量大。有着悠久的历史。");
		article.setPicUrl("http://f.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=eb1502d80c7b02080cc938e75ae295ee/9d82d158ccbf6c8105190695ba3eb13533fa40a4.jpg");
		article.setUrl("");
		
		newsList.add(article);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 图片消息转为xml
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 组装图片消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName,String fromUserName){
		String message = null;
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setMediaId("JTH8vBl0zDRlrrn2bBnMleySuHjVbMhyAo0U2x7kQyd1ciydhhsVPONbnRrKGp8m");
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	public static String welcome(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注本公众号，本公众号是一个可以打牌的公众号喔！");
		return sb.toString();
	}
	
}
