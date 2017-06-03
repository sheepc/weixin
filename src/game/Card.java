package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card {
	private static String[] cardNumber = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private static String[] cardKind = {"Ã·»¨","·½¿é","ºìÌÒ","ºÚÌÒ"};
	private ArrayList<String> outCard;
	public Card()
	{
		this.outCard = new ArrayList<String>();
	}
	
	public String getOneCrad()
	{
		Random rand = new Random();
		int number = rand.nextInt(cardNumber.length);
		int kind = rand.nextInt(cardKind.length);
		while(true)
		{
			if(outCard.contains(cardKind[kind]+cardNumber[number]))
			{
				number = rand.nextInt(cardNumber.length);
				kind = rand.nextInt(cardKind.length);
			}else{
				break;
			}
		}
		outCard.add(cardKind[kind]+cardNumber[number]);
		return cardKind[kind]+cardNumber[number];					
	}
}
