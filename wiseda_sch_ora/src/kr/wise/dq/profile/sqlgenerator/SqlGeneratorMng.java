package kr.wise.dq.profile.sqlgenerator;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;
import kr.wise.dq.profile.sqlgenerator.altibaseV6.AltibaseV6SqlGenerator;
import kr.wise.dq.profile.sqlgenerator.cubrid.CubridSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.db2.Db2SqlGenerator;
import kr.wise.dq.profile.sqlgenerator.hana.HanaSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.mssql.MsSqlServerSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.mysql.MysqlSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.oracle.OracleSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.postgresql.PostSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.sybaseIQ.SybaseIQSqlGenerator;
import kr.wise.dq.profile.sqlgenerator.tibero.TiberoSqlGenerator;

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
		//TIBERO SQL 생성
		else if(dbmsTypCd.equals("TIB")){
			TiberoSqlGenerator tiberoSqlGen = new TiberoSqlGenerator();
			sqlVO = tiberoSqlGen.getSql(sqlGenMap);
		}
		//MS SQL SERVER SQL 생성
		else if(dbmsTypCd.equals("MSQ")){
			MsSqlServerSqlGenerator msSqlServerSqlGen = new MsSqlServerSqlGenerator(); 
			sqlVO = msSqlServerSqlGen.getSql(sqlGenMap); 
		}
		//postgresql
		else if(dbmsTypCd.equals("POS")){ 
			PostSqlGenerator sqlGen = new PostSqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap);
		}
		//cubrid
		else if(dbmsTypCd.equals("CBR")){ 
			CubridSqlGenerator sqlGen = new CubridSqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}
		else if(dbmsTypCd.equals("DB2")){
			Db2SqlGenerator sqlGen = new Db2SqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}
		//sybaseIQ	
		else if(dbmsTypCd.equals("SYQ")){ 
			SybaseIQSqlGenerator sqlGen = new SybaseIQSqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}
		else if(dbmsTypCd.equals("MRA")){
			MysqlSqlGenerator mysqlSqlGen = new MysqlSqlGenerator();
			sqlVO = mysqlSqlGen.getSql(sqlGenMap);
		}
		else if(dbmsTypCd.equals("HAN")){
			HanaSqlGenerator sqlGen = new HanaSqlGenerator();
			sqlVO = sqlGen.getSql(sqlGenMap); 
		}
		return sqlVO;
	}


}
