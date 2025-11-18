package kr.wise.commons.user;

import kr.wise.commons.user.dao.CommOrgMtaDAO;

public class CommOrgMtaMng {
	public static void main(String[] args) {
		if(args == null || args.length == 0) {
			System.err.println("Parameter is not vaild.");
			System.exit(1);
		} else {
			try {
				
				CommOrgMtaDAO dao = new CommOrgMtaDAO();
				
				dao.doCollectionCommOrgMta();
				
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
