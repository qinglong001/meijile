package com.mjl.onestep.common.utils;

/**
 * 阿里大鱼接口工具类
 * @author fcj
 *
 */
public class DaYuUtil {
	/*public static String send(String mobile){
	    //设置超时时间-可自行调整
	    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	    //初始化ascClient需要的几个参数
	    //短信API产品名称（短信产品名固定，无需修改）
	    final String product = "Dysmsapi";
	 	//短信API产品域名（接口地址固定，无需修改）
	    final String domain = "dysmsapi.aliyuncs.com";
	    //替换成你的AK
	    //你的accessKeyId,参考本文档步骤2
	    final String accessKeyId = "LTAIZh7bmWAlogPb";
	    //你的accessKeySecret，参考本文档步骤2
	    final String accessKeySecret = "6xJcj5GKvwCuYAdWlKl6WrD20MGPGQ";
	    //初始化ascClient,暂时不支持多region
	  //  IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
	  //  accessKeySecret);
	    try {
			//DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  //  IAcsClient acsClient = new DefaultAcsClient(profile);
	     //组装请求对象
	     SendSmsRequest request = new SendSmsRequest();
	     //使用post提交
	     request.setMethod(MethodType.POST);
	     //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
	    request.setPhoneNumbers(mobile);
	     //  request.setPhoneNumbers("17660416793");
	     //必填:短信签名-可在短信控制台中找到
	     request.setSignName("美极乐");
	     //必填:短信模板-可在短信控制台中找到
	     request.setTemplateCode("SMS_94620097");
	     //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	     //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
	     String code=getRandomString();
	     request.setTemplateParam("{\"code\":\""+code+"\"}");
	     //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
	     //request.setSmsUpExtendCode("90997");
	     //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	     request.setOutId("aa");
	    //请求失败这里会抛ClientException异常
	    SendSmsResponse sendSmsResponse;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			    //请求成功
				System.out.println(sendSmsResponse.getCode());
				System.out.println(sendSmsResponse.getMessage());
				System.out.println(sendSmsResponse.toString());
				return code;
			    }
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	    
       
	}
	
	public static String getAddressByIP()
	{ 
	  try
	  {
	    String strIP = "222.173.12.22";
	    URL url = new URL( "http://ip.qq.com/cgi-bin/searchip?searchip1=" + strIP); 
	    URLConnection conn = url.openConnection(); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK")); 
	    String line = null; 
	    StringBuffer result = new StringBuffer(); 
	    while((line = reader.readLine()) != null)
	    { 
	      result.append(line); 
	    } 
	    reader.close(); 
	    strIP = result.substring(result.indexOf( "该IP所在地为：" ));
	    strIP = strIP.substring(strIP.indexOf( "：") + 1);
	    String province = strIP.substring(6, strIP.indexOf("省"));
	    String city = strIP.substring(strIP.indexOf("省") + 1, strIP.indexOf("市"));
	    return province+city; 
	  }
	  catch( IOException e)
	  { 
	    return "读取失败"; 
	  }
	}
	 public static void main(String[] args) {
		 getAddressByIP();
	}
	
	public static String getRandomString() {
		final char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= 5; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
			;
		}
		return sb.toString();
	}*/
}