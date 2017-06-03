package tool;
//校验工具
import java.util.Arrays;

public class CheckTool {
	private static final String token="weixin";
	
	//校验签名
	public static boolean  checkSignature(String signature, String timestamp, String nonce)
	{
		String[] arr = new String []{token, timestamp, nonce};
		//字典序排序
		Arrays.sort(arr);
		
		//连接token, timestamp, nonce三个参数
		StringBuilder decript = new StringBuilder();
		for(int i = 0; i<arr.length; i++)
		{
			decript.append(arr[i]);
		}
		
		//sha1加密
		String tmpStr = DecriptTest.SHA1(decript.toString());
		
		if(tmpStr.equals(signature))
		{
			return true;
		}else{
			return false;
		}
		
	}
	
	
}
