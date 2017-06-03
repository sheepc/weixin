package game;

import java.util.ArrayList;
import java.util.List;

import tool.MessageTool;

//共步
//1.各发两张牌
//2.user补牌环节
//3.用户亮牌环节
//4.庄家补牌环节
//5.宣布结果
public class Game21 {
	private ArrayList<String> user;
	private ArrayList<String> server;
	private Card card;
	int step;
	
	public Game21(){
		this.server = new ArrayList<String>();
		this.user = new ArrayList<String>();
		card = new Card();
		step = 0;
	}
	public static String firstMenu()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎进入21点游戏\n\n");
		sb.append("正在为您创建房间中\n");
		sb.append("房间创建成功\n");
		sb.append("正在发牌\n");
		//sb.append("");
		//sb.append("");
		return sb.toString();
	}
	
	public static String secondMenu()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("是否继续要牌？\n\n");
		sb.append("要，请发1\n");
		sb.append("不要，请发2\n");
		//sb.append("");
		//sb.append("");
		return sb.toString();
	}
	
	public void dispatchCard()
	{
		user.add(this.card.getOneCrad());
		user.add(this.card.getOneCrad());
		server.add(this.card.getOneCrad());
		server.add(this.card.getOneCrad());
	}
	
	public String getOtherCard()
	{
		String otherCard = this.card.getOneCrad();
		user.add(otherCard);
		return otherCard;
	}
	
	public String tellUserCrad()
	{
		String message = "您的牌为\n";
		for(int i = 0; i < user.size();i++)
		{
			message += user.get(i).toString()+"\n"; 
		}
		return message;
	}
	
	public String tellServerCrad()
	{
		String message = "庄家的牌为";
		for(int i = 0; i < server.size();i++)
		{
			message += server.get(i).toString()+"\n"; 
		}
		return message;
	}
	
	public void moveStep()
	{
		this.step++;
	}
	
	public int getStep()
	{
		return this.step;
	}
	
	public void setStep()
	{
		this.step = 0;
	}
	
	public String serverTurn()
	{
		String message = "庄家的牌为\n";
		for(int index = 0; index<server.size();index++)
		{
			message += server.get(index)+"\n";
		}
		message += "轮到庄家取牌\n";
		
		//System.out.println("Server "+countServer());
		//System.out.println("User "+countUser());
		//两个A 21点 20 点
		if(countServer() == -1 || countServer() == -2 || countUser()>21||(countServer()<=20 && countServer()>=countUser()))
		{
			message += "庄家不取牌\n";
			return message;
		}else{
			for(int i = 0; i < 3; i++)
			{
				String newCard = card.getOneCrad();
				message += "庄家取了一张牌，牌为"+newCard+"\n";
				server.add(newCard);
				if(countServer()>=countUser() || countServer()>21)break;
			}
		}
		//System.out.println("Server "+countServer());
		//System.out.println("User "+countUser());
		return message;
	}
	public int countUser()
	{
		System.out.println("countUser");
		return countSum(user);
	}
	
	public int countServer()
	{
		System.out.println("countServer");
		return countSum(server);
	}
	
	//AA为-2；A 10/J/Q/K为-1
	public int countSum(ArrayList<String> cardold)
	{
		System.out.println("countSum");
		ArrayList<String> card = new ArrayList<String>(); 
		int sum = 0;

		
		//把JQK替换成10
		for(int i = 0; i < cardold.size();i++)
		{
			if(cardold.get(i).trim().substring(2).matches("J|Q|K"))
			{
				card.add(cardold.get(i).replace(cardold.get(i).trim().substring(2), "10"));
				//System.out.println(card.get(i));
			}else{
				card.add(cardold.get(i));
			}
				//System.out.println(card.get(i));
				//System.out.println(card.get(i).replace(card.get(i).trim().substring(2), "10"));
		}
		
		//处理没取牌的情况
		if(card.size() == 2)
		{
			if(card.get(0).trim().substring(2).equals("A")
				&&card.get(1).trim().substring(2).equals("A") )
			{
				return -2; 
			}
			
			if((card.get(0).trim().substring(2).equals("A")
				||card.get(1).trim().substring(2).equals("A"))
					&&(card.get(0).trim().substring(2).equals("10")
						||card.get(1).trim().substring(2).equals("10")))
			{
				return -1;
			}
		}
		
		int aExist = 0;
		//计算A的张数
		for(int i = 0; i < card.size();i++)
		{
			if(card.get(i).trim().substring(2).equals("A"))
			{
				aExist++;
			}
		}
		//计算非A的点数
		for(int i = 0; i < card.size();i++)
		{
			if(!card.get(i).trim().substring(2).equals("A"))
			{
				sum += Integer.parseInt(card.get(i).trim().substring(2));
			}
		}
		
		//叠加A的点数
		for(int i = 0; i < aExist; i++)
		{
			int j = 0;
			if(sum+11>21)
			{
				if(j == 1)sum -= 10;
				sum++;
			}else{
				sum += 11;
				j = 1;
			}
		}
		return sum;
		
	}

	public String userWin(){
		String message=null;
		if(countUser()<0&&countServer()<0)
		{
			if(countUser()<countServer())
			{
				message = "恭喜您，您获得胜利！";
			}else if(countUser()==countServer()){
				message = "平局！";
			}else{
				message="庄家胜";
			}
		}
		
		if(countUser()<0 && countServer()>0)
		{
			message = "恭喜您，您获得胜利！";
		}
		
		if(countUser()>0 && countServer()<0||countUser()>21)
		{
			message="庄家胜";
		}
		
		if(countUser()>0 && countUser()<=21&&countServer()>21)
		{
			message = "恭喜您，您获得胜利！";
		}
		
		if(countUser()>0 && countUser()<=21&&countServer()>0 && countServer()<=21)
		{
			if(countUser()>countServer())
			{
				message = "恭喜您，您获得胜利！";
			}else if(countUser()==countServer())
			{
				message = "平局";
			}else{
				message="庄家胜";
			}
		}
		
		return message;
	}
}
