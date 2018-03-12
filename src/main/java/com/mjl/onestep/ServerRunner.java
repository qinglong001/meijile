package com.mjl.onestep;  
  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.CommandLineRunner;  
import org.springframework.stereotype.Component;  
  
import com.corundumstudio.socketio.SocketIOServer;  
  /**
   * SocketIO配置类
   * @author fcj
   *
   */
@Component  
public class ServerRunner implements CommandLineRunner {  
    private final SocketIOServer server;  
  
    @Autowired  
    public ServerRunner(SocketIOServer server) {  
        this.server = server;  
    }  
  
    @Override  
    public void run(String... args) {
        server.start();  
    }  
}