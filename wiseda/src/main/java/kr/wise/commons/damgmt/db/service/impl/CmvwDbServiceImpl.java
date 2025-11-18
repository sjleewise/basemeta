package kr.wise.commons.damgmt.db.service.impl;

/**
 * 0. Project  : WDML 프로젝트
 *
 * 1. FileName : CmvwDbService.java
 * 2. Package : kr.wise.cmvw.db.service
 * 3. Comment :
 * 4. 작성자  : jwoolee(이정우)
 * 5. 작성일  : 2013. 5. 28.
 * 6. 변경이력 :
 *    이름		: 일자			: 변경내용
 *    ------------------------------------------------------
 *    jwoolee 	: 2013. 5. 28.	: 신규 개발.
 */

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

//import seed.*;
import kr.wise.commons.WiseConfig;
import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.cmm.security.UtilEncryption;
import kr.wise.commons.cmm.service.EgovIdGnrService;
import kr.wise.commons.damgmt.db.service.CmvwDbService;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgMapper;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;
import kr.wise.commons.damgmt.db.service.WaaDbRole;
import kr.wise.commons.damgmt.db.service.WaaDbRoleMapper;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.util.ConnectionHelper;
import kr.wise.commons.util.CubridSchemaCollect;
import kr.wise.commons.util.TargetDbmsDM;
import kr.wise.commons.util.UtilObject;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSch;
import kr.wise.dq.criinfo.anatrg.service.WaaDbSchMapper;
import kr.wise.meta.ddl.service.WamDdlTbl;
import kr.wise.meta.ddl.service.WamDdlTblMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.MongoClient;
/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : CmvwDbServiceImpl.java
 * 3. Package  : kr.wise.commons.damgmt.db.service.impl
 * 4. Comment  :
 * 5. 작성자   : yeonho
 * 6. 작성일   : 2014. 4. 27. 오후 4:36:01
 * </PRE>
 */
