package kr.wise.meta.mmart;

import kr.wise.meta.mmart.dao.Mmart9WaeErwinDAO;

 

/**
 * 기술표준원 코드분류체계 interface class
 */
public class Mmart9WaeErwinMng {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {   
		
		try{
			
			
			Mmart9WaeErwinDAO dao = new Mmart9WaeErwinDAO();
			
			dao.doCollectErwinCol();  
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
