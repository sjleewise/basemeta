package kr.wise.scheduler.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteRunByRMI extends Remote	 {
	public String testrun(String str) throws RemoteException;
	
	public void registerDq(String[] args) throws RemoteException;
}
