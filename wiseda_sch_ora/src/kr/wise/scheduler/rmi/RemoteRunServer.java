package kr.wise.scheduler.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class RemoteRunServer {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		
		RemoteRunImpl rri	= new RemoteRunImpl();
		String rmiUrl = "rmi://10.1.23.6:8999/remoteRunDQ";
		Naming.rebind(rmiUrl, rri);
		
		System.out.println("wait rmi client call...");
		
		
		
	}
}