@Service("CmvwDbService")
public class CmvwDbServiceImpl implements CmvwDbService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Inject
	private WaaDbConnTrgMapper mapper;

	@Inject
	private WaaDbSchMapper schMapper;
	
	@Inject
	private WamDdlTblMapper wamDdlTblMapper;

	@Inject
	private EgovIdGnrService objectIdGnrService;
	
	@Inject
	private WaaDbRoleMapper roleMapper;


	public List<WaaDbConnTrgVO> getDbConnTrgList(WaaDbConnTrgVO search) {
		List<WaaDbConnTrgVO> list = mapper.selectList(search);

		return list;
	}

	public WaaDbConnTrgVO findDb(WaaDbConnTrgVO search) {

		return mapper.selectByPrimaryKey(search.getDbConnTrgId());
	}

	/** @param record
	/** @return yeonho
	 * @throws Exception */
	public int regDbConnTrg(WaaDbConnTrgVO record) throws Exception {
		String tmpStatus = record.getIbsStatus();
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
        String encPw = record.getDbConnAcPwd();
        String decPw="";

		//DB ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if("I".equals(tmpStatus) && !StringUtils.hasText(record.getDbConnTrgId())) {  // 신규...
			record.setDbConnTrgId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);
			//신규일 경우패스워드 암호화 20151228 이상익
			if(WiseConfig.SECURITY_APPLY.equals("Y")){
			//   encPw = seed.EncryptUtils.getEncData(record.getDbConnAcPwd(), record.getDbConnAcId());
			   logger.debug("암호화된 패스워드 : "+encPw);
			   record.setDbConnAcPwd(encPw);
			}
			
		} else if("U".equals(tmpStatus) || StringUtils.hasText(record.getDbConnTrgId())) {
//			//DB ID를 기반으로 데이터가 있는지 확인하여 없을경우 신규처리..
//			WaaDbConnTrgVO tmpVO = mapper.selectByPrimaryKey(record.getDbConnTrgId());
//			if (tmpVO == null || !tmpVO.getDbConnTrgId().equals(record.getDbConnTrgId())) {
//				record.setDbConnTrgId(objectIdGnrService.getNextStringId());
//				record.setObjVers(1);
//			} else {
//				if (UtilObject.isNull(record.getObjVers())) {
//					record.setObjVers(1);
//				}
//				else {
//					record.setObjVers(record.getObjVers()+1);
//				}
			if(WiseConfig.SECURITY_APPLY.equals("Y")){
				  //decPw = EncryptUtils.getDecData(record.getDbConnAcPwd(), record.getDbConnAcId());
				  if(decPw==null||decPw.equals("")){
			      //   record.setDbConnAcPwd(seed.EncryptUtils.getEncData(record.getDbConnAcPwd(), record.getDbConnAcId()));
				  }
			}
				record.setObjVers(record.getObjVers()+1);
				record.setRegTypCd("U");
				mapper.updateExpDtm(record);
//			}
		}

		record.setWritUserId(user.getUniqId());
		
		// 메타관리여부 항목이 빈값일 경우 default "아니요" 셋팅
		if(record.getMetaMngYn() == null || record.getMetaMngYn().equals("")){
			record.setMetaMngYn("N");
		}
		
		result = mapper.insertSelective(record);
		
		//신규 등록, 수정등이 이루어질 경우 테스트를 다시 해야하므로, 연결상태를 null로 초기화한다.
		record.setDbLnkSts(null);
		mapper.updateConnTest(record);
		return result;
	}

	public String chkDbConnTrg(WaaDbConnTrgVO record) {
		String result = "성공";
		try {
			Driver driver = (Driver)Class.forName(record.getConnTrgDrvrNm()).newInstance();

	        Properties prop = new Properties();
	        prop.put("user", record.getDbConnAcId());
	        prop.put("password", record.getDbConnAcPwd());
	        prop.put("encoding", "KSC5601");

	    	driver.connect(record.getConnTrgLnkUrl(), prop);

		} catch(Exception e) {
			result = e.getMessage();
		}
		return result;
	}


	/** yeonho
	 * @throws Exception */
	public int regDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception {
		int result = 0;
		for (WaaDbConnTrgVO waaDbConnTrg : list) {
			
			String tmpStatus = waaDbConnTrg.getIbsStatus();
			int row = mapper.selectCntDBMS(waaDbConnTrg);
			
			if("I".equals(tmpStatus) && row >= 1) {
				return -2;
			}
			
			result += regDbConnTrg(waaDbConnTrg);
		}
		return result;
	}


	/**
	 * <PRE>
	 * 1. MethodName : delDbList
	 * 2. Comment    : 체크 상태인 리스트를 삭제상태로 변경 후 저장
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 5. 28.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception
	 */
	public int delDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception {
		int result = 0;
		
		for (WaaDbConnTrgVO waaDbConnTrg : list) {
			
			String id = waaDbConnTrg.getDbConnTrgId();
			
			//스키마 정보 존재 여부 확인..
			List<WaaDbSch> lstSch = schMapper.selectListByFK(id);
			for(WaaDbSch schVo : lstSch){
				//스키마 삭제후 DBMS삭제 가능합니다.
				return -2;
			} 
			
			if (id != null && !"".equals(id)) {
				result += mapper.updateExpDtm(waaDbConnTrg);
			}
		}
		
		return result;
	}

	/**
	 * <PRE>
	 * 1. MethodName : delDbList
	 * 2. Comment    : 체크 상태인 리스트를 삭제상태로 변경 후 저장
	 * 3. 작성자       : jwoolee(이정우)
	 * 4. 작성일       : 2013. 5. 28.
	 * </PRE>
	 *   @return void
	 *   @param list
	 * @throws Exception
	 */
	public void chkDbConnTrgList(ArrayList<WaaDbConnTrgVO> list) throws Exception {
		for (WaaDbConnTrgVO waaDbConnTrg : list) {
			String id = waaDbConnTrg.getDbConnTrgId();
			if (id != null && !"".equals(id)) {
				waaDbConnTrg.setIbsStatus("U");
				waaDbConnTrg.setDbLnkSts(chkDbConnTrg(waaDbConnTrg));
				regDbConnTrg(waaDbConnTrg);
			}
		}

	}

	@Override
	public List<WaaDbSch> getDbSchList(String dbConnTrgId) {
		return schMapper.selectListByFK(dbConnTrgId);
	}

	/** yeonho
	 * @throws Exception */
	@Override
	public int regDbSchList(ArrayList<WaaDbSch> list) throws Exception {
		int result = 0;

		for (WaaDbSch record : list) {

			// 그리드 상태가 있을 경우만 DB에 처리한다...
			if (!UtilString.isBlank(record.getIbsStatus())) {
				result += regDbSch(record);
			}

		}

		return result;
	}

	@Override
	public int regDbSch(WaaDbSch record) throws Exception {
		logger.debug("regDbSch method");
		boolean isNew = true;
		LoginVO user = (LoginVO)UserDetailHelper.getAuthenticatedUser();
		int result = 0;
		
		//DBMS접속정보 ID에 대한 검증
		if(record.getDbConnTrgId() != null && !record.getDbConnTrgId().equals("")){
			WaaDbConnTrgVO tmpDbConnTrgVO = mapper.selectByPrimaryKey(record.getDbConnTrgId());
			
			// 정보가 존재하지 않는 경우 - ("존재하지 않는 DBMS접속정보입니다. <br>값을 확인해주세요.") alert 띄우기 
			if(tmpDbConnTrgVO == null){
				return -3;
				
			} else {
				if(record.getDbConnTrgPnm() != null && !record.getDbConnTrgPnm().equals("")){
					if(!tmpDbConnTrgVO.getDbConnTrgPnm().equals(record.getDbConnTrgPnm())){
						return -3;
					}
				} else {
					// 정보가 존재하는 경우 DBMS ID 셋팅
					record.setDbConnTrgId(tmpDbConnTrgVO.getDbConnTrgId());
				}
			}
			
		} else{
			//DBMS접속정보 ID가 없는경우 DBMS물리명으로 존재여부 확인
			WaaDbConnTrgVO tmpDbConnTrgVO = mapper.selectByDbConnTrgPnm(record.getDbConnTrgPnm());
			
			// 정보가 존재하지 않는 경우 - ("존재하지 않는 DBMS접속정보입니다. <br>값을 확인해주세요.") alert 띄우기 
			if(tmpDbConnTrgVO == null){
				return -3;
				
			}else {
			// 정보가 존재하는 경우 DBMS ID값 셋팅
				record.setDbConnTrgId(tmpDbConnTrgVO.getDbConnTrgId());
			}
		}
		
		//엑셀업로드시 작성한 스키마 Id에 대한 검증 -- 없으면 신규처리, 있으면 변경처리
		if(record.getDbSchId() != null && !record.getDbSchId().equals("")) {
			WaaDbSch tmpDbSch = schMapper.selectByPrimaryKey(record.getDbSchId());

			if(null == tmpDbSch || !tmpDbSch.getDbSchId().equals(record.getDbSchId())) {
				isNew = true;
				record.setDbSchId(null);
			} else {
				isNew = false;
			}
		}else if((record.getDbSchId() == null || record.getDbSchId().equals("")) && (record.getDbSchPnm() != null && !record.getDbSchPnm().equals(""))){
			WaaDbSch tmpDbSch = schMapper.selectBySchPnm(record.getDbConnTrgId(), record.getDbSchPnm());
			
			if(null == tmpDbSch || !tmpDbSch.getDbSchPnm().equals(record.getDbSchPnm())) {
				isNew = true;
				record.setDbSchId(null);
			} else{
				isNew = false;
				record.setDbSchId(tmpDbSch.getDbSchId());
			}
		}else {
			isNew = true;
			record.setDbSchId(null);
		}
		
		record.setWritUserId(user.getUniqId());
		//스키마ID가 없을경우 신규로, 그렇지 않을경우 수정으로 처리
		if(isNew) {  // 신규...

			record.setDbSchId(objectIdGnrService.getNextStringId());
			record.setRegTypCd("C");
			record.setObjVers(1);


		} else if(!isNew && record.getIbsStatus().equals("D")) {  // 삭제...
			
			//========삭제시 DDL테이블 체크=============
			WamDdlTbl ddlVo = new WamDdlTbl();  
			
			ddlVo.setDbSchId(record.getDbSchId()); 
			
			List<WamDdlTbl> lstDdl= wamDdlTblMapper.selectList(ddlVo); 
			
			for(WamDdlTbl tmpVo: lstDdl ) {
				
				return -2; 
			} 			
			//============================================
			
			schMapper.updateExpDtm(record.getDbSchId());
			return 1;
		} else {  // 변경...

			record.setRegTypCd("U");

			record.setRqstUserId(user.getUniqId());
			if (UtilObject.isNull(record.getObjVers())) {
				record.setObjVers(1);
			}
			else {
				record.setObjVers(record.getObjVers()+1);
			}


			schMapper.updateExpDtm(record.getDbSchId()); //기존 데이터를 삭제...
		}
        if(record.getDbTblSpacPnm()==null||record.getDbTblSpacPnm().equals("")){
           record.setDbTblSpacId(null) ;
        }
        if(record.getDbIdxSpacPnm()==null||record.getDbIdxSpacPnm().equals("")){
           record.setDbIdxSpacId(null) ;
        }
        if(record.getDbTblSpacId()==null || record.getDbTblSpacId().equals("")){
        	record.setDbTblSpacId(null);
        }
        if(record.getDbIdxSpacId()==null || record.getDbIdxSpacId().equals("")){
        	record.setDbIdxSpacId(null);
        }        
		
		// DDL대상여부 항목이 빈값일 경우 default '예' 셋팅
		if(record.getDdlTrgYn() == null || record.getDdlTrgYn().equals("")){
			record.setDdlTrgYn("Y");
		}
		// DDL대상구분코드 항목이 빈값일 경우 default '개발' 셋팅
		if(record.getDdlTrgDcd() == null || record.getDdlTrgDcd().equals("")){
			record.setDdlTrgDcd("D");
		}
		// 컬럼프로파일여부 항목이 빈값일 경우 default '아니요' 셋팅
		if(record.getColPrfYn() == null || record.getColPrfYn().equals("")){
			record.setColPrfYn("N");
		}
        
		result = schMapper.insertSelective(record);
		return result;
	}

	@Override
	public int delDbSchList(ArrayList<WaaDbSch> list) throws Exception {
		int result = 0;

		for (WaaDbSch record : list) {

			// 그리드 상태가 있을 경우만 DB에 처리한다...
			if (!UtilString.isBlank(record.getIbsStatus())) {
				record.setIbsStatus("D");
				result += regDbSch(record);
			}

		}

		return result;
	}

	/** insomnia */
	public List<WaaDbSch> getDbSchemaList(WaaDbSch search) {

		return schMapper.selectSchemaList(search);
	}

	/** yeonho 
	 * @throws Exception 
	 * @throws SQLException */
	@Override
	public int dbConnTrgConnTest(ArrayList<WaaDbConnTrgVO> list) throws SQLException, Exception {
		int result = 0;

		for (WaaDbConnTrgVO record : list) {

			// 그리드 상태가 있을 경우만 DB에 처리한다...
			if (!UtilString.isBlank(record.getIbsStatus())) {
//				chkDbConnTrg(record);
				result += ConnTestDb(record);
			}

		}

		return result;
	}

	/** @param record
	/** @return yeonho 
	 * @throws Exception 
	 * @throws SQLException */
	private int ConnTestDb(WaaDbConnTrgVO record) throws SQLException, Exception {
		Connection tgtCon = null;
		MongoClient mongoClient = null;
		try{
			if(record.getDbmsTypCd().equals("MDB"))
				mongoClient = ConnectionHelper.getMongoConnection(record.getConnTrgLnkUrl(), record.getDbConnAcId(), record.getDbConnAcPwd());
			else
				tgtCon = ConnectionHelper.getConnection(record.getConnTrgDrvrNm(), record.getConnTrgLnkUrl(), record.getDbConnAcId(), record.getDbConnAcPwd());
			record.setDbLnkSts("성공");
			record.setDbLnkStsCtns("");
			mapper.updateConnTest(record);
		} catch(Exception e) {
			record.setDbLnkSts("접속실패");
			record.setDbLnkStsCtns(e.getMessage());
			mapper.updateConnTest(record);
			logger.debug("errorMessage : {}", e.getMessage());
			
		} finally {
			
		}
		
		return 1;
		
	}

	@Override
	public List<WaaDbSch> getDevSubjDbSchemaList(WaaDbSch search) {
		
		return  schMapper.selectDevSubjSchemaList(search); 
	}
	
	@Override
	public List<WaaDbRole> getDbRoleList(WaaDbRole search) {
		
		return roleMapper.selectRoleList(search);
	}

	@Override
	public void getSchema(ArrayList<WaaDbConnTrgVO> list) throws SQLException, Exception {
		CubridSchemaCollect col = null;
		
		
		WaaDbSch search = null;
		List<WaaDbSch> schList = null;
		
		TargetDbmsDM dm = null;
		List<TargetDbmsDM> lsitdm = null;
		Connection source = null;
		Connection target = null;
		try {
			source = ConnectionHelper.getConnection("cubrid.jdbc.driver.CUBRIDDriver"
					, "jdbc:cubrid:localhost:30000:ngccmeta:::?charset=utf-8"
					, "WISEDA"
					, "wise1012");
			
			for (WaaDbConnTrgVO vo : list) {
				dm = new TargetDbmsDM();			
				lsitdm = new ArrayList<TargetDbmsDM>();
				search = new WaaDbSch();
				
				target = ConnectionHelper.getConnection(vo.getConnTrgDrvrNm()
						, vo.getConnTrgLnkUrl()
						, vo.getDbConnAcId()
						, vo.getDbConnAcPwd());
				
				search.setDbConnTrgPnm(vo.getDbConnTrgPnm());
				
				schList = schMapper.selectSchemaList(search);
				
				for (WaaDbSch sch : schList) {
					dm.setDbc_owner_nm(sch.getDbSchPnm());
					dm.setDbSchId(sch.getDbSchId());
					dm.setDbms_enm(sch.getDbConnTrgPnm());
					dm.setDbms_type_cd(sch.getDbmsTypCd());
					
					lsitdm.add(dm);
				}		
				
				col = new CubridSchemaCollect(source, target, lsitdm);
				col.doProcess();
				
				col.deleteWat(vo.getDbConnTrgId());
				
				col.saveWat(dm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}  finally {
			if(source != null) {source.close();};
			if(target != null) {target.close();};
		}
		
		
		
	}

}
