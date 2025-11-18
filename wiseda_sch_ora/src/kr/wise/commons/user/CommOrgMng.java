package kr.wise.commons.user;

import kr.wise.commons.user.dao.CommOrgDAO;

public class CommOrgMng {
	
	public static void main(String[] args) {
		if(args == null || args.length == 0) {
			System.err.println("Parameter is not vaild.");
			System.exit(1);
		} else {
			try {
				
				CommOrgDAO dao = new CommOrgDAO();
				
				dao.doCollectionCommOrg();
				
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
