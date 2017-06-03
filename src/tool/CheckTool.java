package tool;
//У�鹤��
import java.util.Arrays;

public class CheckTool {
	private static final String token="weixin";
	
	//У��ǩ��
	public static boolean  checkSignature(String signature, String timestamp, String nonce)
	{
		String[] arr = new String []{token, timestamp, nonce};
		//�ֵ�������
		Arrays.sort(arr);
		
		//����token, timestamp, nonce��������
		StringBuilder decript = new StringBuilder();
		for(int i = 0; i<arr.length; i++)
		{
			decript.append(arr[i]);
		}
		
		//sha1����
		String tmpStr = DecriptTest.SHA1(decript.toString());
		
		if(tmpStr.equals(signature))
		{
			return true;
		}else{
			return false;
		}
		
	}
	
	
}
