package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import game.Game21;
import tool.CheckTool;
import tool.MessageTool;
import tool.WeixinTool;

/**
 * Servlet implementation class weixinServlet
 */
public class WeixinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Game21 game = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeixinServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 接入认证
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//System.out.println("11111");
		//获取签名
		String signature = request.getParameter("signature");
		//获取时间戳
		String timestamp = request.getParameter("timestamp");
		//获取随机数
		String nonce = request.getParameter("nonce");
		//获取随机字符串
		String echostr = request.getParameter("echostr");
		
		//校验成功返回
		PrintWriter out = response.getWriter();
		if(CheckTool.checkSignature(signature, timestamp, nonce))//校验
		{
			//System.out.println("22222");
			out.print(echostr);//返回随机字符串
		}
		out.close();
		out = null;
	}

	/**
	 * 消息的接收与响应
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		//System.out.println("3333");
		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageTool.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String event = map.get("Event");
			String eventKey = map.get("EventKey");
			String content = map.get("Content");
			String message = null;
			boolean goOn = true;
			//事件处理
			if(msgType.equals("event") )
			{
				//点击21点游戏
				if(event.equals("CLICK"))
				{
					//
					//System.out.println(game.getStep());
					game = new Game21();
					game.dispatchCard();
					game.moveStep();
					System.out.println(game.getStep());
					message = MessageTool.initText(toUserName, fromUserName, Game21.firstMenu()+game.tellUserCrad()+"\n"+game.secondMenu());
					//out.append(message);
					out.print(message);
					out.close();
					System.out.println(00000);
				}
				
				if(event.equals("subscribe"))
				{
					message = MessageTool.initText(toUserName, fromUserName, MessageTool.welcome());
					out.print(message);
					out.close();
				}				
			}
			
			if(msgType.equals("text"))
			{
				//System.out.println(4444);
				if(null != game)
				{
					if(game.getStep() == 1)
					{
						System.out.println(game.getStep());
						if(content.equals("1"))
						{
							message = MessageTool.initText(toUserName, fromUserName, "新牌为"+game.getOtherCard()+"\n\n"+game.tellUserCrad()+"\n"+Game21.secondMenu());
						}else if(content.equals("2"))
						{
							String tmp = game.tellUserCrad();
							tmp += game.serverTurn()+"\n";
							tmp += game.userWin();
							message = MessageTool.initText(toUserName, fromUserName, tmp);
							game.moveStep();
						}else {
							message = MessageTool.initText(toUserName, fromUserName, game.secondMenu());
						}
						
						out.print(message);
						out.close();
					}
				}
				
				if(content.equals("你好"))
				{
					message = MessageTool.initText(toUserName, fromUserName,"你好");
					out.print(message);
					out.close();
				}
				
				if(content.equals("我要玩游戏"))
				{
					message = MessageTool.initText(toUserName, fromUserName,"请点击【进行游戏】下【21点】即可开始游戏");
					out.print(message);
					out.close();
				}
				
				if(content.equals("21点"))
				{
					message = MessageTool.initNewsMessage(toUserName, fromUserName);
					out.print(message);
					out.close();
				}
			}
			
			
			System.out.println(message);
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
