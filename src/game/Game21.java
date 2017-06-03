package game;

import java.util.ArrayList;
import java.util.List;

import tool.MessageTool;

//����
//1.����������
//2.user���ƻ���
//3.�û����ƻ���
//4.ׯ�Ҳ��ƻ���
//5.�������
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
		sb.append("��ӭ����21����Ϸ\n\n");
		sb.append("����Ϊ������������\n");
		sb.append("���䴴���ɹ�\n");
		sb.append("���ڷ���\n");
		//sb.append("");
		//sb.append("");
		return sb.toString();
	}
	
	public static String secondMenu()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("�Ƿ����Ҫ�ƣ�\n\n");
		sb.append("Ҫ���뷢1\n");
		sb.append("��Ҫ���뷢2\n");
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
		String message = "������Ϊ\n";
		for(int i = 0; i < user.size();i++)
		{
			message += user.get(i).toString()+"\n"; 
		}
		return message;
	}
	
	public String tellServerCrad()
	{
		String message = "ׯ�ҵ���Ϊ";
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
		String message = "ׯ�ҵ���Ϊ\n";
		for(int index = 0; index<server.size();index++)
		{
			message += server.get(index)+"\n";
		}
		message += "�ֵ�ׯ��ȡ��\n";
		
		//System.out.println("Server "+countServer());
		//System.out.println("User "+countUser());
		//����A 21�� 20 ��
		if(countServer() == -1 || countServer() == -2 || countUser()>21||(countServer()<=20 && countServer()>=countUser()))
		{
			message += "ׯ�Ҳ�ȡ��\n";
			return message;
		}else{
			for(int i = 0; i < 3; i++)
			{
				String newCard = card.getOneCrad();
				message += "ׯ��ȡ��һ���ƣ���Ϊ"+newCard+"\n";
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
	
	//AAΪ-2��A 10/J/Q/KΪ-1
	public int countSum(ArrayList<String> cardold)
	{
		System.out.println("countSum");
		ArrayList<String> card = new ArrayList<String>(); 
		int sum = 0;

		
		//��JQK�滻��10
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
		
		//����ûȡ�Ƶ����
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
		//����A������
		for(int i = 0; i < card.size();i++)
		{
			if(card.get(i).trim().substring(2).equals("A"))
			{
				aExist++;
			}
		}
		//�����A�ĵ���
		for(int i = 0; i < card.size();i++)
		{
			if(!card.get(i).trim().substring(2).equals("A"))
			{
				sum += Integer.parseInt(card.get(i).trim().substring(2));
			}
		}
		
		//����A�ĵ���
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
				message = "��ϲ���������ʤ����";
			}else if(countUser()==countServer()){
				message = "ƽ�֣�";
			}else{
				message="ׯ��ʤ";
			}
		}
		
		if(countUser()<0 && countServer()>0)
		{
			message = "��ϲ���������ʤ����";
		}
		
		if(countUser()>0 && countServer()<0||countUser()>21)
		{
			message="ׯ��ʤ";
		}
		
		if(countUser()>0 && countUser()<=21&&countServer()>21)
		{
			message = "��ϲ���������ʤ����";
		}
		
		if(countUser()>0 && countUser()<=21&&countServer()>0 && countServer()<=21)
		{
			if(countUser()>countServer())
			{
				message = "��ϲ���������ʤ����";
			}else if(countUser()==countServer())
			{
				message = "ƽ��";
			}else{
				message="ׯ��ʤ";
			}
		}
		
		return message;
	}
}
