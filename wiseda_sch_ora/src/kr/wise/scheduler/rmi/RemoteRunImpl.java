package kr.wise.scheduler.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import kr.wise.scheduler.ScheduleRegistry;

public class RemoteRunImpl extends UnicastRemoteObject  implements RemoteRunByRMI {

	
	public static void main(String[] args) throws RemoteException {
		
		try {
			RemoteRunImpl rri	= new RemoteRunImpl();
			String rmiUrl = "rmi://127.0.0.1:8999/remoteRunDQ";
			Naming.rebind(rmiUrl, rri);
			System.out.println("[RMI-DQ Server] START");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	protected RemoteRunImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String testrun(String str) throws RemoteException {
		System.out.println("input : " + str);
		
		return "Hello " + str;
	}

	@Override
	public void registerDq(String[] param) throws RemoteException {
		
		String[] args = {"QP", "P1378270837214" , "프로파일RIM실행" ,"system", "홍길동", "부서명"};
		
		//외부 실행 파일 실행시 오류가 발생하므로 반드시 java 메소드 호출하여 처리하도록 해야함
		//String cmd = "C:/IDE/workspace-nhic/wisedq_nhis_scheduler/scheduler/bin/ScheduleRegistry.cmd " + "QB P1378270837214 업무규칙RIM실행 system 홍길동 부서명";
		
		System.out.println("=== START DQ RMI ===");
		System.out.println("=== param ===\n"+ param.toString());
		//DQ 스케줄러에 등록하는 클래스 호출
		//파라메터는 main 함수의 javadoc 참조
		ScheduleRegistry.main(param);
		System.out.println("=== END DQ RMI ===");
		
	}

}
