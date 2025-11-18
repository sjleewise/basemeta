package kr.wise.executor.exceute.schema;


import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import kr.wise.executor.ExecutorConf;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DbCatalogRunSSH {	
	public void runSSH(String dbmsId) throws Exception {
		String host = ExecutorConf.getSsh_host();
		int port = ExecutorConf.getSsh_port();
		String username = ExecutorConf.getSsh_user();
		String password = ExecutorConf.getSsh_pass();
		
		Session session = null;
		Channel channel = null;
		
		String errMsg = "";
		
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			
			session.setPassword(password);
			
			Properties conf = new Properties();
			
			conf.put("StrictHostKeyChecking",  "no");
			session.setConfig(conf);
			
			session.connect();
			
			channel = session.openChannel("exec");
			
			ChannelExec channelExec = (ChannelExec)channel;
			
			channelExec.setCommand("sh "+ ExecutorConf.getSsh_path() + " " + dbmsId);
//			channelExec.connect();
			
			InputStream errStream = channelExec.getErrStream();// <- 일반 에러 스트림
			byte[] buffer = new byte[1024];
			 
			channelExec.connect();
			 
			while(true) {
			    while (errStream.available() > 0) {
			        int i = errStream.read(buffer, 0, 1024);
			        if (i > 0) {
			            String err = new String(buffer, 0, i); 
			            errMsg += err;
			            System.err.println(err);
			        }
			    }
			 
			    if (channel.isClosed()) {
			        if (errStream.available() > 0) {
			            continue;
			        }
			        break;
			    }
			 
			    TimeUnit.MILLISECONDS.sleep(100);
			}
			 
			int exitStatus = channel.getExitStatus();
			System.out.println("Exit Status : " + exitStatus);
			
			if(exitStatus > 0) {
				Exception e = new Exception(errMsg);
				throw e;
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			throw e;
		} finally {
			if(channel != null) 
				channel.disconnect();
			if(session != null)
				session.disconnect();
		}
		
//		return errMsg;
	}
}
