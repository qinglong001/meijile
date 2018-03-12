package com.mjl.onestep.socketio;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.mjl.onestep.po.sys.MessageInfo;
import com.mjl.onestep.po.sys.SysClientInfo;
import com.mjl.onestep.repository.sys.SysClientInfoRepository;
/**
 * SocketIO 监听类
 * @author fcj
 *
 */
@Component
public class MessageEventHandler 
{
	private final SocketIOServer server;
	
	@Autowired
	private SysClientInfoRepository clientInfoRepository;
	
	@Autowired
    public MessageEventHandler(SocketIOServer server)
	{
        this.server = server;
    }

	/**
	 *  监听用户连接
	 * 	添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
	 *	方便后面发送消息时查找到对应的目标client,
	 *
	 */
	@OnConnect
    public void onConnect(SocketIOClient client)
    {
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		SysClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
		System.out.println(clientId+"进来了" );
		if (clientInfo != null)
		{
			Date nowTime = new Date(System.currentTimeMillis());
			clientInfo.setConnected((short)1);
			clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
			clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
			clientInfo.setLastconnecteddate(nowTime);
			clientInfoRepository.save(clientInfo);
		}
    }
	/**
	 * 客户端断开连接时调用，刷新客户端信息
	 *
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client)
	{
		String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
		Date nowTime = new Date(System.currentTimeMillis());
		System.out.println(clientId+"断开了"+nowTime);
		SysClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
		if (clientInfo != null)
		{
			clientInfo.setConnected((short)0);
			clientInfo.setMostsignbits(null);
			clientInfo.setLeastsignbits(null);
			clientInfoRepository.save(clientInfo);
		}
	}
	/**
	 * 接受消息入口
	 * 消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
	 */
	@OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, String data) 
	{
			System.out.println(data);
			client.sendEvent("messageevent", data);
			System.out.println("发送消息data 给"+server.getAllClients().iterator().next());
		
    }
}
