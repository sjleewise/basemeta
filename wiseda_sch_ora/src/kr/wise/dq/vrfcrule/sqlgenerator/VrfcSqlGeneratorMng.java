package kr.wise.dq.vrfcrule.sqlgenerator;

import java.util.Map;

import kr.wise.dq.vrfcrule.service.VrfcRuleVO;
import kr.wise.dq.vrfcrule.sqlgenerator.altibaseV6.VrfcAltibaseV6SqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.cubrid.VrfcCubridGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.db2Udb.VrfcDb2UdbGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.maria.VrfcMariaSqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.mssql.VrfcMsSqlServerSqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.mysql.VrfcMysqlSqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.oracle.VrfcOracleSqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.postgres.VrfcPostSqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.tibero.VrfcTiberoSqlGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VrfcSqlGeneratorMng {

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	public VrfcSqlGeneratorVO getSql(Map<String, Object> sqlGenMap)  {
		
		VrfcSqlGeneratorVO sqlVO = new VrfcSqlGeneratorVO();
				
		VrfcRuleVO vrfcRuleVO = (VrfcRuleVO) sqlGenMap.get("vrfcVO");
		String dbmsTypCd = vrfcRuleVO.getDbmsTypCd(); 
		
		logger.debug("===== start sql gen : "+dbmsTypCd);
		
		//오라클 형식 SQL 생성
		if(dbmsTypCd.equals("ORA")){
			VrfcOracleSqlGenerator sqlGen = new VrfcOracleSqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}
		//ALTIBASE 6버젼 SQL 생성
		else if(dbmsTypCd.equals("ALT")){
			VrfcAltibaseV6SqlGenerator sqlGen = new VrfcAltibaseV6SqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}
		//Mysql SQL 생성
		else if(dbmsTypCd.equals("MYS")){
			VrfcMysqlSqlGenerator sqlGen = new VrfcMysqlSqlGenerator(); 
			sqlVO = sqlGen.getSql(sqlGenMap);
		}		
		//Maria SQL 생성
		else if(dbmsTypCd.equals("MRA")){
			VrfcMariaSqlGenerator sqlGen = new VrfcMariaSqlGenerator(); 
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}		
		//MS SQL SERVER SQL 생성
		else if(dbmsTypCd.equals("MSQ")){ 
			VrfcMsSqlServerSqlGenerator sqlGen = new VrfcMsSqlServerSqlGenerator();  
			sqlVO = sqlGen.getSql(sqlGenMap);
			
		}else if(dbmsTypCd.equals("CBR")){ 
			VrfcCubridGenerator sqlGen = new VrfcCubridGenerator();  
			sqlVO = sqlGen.getSql(sqlGenMap);  
			
		}else if(dbmsTypCd.equals("POS")){ 
			VrfcPostSqlGenerator sqlGen = new VrfcPostSqlGenerator();  
			sqlVO = sqlGen.getSql(sqlGenMap);  	 
		}//TIBERO SQL 생성
		else if(dbmsTypCd.equals("TIB")){ //PRF -> VRFC로 변경 (ora와 동일함)
			VrfcTiberoSqlGenerator tiberoSqlGen = new VrfcTiberoSqlGenerator();
			sqlVO = tiberoSqlGen.getSql(sqlGenMap);
//			TiberoSqlGenerator tiberoSqlGen = new TiberoSqlGenerator();
//			sqlVO = tiberoSqlGen.getSql(sqlGenMap);
		}
		else if(dbmsTypCd.equals("UDB")){
			VrfcDb2UdbGenerator sqlgen = new VrfcDb2UdbGenerator();
			sqlVO = sqlgen.getSql(sqlGenMap);
		}

		return sqlVO;
	}


}
