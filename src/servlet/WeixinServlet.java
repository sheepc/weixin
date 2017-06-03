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
	 * ������֤
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//System.out.println("11111");
		//��ȡǩ��
		String signature = request.getParameter("signature");
		//��ȡʱ���
		String timestamp = request.getParameter("timestamp");
		//��ȡ�����
		String nonce = request.getParameter("nonce");
		//��ȡ����ַ���
		String echostr = request.getParameter("echostr");
		
		//У��ɹ�����
		PrintWriter out = response.getWriter();
		if(CheckTool.checkSignature(signature, timestamp, nonce))//У��
		{
			//System.out.println("22222");
			out.print(echostr);//��������ַ���
		}
		out.close();
		out = null;
	}

	/**
	 * ��Ϣ�Ľ�������Ӧ
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
			//�¼�����
			if(msgType.equals("event") )
			{
				//���21����Ϸ
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
							message = MessageTool.initText(toUserName, fromUserName, "����Ϊ"+game.getOtherCard()+"\n\n"+game.tellUserCrad()+"\n"+Game21.secondMenu());
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
				
				if(content.equals("���"))
				{
					message = MessageTool.initText(toUserName, fromUserName,"���");
					out.print(message);
					out.close();
				}
				
				if(content.equals("��Ҫ����Ϸ"))
				{
					message = MessageTool.initText(toUserName, fromUserName,"������������Ϸ���¡�21�㡿���ɿ�ʼ��Ϸ");
					out.print(message);
					out.close();
				}
				
				if(content.equals("21��"))
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
