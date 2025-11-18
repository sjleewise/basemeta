/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlScriptService.java
 * 2. Package : kr.wise.meta.ddl.script.service
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 21. 오후 5:28:41
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.advisor.prepare.udefoutlier.script;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import kr.wise.advisor.prepare.udefoutlier.service.WadUod;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlTbl;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlScriptService.java
 * 3. Package  : kr.wise.meta.ddl.script.service
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 21. 오후 5:28:41
 * </PRE>
 */
public interface PythonScriptService {

	public int scriptFileCre(String udfOtlDtcId) throws Exception; 
	
	/* @throws IOException */
	String getUdefOutlierScript(String udfOtlDtcId) throws Exception; 

	public WadUod insertLinkInfo(String udfOtlDtcId) throws Exception;
}
