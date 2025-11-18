package kr.wise.scheduler.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RemoteRunDQ {
	
	public static void main(String[] args) {
		
		String rmiUrl = "rmi://127.0.0.1:8999/remoteRunDQ"	;
		
		
		try {
			String[] param = {"QP", "P1378270837214" , "프로파일RIM실행" ,"system", "홍길동", "부서명"};
			Object obj = Naming.lookup(rmiUrl);
			RemoteRunByRMI rundq = (RemoteRunByRMI)obj;
			
			String str = rundq.testrun("이것은 테스트여");
			
			System.out.println("result : " + str);
			
//			rundq.registerDq(param);
			rundq.registerDq(args);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
