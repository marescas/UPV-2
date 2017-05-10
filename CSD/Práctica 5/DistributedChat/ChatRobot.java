// This file must be implemented when completing activity 2
//

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

//
// ChatRobot implementation
//
public class ChatRobot
{
   public static void main (String args [] )
   {
    try {
	 Registry reg = LocateRegistry.getRegistry (conf.getNameServiceHost(), 
						    conf.getNameServicePort());
					
	 srv = (IChatServer) reg.lookup (serverName);
	 //System.out.println ("LOG==> ChatServer: " + srv);
      } catch (java.rmi.ConnectException e) {
	 throw new Exception ("rmiregistry not found at '" + 
			      conf.getNameServiceHost() + ":" + conf.getNameServicePort() + "'");
      } catch (java.rmi.NotBoundException e) {
	 throw new Exception ("Server '" + serverName + "' not found.");
      }
      myUser = new ChatUser ("ROBOT", this);
   }
}
