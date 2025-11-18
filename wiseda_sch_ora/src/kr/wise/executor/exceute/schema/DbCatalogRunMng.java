package kr.wise.executor.exceute.schema;

import java.sql.SQLException;

import kr.wise.executor.dao.TargetDbmsDAO;
import kr.wise.executor.dm.TargetDbmsDM;

import org.apache.log4j.Logger;

/**
 * 기술표준원 코드분류체계 interface class
 */
public class DbCatalogRunMng {

    private static final Logger logger = Logger.getLogger(DbCatalogRunMng.class);

    /**
     * @param args
     * @throws SQLException
     */
    
    //DB 스키마수집 직접 실행
    
    public static void main(String[] args) throws SQLException {

       
        try {

            String dbmsId = "";
            
            dbmsId = args[0];
            
//            dbmsId = "OBJ_00000007002"; //PMIS_DEV
//            dbmsId = "OBJ_00000129548"; //PMIS_PRD
            
            // 수집대상 DB정보 조회
            TargetDbmsDAO targetDbmsDAO = new TargetDbmsDAO();
            TargetDbmsDM targetDbmsDM = targetDbmsDAO.selectTargetDbms(dbmsId);

            DbCatalogManager schemaRun = new DbCatalogManager(targetDbmsDM);
            
            schemaRun.launch();
           

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {

           
        }
    }

    
}
