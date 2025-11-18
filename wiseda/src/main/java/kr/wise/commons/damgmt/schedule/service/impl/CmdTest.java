package kr.wise.commons.damgmt.schedule.service.impl;

import java.io.IOException;

public class CmdTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Process process = null;
		Process process2 = null;
		String schedulerpath = "start.cmd";
		
		try {
			//코드분류체계이관 shell 경로
			String cmd = "cmd /c start cd \"C:/ide/basemeta/wiseda_sch/scheduler/bin/\" && dir";// + schedulerpath;
			System.out.println(cmd);
//			process = new ProcessBuilder("cmd", "/c", "cd C:/ide/basemeta/wiseda_sch/scheduler/bin && start start.cmd").start();
			process = new ProcessBuilder("/bin/sh", "-c", "cd /usr/local/wiseda_base/tomcat7/webapps/wiseda_base_sch/scheduler/bin && start ./stop.sh").start();
			// 서버가 윈도우인 경우 버퍼 해소
//			CStreamGobbler out   = new CStreamGobbler(process.getInputStream(), "OUT");
//			CStreamGobbler error = new CStreamGobbler(process.getErrorStream(), "ERROR");
//
//			out.start();
//			error.start();

			int exitValue = process.waitFor();
			
			System.out.println("ExitValue : " + exitValue);
			
			
			if(exitValue != 0) {
				throw new Exception("Runtime error.("+exitValue+")");
			}
			
		} catch (InterruptedException e) {
			System.out.println("" + e);
			throw new Exception(e);
		} catch(IOException e) {
			System.out.println("" + e);
			throw new Exception(e);
		} catch(Exception e) {
			System.out.println("" + e);
			throw e;
		} finally {
			if(process != null) try { process.destroy(); } catch(Exception ignored) {}
		}
	}

}
