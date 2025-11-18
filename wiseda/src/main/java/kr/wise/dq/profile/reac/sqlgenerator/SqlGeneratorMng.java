package kr.wise.dq.profile.reac.sqlgenerator;

import java.util.Map;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.AltibaseV6SqlGenerator;
import kr.wise.dq.profile.sqlgenerator.mssql.MsSqlServerSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.oracle.OracleSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.mysql.MysqlSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.tibero.TiberoSqlGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.cubrid.VrfcCubridGenerator;
import kr.wise.dq.vrfcrule.sqlgenerator.postgres.VrfcPostSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.SqlGeneratorVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlGeneratorMng {

	private final  Logger logger = LoggerFactory.getLogger(getClass());
	
	public SqlGeneratorVO getSql(Map<String, Object> sqlGenMap)  {
		SqlGeneratorVO sqlVO = new SqlGeneratorVO();
		
		
		WamPrfMstrVO prfMstrVO = (WamPrfMstrVO) sqlGenMap.get("prfMstrVO");
		String dbmsTypCd = prfMstrVO.getDbmsTypCd();
		
		logger.debug("===== start sql gen : "+dbmsTypCd);
		
		//오라클 형식 SQL 생성
		if(dbmsTypCd.equals("ORA")){
			OracleSqlGenerator orclSqlGen = new OracleSqlGenerator();
			sqlVO = orclSqlGen.getSql(sqlGenMap);
		}
		//ALTIBASE 6버젼 SQL 생성
		else if(dbmsTypCd.equals("ALT")){
			AltibaseV6SqlGenerator altiV6SqlGen = new AltibaseV6SqlGenerator();
			sqlVO = altiV6SqlGen.getSql(sqlGenMap);
		}
		//Mysql SQL 생성
		else if(dbmsTypCd.equals("MYS")){
			MysqlSqlGenerator mysqlSqlGen = new MysqlSqlGenerator();
			sqlVO = mysqlSqlGen.getSql(sqlGenMap);
		}
		else if(dbmsTypCd.equals("MRA")){ 
			MysqlSqlGenerator mysqlSqlGen = new MysqlSqlGenerator();
			sqlVO = mysqlSqlGen.getSql(sqlGenMap);
		}
		//TIBERO SQL 생성
		else if(dbmsTypCd.equals("TIB")){
			TiberoSqlGenerator tiberoSqlGen = new TiberoSqlGenerator();
			sqlVO = tiberoSqlGen.getSql(sqlGenMap);
		}
		//MS SQL SERVER SQL 생성
		else if(dbmsTypCd.equals("MSQ")){
			MsSqlServerSqlGenerator msSqlServerSqlGen = new MsSqlServerSqlGenerator(); 
			sqlVO = msSqlServerSqlGen.getSql(sqlGenMap); 
		
		}else if(dbmsTypCd.equals("CBR")){ 
			OracleSqlGenerator sqlGen = new OracleSqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap);  
			
		}else if(dbmsTypCd.equals("POS")){ 
			OracleSqlGenerator sqlGen = new OracleSqlGenerator();  
			sqlVO = sqlGen.getSql(sqlGenMap);  	 
		}	

		return sqlVO;
	}


}
