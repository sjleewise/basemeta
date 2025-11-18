package kr.wise.commons;


public class QueryUtils {

	public static String convertRowLimitQuery(String dbmsTypeCd, String query, int limitRow, String exeType, String prfKind) throws Exception {
		if(dbmsTypeCd == null || query == null) {
			throw new Exception("DbmsTypeCd : " + dbmsTypeCd + ", query : " + query == null ? "null" : "not null");
		}
		
		System.out.println("dbmsTypeCd:{}" + dbmsTypeCd);
		
		StringBuilder sb = new StringBuilder();
		if("ORA".equals(dbmsTypeCd) || "ALT".equals(dbmsTypeCd) || "TIB".equals(dbmsTypeCd)) {	// 오라클, 알티베이스, 티베로
			sb.append("\n SELECT * FROM ( \n");
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY COUNT DESC \n");
			}
			sb.append("\n ) ");
			//컬럼분석
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n WHERE ROWNUM <= ").append(limitRow);
			}
		} else if("SYQ".equals(dbmsTypeCd) || "SYA".equals(dbmsTypeCd)) {	// 사이베이스
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n SET ROWCOUNT ").append(limitRow).append(" \n");
			}
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY COUNT DESC \n");
			}
			sb.append("\n SET ROWCOUNT 0 \n");
		} else if("MSQ".equals(dbmsTypeCd)) {		// MSSQL
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n SELECT TOP ").append(limitRow).append(" * FROM ( \n");
			} 
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				// sb.append("\n ORDER BY COUNT DESC \n"); 
			}
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n ) X ");
			} 
		} else if("DB2".equals(dbmsTypeCd)) {		// DB2
			sb.append(query);
//			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
//				sb.append("\n ORDER BY CNT DESC \n");
//			}
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n FETCH FIRST ").append(limitRow).append(" ROWS ONLY ");
			}
		} else if("MYS".equals(dbmsTypeCd) || "MRA".equals(dbmsTypeCd)) {		// MySQL
			sb.append("\n SELECT * FROM ( \n");
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY COUNT DESC \n");
			}
			sb.append("\n ) A ");
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n LIMIT 0, ").append(limitRow);
			}
		} else if("T".equals(dbmsTypeCd)) {		// Tera
			
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n SELECT TOP ").append(limitRow).append(" * FROM ( \n");
			} else {
				sb.append("\n SELECT  * FROM ( \n");
			}
			sb.append(query);
			sb.append("\n ) A ");
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY \"COUNT\" DESC \n");
			}
		} else if("CBR".equals(dbmsTypeCd) ) {	// CUBRID
			sb.append("\n SELECT * FROM ( \n");
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY \"COUNT\" DESC \n");
			}
			sb.append("\n ) ");
			//컬럼분석
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n WHERE ROWNUM <= ").append(limitRow);
			}
		} else if("POS".equals(dbmsTypeCd) ) {	// POSTGRESQL
			sb.append("\n SELECT * FROM ( \n");
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY \"COUNT\" DESC \n");
			}
			sb.append("\n ) aa");
			//컬럼분석
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n LIMIT ").append(limitRow);
			}
		} else if("HAN".equals(dbmsTypeCd)) {
			//컬럼분석
			if(!prfKind.equals(DQConstant.G_COLUMN_ANALYSIS)) {
				sb.append("\n SELECT TOP " + limitRow + " * FROM ( \n");
			} else {
				sb.append("\n SELECT * FROM ( \n");
			}
			
			sb.append(query);
			if(exeType.equals(DQConstant.EXE_TYPE_02_CD)){
				sb.append("\n ORDER BY COUNT DESC \n");
			}
			sb.append("\n ) ");
		} else {
			throw new Exception("DbmsTypeCd is not exist. - " + dbmsTypeCd);
		}
		return sb.toString();
	}
}
