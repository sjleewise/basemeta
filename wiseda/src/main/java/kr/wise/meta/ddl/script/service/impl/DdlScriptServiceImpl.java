/**
 * 0. Project  : WISE DA 프로젝트
 *
 * 1. FileName : DdlScriptServiceImpl.java
 * 2. Package : kr.wise.meta.ddl.script.service.impl
 * 3. Comment :
 * 4. 작성자  : insomnia
 * 5. 작성일  : 2014. 5. 21. 오후 5:29:18
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    insomnia : 2014. 5. 21. :            : 신규 개발.
 */
package kr.wise.meta.ddl.script.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilString;
import kr.wise.meta.ddl.script.model.Database;
import kr.wise.meta.ddl.script.model.EtcObject;
import kr.wise.meta.ddl.script.model.Grant;
import kr.wise.meta.ddl.script.model.Sequence;
import kr.wise.meta.ddl.script.model.Table;
import kr.wise.meta.ddl.script.platform.SqlBuilder;
import kr.wise.meta.ddl.script.platform.altibase.Altibase6Builder;
import kr.wise.meta.ddl.script.platform.cubrid.Cubrid10Builder;
import kr.wise.meta.ddl.script.platform.db2.DB2Builder;
import kr.wise.meta.ddl.script.platform.db2.UDBBuilder;
import kr.wise.meta.ddl.script.platform.informix.Informix4Builder;
import kr.wise.meta.ddl.script.platform.maria.MariaDbBuilder;
import kr.wise.meta.ddl.script.platform.mssql.Mssql2017Builder;
import kr.wise.meta.ddl.script.platform.mysql.Mysql6Builder;
import kr.wise.meta.ddl.script.platform.oracle.Oracle10Builder;
import kr.wise.meta.ddl.script.platform.postgresql.Postgresql12Builder;
import kr.wise.meta.ddl.script.platform.sybase.SybaseASEBuilder;
import kr.wise.meta.ddl.script.platform.sybase.SybaseIQBuilder;
import kr.wise.meta.ddl.script.platform.teradata.TeradataBuilder;
import kr.wise.meta.ddl.script.platform.tibero.Tibero6Builder;
import kr.wise.meta.ddl.script.service.DdlScriptMapper;
import kr.wise.meta.ddl.script.service.DdlScriptService;
import kr.wise.meta.ddl.service.WaqDdlGrt;
import kr.wise.meta.ddl.service.WaqDdlIdx;
import kr.wise.meta.ddl.service.WaqDdlPart;
import kr.wise.meta.ddl.service.WaqDdlSeq;
import kr.wise.meta.ddl.service.WaqDdlTbl;
import kr.wise.meta.ddletc.service.WaqDdlEtc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 1. ClassName :
 * 2. FileName  : DdlScriptServiceImpl.java
 * 3. Package  : kr.wise.meta.ddl.script.service.impl
 * 4. Comment  :
 * 5. 작성자   : insomnia
 * 6. 작성일   : 2014. 5. 21. 오후 5:29:18
 * </PRE>
 */
@Service("ddlScriptService")
public class DdlScriptServiceImpl implements DdlScriptService { 

	private final Logger logger = LoggerFactory.getLogger(getClass()); 

	@Inject
	private DdlScriptMapper mapper;

	@Inject
	private MessageSource message;


	/** insomnia
	 * @throws IOException */
	public String getDdlScriptTable(String ddlTblId) throws IOException {

		logger.debug("ddltblid:{}", ddlTblId);

		//테이블 조회
		Table table = mapper.selectTableOne(ddlTblId);
		
		
		if(table == null) {

//			return "DDL 테이블과 관련된 컬럼정보 또는 DB정보가 없습니다.";
			return message.getMessage("ERR.DDL.NODATA", null, null);
		}

		logger.debug("table:{}", table);

		/** sql결과 */
		StringWriter _writer = null;
		
		_writer = new StringWriter();;

		Database database = new Database();

		SqlBuilder sqlbuild = null ;

		//DBMS 종류 분기
		String dbtype = null;
		
		dbtype = UtilString.null2Blank(table.getType());
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);

		logger.debug("script 1");
		sqlbuild.setWriter(_writer);
		logger.debug("script 2");

//		database.addTable(table);

		//테이블 DDL 설명 생성
		sqlbuild.writeTableComment(table);
		
		sqlbuild.dropTable(table);

		if(!table.getRegTypCd().equals("D")){
			sqlbuild.createTable(database, table); 
			sqlbuild.createComment(table);
		}
		
		//인덱스 조회
//		List<Table> idxTbl = mapper.selectTableIdxList(ddlTblId);  
//
//		for(Table idx : idxTbl){
//		
//			//인덱스 생성 
//			sqlbuild.createIndex(idx);
//		}
				
		// 권한 생성
		sqlbuild.createGrants(table);		

		return _writer.toString();
	}
	
	public String getDdlScriptTable(String ddlTblId, String creDrpDcd) throws IOException {

		logger.debug("ddltblid:{}", ddlTblId);

		//테이블 조회
		Table table = mapper.selectTableOne(ddlTblId);
//		Table tableForAlt = mapper.selectTableOneForAlter(ddlTblId);
		
		
		if(table == null) {

//			return "DDL 테이블과 관련된 컬럼정보 또는 DB정보가 없습니다.";
			return message.getMessage("ERR.DDL.NODATA", null, null);
		}

		logger.debug("table:{}", table);

		/** sql결과 */
		StringWriter _writer = null;
		
		_writer = new StringWriter();;

		Database database = new Database();

		SqlBuilder sqlbuild = null ;

		//DBMS 종류 분기
		String dbtype = null;
		
		dbtype = UtilString.null2Blank(table.getType());
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);

		logger.debug("script 1");
		sqlbuild.setWriter(_writer);
		logger.debug("script 2");

//		database.addTable(table);

		//테이블 DDL 설명 생성
		sqlbuild.writeTableComment(table);
		
		if(UtilString.null2Blank(table.getRegTypCd()).equals("D")) {
			sqlbuild.dropTable(table);
			
			return _writer.toString();
		} 
		
		if("BAK".equals(creDrpDcd)){
			if(!"C".equals(table.getRegTypCd())) {
				sqlbuild.createBackupTable(database, table);
				sqlbuild.createComment(table);
				sqlbuild.dataCopy(table);
				sqlbuild.dropBackupTable(table);
			}	
		} else if("CRE".equals(creDrpDcd)) {
			sqlbuild.createTable(database, table); 
			sqlbuild.createComment(table);
		} else if("DRP".equals(creDrpDcd)) {
			sqlbuild.dropTable(table);
			
			sqlbuild.createTable(database, table); 
			sqlbuild.createComment(table);
		} else if("ALT".equals(creDrpDcd)) {
			table.setRqstDcd("DD");
			sqlbuild.alterTable(table);
		} else {
			sqlbuild.dropTable(table);
			
			sqlbuild.createTable(database, table); 
			sqlbuild.createComment(table);
		}
		
		
		//인덱스 조회
//		List<Table> idxTbl = mapper.selectTableIdxList(ddlTblId);  
//
//		for(Table idx : idxTbl){
//		
//			//인덱스 생성 
//			sqlbuild.createIndex(idx);
//		}
				
		// 권한 생성
		sqlbuild.createGrants(table);		

		return _writer.toString();
	}

	/** insomnia
	 * @throws IOException */
	public List<WaqDdlTbl> updateDdlScirptWaq(WaqMstr mstVo) throws IOException {
		List<WaqDdlTbl> waqlist = new ArrayList<WaqDdlTbl>();

		String rqstNo = mstVo.getRqstNo();

		List<Table> tables = mapper.selectDdlTableListWaq(rqstNo);

		for (Table table : tables) {

			//DDL 스크립트를 가져온다.
			String ddlscript = getDDlScriptTable(table);

			WaqDdlTbl waqtbl = new WaqDdlTbl();
			waqtbl.setRqstNo(table.getRqstNo());
			waqtbl.setRqstSno(table.getRqstSno());
			waqtbl.setScrtInfo(ddlscript);

			waqlist.add(waqtbl);

		}

		return waqlist;
	}
	
	/** insomnia
	 * @throws IOException */
	public String getDdlScirptWaqRqstSno(WaqDdlTbl ddlVo) throws IOException {
		
	    logger.debug("\n TblChgTypCd:" +  UtilString.null2Blank(ddlVo.getTblChgTypCd()) );
	    
		List<Table> tables = mapper.selectDdlTableListWaqRqstSno(ddlVo); 

		String ddlscript = "";
		
		for (Table table : tables) {

		    String creDrpDcd = UtilString.null2Blank(ddlVo.getTblChgTypCd()); 
		    
		    if(!creDrpDcd.equals("")) {
		        //팝업화면 CREATE , DROP & CREATE 에 따라 DDL스크립트 조회
		        	        
		        table.getDdltbl().setTblChgTypCd(creDrpDcd);
		    } else{
		        table.getDdltbl().setTblChgTypCd(creDrpDcd);
		    }
		    
			//DDL 스크립트를 가져온다.
			ddlscript = getDDlScriptTable(table);
			
		}

		return ddlscript;
	}
	
	/** insomnia
	 * @throws IOException */
	public String getDdlIdxScirptWaqRqstSno(WaqDdlIdx ddlVo) throws IOException {
		
		List<Table> tables = mapper.selectDdlIndexListWaqRqstSno(ddlVo);   

		String ddlscript = "";
		
		for (Table table : tables) {

			//DDL 스크립트를 가져온다.
			ddlscript += getDDlScriptIndex(table);
			
		}

		return ddlscript;
	}
	
	public String getDdlIdxScirpt(WaqDdlIdx ddlVo) throws IOException {
		Table table = mapper.selectDdlIndex(ddlVo);   
		
		if(table != null)
			table.setRegTypCd(UtilString.null2Blank(ddlVo.getRegTypCd()));
		
		return getDDlScriptIndex(table);
	}
	
	/** insomnia
	 * @throws IOException */
	public List<WaqDdlIdx> updateDdlIdxScirptWaq(WaqMstr mstVo) throws IOException {
		List<WaqDdlIdx> waqlist = new ArrayList<WaqDdlIdx>();
		
		String rqstNo = mstVo.getRqstNo();
		
		List<Table> tables = mapper.selectDdlIndexListWaq(rqstNo);
		
		for (Table table : tables) {
			
			//DDL 스크립트를 가져온다.
			String ddlscript = getDDlScriptIndex(table);
			
			WaqDdlIdx waqidx = new WaqDdlIdx();
			waqidx.setRqstNo(table.getRqstNo());
			waqidx.setRqstSno(table.getRqstSno());
			waqidx.setScrtInfo(ddlscript);
			
			waqlist.add(waqidx);
			
		}
		
		return waqlist;
	}

	/** @param table
	/** @return insomnia
	 * @throws IOException */
	private String getDDlScriptTable(Table table) throws IOException {
		logger.debug("table:{}", table);

		/** sql결과 */
		StringWriter _writer = null;
		
		_writer = new StringWriter();;

		Database database = new Database();

		SqlBuilder sqlbuild = null;

		String dbtype = null;
		
		dbtype = UtilString.null2Blank(table.getType());
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);

		//테이블 DDL 설명 생성
		sqlbuild.writeTableComment(table);

		String tblChgTypCd = UtilString.null2Blank(table.getDdltbl().getTblChgTypCd())	;	
	    logger.debug("\ntblChgTypCd : {}\n",tblChgTypCd);
	    if("D".equals(table.getRegTypCd())) {
            sqlbuild.dropTable(table);
            sqlbuild.dropSynonym(table);
            return _writer.toString();
        }		
	    if("".equals(tblChgTypCd)) {
		
		    //테이블이 삭제인 경우....
	        if("D".equals(table.getRegTypCd())) {
	            sqlbuild.dropTable(table);
	            return _writer.toString();
	        }
	        		    
		     //신규 테이블일 경우...
	        if("C".equals(table.getRegTypCd())) {
	            sqlbuild.createTable(database, table);
	            sqlbuild.createComment(table);
	            
	            //sqlbuild.createIndexes(table);
	            
//	            String ddlTblId = UtilString.null2Blank(table.getDdltbl().getDdlTblId());
	            
	            //인덱스 조회
	            //List<Table> idxTbl = mapper.selectTableIdxList(ddlTblId);  
	            
//	            List<Table> idxTbl = mapper.selectWaqCreTableIdxList(table);
//
//	            for(Table idx : idxTbl){
//	                //인덱스 생성 
//	                sqlbuild.createIndex(idx);
//	            }
	            
	    		// 권한 생성
	    		sqlbuild.createGrants(table);

	            return _writer.toString();
	        } else if("U".equals(table.getRegTypCd())) {
	        	sqlbuild.createBackupTable(database, table);
				sqlbuild.createComment(table);
				sqlbuild.dataCopy(table);
				sqlbuild.dropBackupTable(table);
				
				sqlbuild.createGrants(table);
	
				return _writer.toString(); 
	        }
	        
		} else if("DRP".equals(tblChgTypCd) || "CRE".equals(tblChgTypCd)) {
		    
		    //테이블 스크립트가 Drop & Create 인 경우....
		    if("DRP".equals(tblChgTypCd)){
		    
		        sqlbuild.dropTable(table);
		    }
			
			sqlbuild.createTable(database, table);
			sqlbuild.createComment(table);
			
//			String ddlTblId = UtilString.null2Blank(table.getDdltbl().getDdlTblId());
			
			//인덱스 조회
			//List<Table> idxTbl = mapper.selectTableIdxList(ddlTblId);
			
//			List<Table> idxTbl = mapper.selectWaqCreTableIdxList(table);
//
//			for(Table idx : idxTbl){
//			
//				//인덱스 생성 
//				sqlbuild.createIndex(idx);
//			}
			
			sqlbuild.createGrants(table);

			return _writer.toString(); 
			
		}else if("ALT".equals(tblChgTypCd)) { //테이블 스크립트가 Alter 일 경우...
		    logger.debug("\ntable.getRegTypCd() : {}\n",table.getRegTypCd());
			if("C".equals(table.getRegTypCd())) {
				return "";
			}
			//=====drop 인덱스=======
			table.setRqstDcd("DD");
			
//			List<Table> idxTbl = mapper.selectWaqUpdTableIdxList(table);
//			
//			for(Table idx : idxTbl){
//				
//				//인덱스 생성 
//				sqlbuild.dropIndex(idx);
//			}
			
			//=====ALTER 문장=======
			sqlbuild.alterTable(table);
			//======================
						
			
			//=====create 인덱스=======
//			table.setRqstDcd("CU");
			
//			idxTbl = mapper.selectWaqUpdTableIdxList(table);
//			
//			for(Table idx : idxTbl){
//			    
//			    String regTypCd = UtilString.null2Blank(idx.getRegTypCd());
//			    
//			    if(regTypCd.equals("U")) {
//			        
//			        //인덱스 생성 
//	                sqlbuild.dropIndex(idx);
//			    }
//				
//				//인덱스 생성 
//				sqlbuild.createIndex(idx);
//			}
						
			return _writer.toString();
			
		}else  if("RNT".equals(table.getDdltbl().getTblChgTypCd())) {
		
		    //테이블 스크립트가 Rename 일 경우...
		    
		    sqlbuild.renameTable(table);
			return _writer.toString();
		} else if("BAK".equals(tblChgTypCd)) {
			if(!"C".equals(table.getRegTypCd())) {
				sqlbuild.createBackupTable(database, table);
				sqlbuild.createComment(table);
				sqlbuild.dataCopy(table);
				sqlbuild.dropBackupTable(table);
				
				String ddlTblId = UtilString.null2Blank(table.getDdltbl().getDdlTblId());
				
				//인덱스 조회
				//List<Table> idxTbl = mapper.selectTableIdxList(ddlTblId);
				
//				List<Table> idxTbl = mapper.selectWaqCreTableIdxList(table);
	
	//			for(Table idx : idxTbl){
	//			
	//				//인덱스 생성 
	//				sqlbuild.createIndex(idx);
	//			}
				
				sqlbuild.createGrants(table);
	
				return _writer.toString(); 
			}	
		}

		return _writer.toString();
	}
	/** @param table
	/** @return insomnia
	 * @throws IOException */	               
	public String getDDlScriptIndex(Table table) throws IOException { 
		logger.debug("table:{}", table);
		
		if(table == null)
			return "정보가 존재 하지 않습니다.";
		
		/** sql결과 */
		StringWriter _writer = null;
		
		_writer = new StringWriter();;
		
		Database database = new Database();
		
		SqlBuilder sqlbuild = null;
		
		String dbtype = null;
		
		dbtype = UtilString.null2Blank(table.getType());
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);
		
		//DDL 인덱스 설명 생성
		sqlbuild.writeIndexComment(table);
		
		logger.info( _writer.toString());
		
		//신규
		if("C".equals(table.getRegTypCd())) {
			sqlbuild.createIndex(table);
			return _writer.toString();
		}
		//변경
		if("U".equals(table.getRegTypCd())) {
			sqlbuild.dropIndex(table);
			sqlbuild.createIndex(table);
			return _writer.toString();
		}
		//삭제
		if("D".equals(table.getRegTypCd())) {
			sqlbuild.dropIndex(table);
			return _writer.toString();
		}
				
		return _writer.toString();
	}

	@Override
	public String getScrtInfo(String ddlTblId) {
		return mapper.getScrtInfo(ddlTblId);
	}

	@Override
	public Table getTableIndexOne(String ddlIdxId) {
		// TODO Auto-generated method stub
		return mapper.selectTableIdxOne(ddlIdxId);
	}

	@Override
	public List<WaqDdlSeq> updateDdlSeqScirptWaq(WaqMstr mstVo) throws IOException {
		List<WaqDdlSeq> waqlist = new ArrayList<WaqDdlSeq>();
		
		String rqstNo = mstVo.getRqstNo();
//		//logger.debug("시퀀스 스크립트 생성 시작");
		List<Sequence> seqs = mapper.selectDdlSeqListWaq(rqstNo);
//		//logger.debug("시퀀스 스크립트 생성1");
		for (Sequence seq : seqs) {
//			//logger.debug("시퀀스 스크립트 생성2");
			//DDL 스크립트를 가져온다.
			String ddlscript = getDDlScriptSeq(seq);
			
			WaqDdlSeq waqseq = new WaqDdlSeq();
			waqseq.setRqstNo(seq.getRqstNo());
			waqseq.setRqstSno(seq.getRqstSno());
			waqseq.setScrtInfo(ddlscript);
			
			waqlist.add(waqseq);
			
		}
		
		return waqlist;
	}
	public String getDdlSeqScirpt(Sequence ddlVo) throws IOException {
		Sequence seq = mapper.selectDdlSeq(ddlVo);   
//		seq.setRegTypCd(ddlVo.getRegTypCd());
		return getDDlScriptSeq(seq);
	}

	/** @param sequence
	/** @return syyoo
	 * @throws IOException */
	private String getDDlScriptSeq(Sequence seq) throws IOException {
		//logger.debug("seq:{}", seq);
		
		/** sql 결과 */
		StringWriter _writer = new StringWriter();;
		
		Database database = new Database();
		
		SqlBuilder sqlbuild = null;
		
		String dbtype = seq.getType();
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);
		
		//DDL 시퀀스 설명 생성
		sqlbuild.writeSequenceComment(seq);
		
		logger.info( _writer.toString());
		
		//신규

		if("C".equals(seq.getRegTypCd())) {
			if("CBR".equals(dbtype)) {
				sqlbuild.createSerial(seq);
			}
			
			sqlbuild.createSequence(seq);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createSeqGrants(seq);
			sqlbuild.createSeqSynonyms(seq);
			return _writer.toString();
		}
		//변경
		else if("U".equals(seq.getRegTypCd())) {
			if("CBR".equals(dbtype)) {
				sqlbuild.dropSerial(seq);
				sqlbuild.createSerial(seq);
			} else {
				sqlbuild.dropSequence(seq);
				sqlbuild.createSequence(seq);
			}
			
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createSeqGrants(seq);
			sqlbuild.createSeqSynonyms(seq);
			
			return _writer.toString();
		}
	
		//삭제
		else if("D".equals(seq.getRegTypCd())) {
			if("CBR".equals(dbtype)) {
				sqlbuild.dropSerial(seq);
			} else {
				sqlbuild.dropSequence(seq);
			}

			return _writer.toString();
		}
		
		return _writer.toString();
	}
	
	private SqlBuilder getDbmsBuilder(String dbtype) {
		if(dbtype.equals("ORA")) {//ORACLE
			return new Oracle10Builder();
		}else if(dbtype.equals("SYQ")){ //SYBASEIQ
			return new SybaseIQBuilder();
		}else if(dbtype.equals("SYA")){//SYBASEASE
			return new SybaseASEBuilder();
		}else if(dbtype.equals("DB2")){//DB2 AS400
			return new DB2Builder();
		}else if(dbtype.equals("MSQ")){//MS-SQL
			return new Mssql2017Builder();
		}else if(dbtype.equals("ALT")){//ALTIBASE
			return new Altibase6Builder();
		}else if(dbtype.equals("TIB")){//TIBERO
			return new Tibero6Builder();
		}else if(dbtype.equals("MYS")){//MYSQL
			return new Mysql6Builder();
		}else if(dbtype.equals("CBR")){//CUBRID
			return new Cubrid10Builder();
		}else if(dbtype.equals("IFX")){//INFORMIX
			return new Informix4Builder();
		}else if(dbtype.equals("POS")){//POSTGRESQL
			return new Postgresql12Builder();
		}else if(dbtype.equals("TER")){//TERADATA
			return new TeradataBuilder();
		}else if(dbtype.equals("UDB")){//UNIVERSAL DATABASE
			return new UDBBuilder();
		}else if(dbtype.equals("MRA")){//mariadb
			return new MariaDbBuilder();
		}else{
			return new Oracle10Builder();
		}
	}


	@Override
	public String getScrtInfoSeq(String ddlSeqId) {
		return mapper.getScrtInfoSeq(ddlSeqId);
	}

	@Override
	public String getScrtInfoSeqByWaq(WaqDdlSeq ddlVo, String chgTypCd) {
		return mapper.getScrtInfoSeqByWaq(ddlVo);
	}

	@Override
	public String getDDlScriptIndexByChgTypCd(String ddlIdxId, String tblChgTypCd) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDDlScriptTable(String ddlTblId, String tblChgTypCd) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDDlScriptPartition(Table tablepart) throws IOException {
		//logger.debug("ddl 파티션 스크립트 생성 by table:{}", tablepart);
				Table ddltable = new Table();
				//테이블 조회
				String ddlTblId = tablepart.getPartition().getDdlTblId();
				if(!UtilString.null2Blank(ddlTblId).equals("")){
					ddltable = mapper.selectTableOne(ddlTblId);
				}else{
					Map<String, Object> searchmap = new HashMap<String, Object>();
					searchmap.put("ddlTblPnm"	, tablepart.getPartition().getDdlTblPnm());
					searchmap.put("rqstNo"	, tablepart.getRqstNo());
					searchmap.put("rqstSno"	, tablepart.getRqstSno());
					ddltable = mapper.selectTableOneByRqstNo(searchmap);
				}
				
				
				
				
				if(ddltable == null) {

//					return "DDL 테이블과 관련된 컬럼정보 또는 DB정보가 없습니다.";
					return message.getMessage("ERR.DDL.NODATA", null, null);
				}
				
				/** sql결과 */
				StringWriter _writer = new StringWriter();;

				Database database = new Database();

				SqlBuilder sqlbuild = null;

				String dbtype = ddltable.getType();

				sqlbuild = getDbmsBuilder(dbtype);
				
				
				//buffer 초기화...
				_writer.getBuffer().setLength(0);
				sqlbuild.setWriter(_writer);

				//파티션 DDL 설명 생성
		    	sqlbuild.writeTableComment(ddltable);

				//파티션이 삭제인 경우....
				if("D".equals(tablepart.getRegTypCd())) {
					_writer.append("/** Drop Table **/ \n");
					sqlbuild.dropTable(ddltable);
					_writer.append("/** Create Table **/ \n");
					sqlbuild.createTable(database, ddltable);
					_writer.append("/** Comments **/ \n");
					sqlbuild.createComment(ddltable);
					_writer.append("/** Grant & Synonym **/ \n");
					sqlbuild.createGrants(ddltable);
//					if(i==0||table.getPciYn().equals("N")){  //분리보관일경우 시노님 제외
					   sqlbuild.createSynonyms(ddltable);
//					}
					//return _writer.toString(); 
					
					//drop & create 인경우 create index 구문 출력
					//변경상태인 경우에만 create index 구문 출력
					if("U".equals(ddltable.getRegTypCd())){
						List<Table> indices = mapper.selectWahIndices(ddlTblId);
						if(indices.size() > 0){
							_writer.append("/** Create Index **/ \n");
						}
						for (Table index : indices) {
							//파티션 정보가 있을 경우 테이블에 셋팅한다.
							if (tablepart.getPartition() != null) {
								index.setPartition(tablepart.getPartition());
							}
							sqlbuild.createIndex(index);
						}	   
					}
					
//					return _writer.toString();
				}
				//신규 파티션일 경우...
				if("C".equals(tablepart.getRegTypCd())) {
					//파티션 정보가 있을 경우 테이블에 셋팅한다.
					if (tablepart.getPartition() != null) {
						ddltable.setPartition(tablepart.getPartition());
					}
					
					_writer.append("/** Drop Table **/ \n");
					sqlbuild.dropTable(ddltable);

					_writer.append("/** Create Table **/ \n");
					sqlbuild.createTable(database, ddltable);
//					sqlbuild.createPartition(database, table);
					
					sqlbuild.createIndexes(ddltable);
					_writer.append("/** Comments **/ \n");
					sqlbuild.createComment(ddltable);
					_writer.append("/** Grant & Synonym **/ \n");
					sqlbuild.createGrants(ddltable);
					sqlbuild.createSynonyms(ddltable);
//					return _writer.toString();
					//drop & create 인경우 create index 구문 출력
					//변경상태인 경우에만 create index 구문 출력
//					if("U".equals(ddltable.getRegTypCd())){
					List<Table> indices = null;	
					// DDL 파티션테이블 이관요청시 null 에러..
					if(!UtilString.null2Blank(ddlTblId).equals(""))
					{
						indices = mapper.selectWahIndices(ddlTblId);
						
						if(indices.size() > 0){
							_writer.append("/** Create Index **/ \n");
						}
						for (Table index : indices) {
							//파티션 정보가 있을 경우 테이블에 셋팅한다.
							if (tablepart.getPartition() != null) {
								index.setPartition(tablepart.getPartition());
							}
							sqlbuild.createIndex(index);
						}	
					}
						   
//					}
				}
				//변경 파티션일 경우...
				if("U".equals(tablepart.getRegTypCd())) {
					
					//파티션 정보가 있을 경우 테이블에 셋팅한다.
					if (tablepart.getPartition() != null) {
						ddltable.setPartition(tablepart.getPartition());
					}
					
					_writer.append("/** Alter Partition **/ \n");
//					sqlbuild.createTable(database, table);
					sqlbuild.alterPartition(database, ddltable);
//					sqlbuild.createPartition(database, table);
				}

				return _writer.toString();
	}

	@Override
	public String getDDlScriptPartition(String objId, String tblChgTypCd) throws IOException {
			
		/** sql결과 */
		StringWriter _writer = new StringWriter();;

		Database database = new Database();

		SqlBuilder sqlbuild = null;

		//파티션 정보를 조회해서 테이블에 셋팅한다....
		Table partition = mapper.selectPartitionbyPartid(objId);
		
		Table partitionAlter = mapper.selectPartitionForAlter(objId); 
		
		String ddlTblId = partition.getDdltbl().getDdlTblId();
		
		//테이블 조회
		//파티션 조회시 파티션 요청 정보를 조회 해야 한다.
//			Table table = mapper.selectTableOne(ddlTblId);
		Table table = mapper.selectTableOneByPart(ddlTblId);
		
		String tmpTblSpacePnm = "XXXXXXXXXXXX";
		if(table != null){
			tmpTblSpacePnm = table.getTblSpacPnm() == null ? "XXXXXXXXXXXX" : table.getTblSpacPnm();
		}
		
		List<Table> indices = null;
		
		String dbtype = table.getType();
		
		sqlbuild = getDbmsBuilder(dbtype);
	

		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);
	  
		//스크립트 유형 미선택
		if(UtilString.null2Blank(tblChgTypCd).equals("")) { 
			//신규
			if("C".equals(partition.getRegTypCd())) {
				tblChgTypCd = "CRO";
			}
			//변경
			else if("U".equals(partition.getRegTypCd())) {
				tblChgTypCd = "ALT";
			}
		}
		
		//테이블 DDL 설명 생성
		sqlbuild.writeTableComment(table);

		
        if("D".equals(partition.getRegTypCd()) || "DRO".equals(tblChgTypCd)  ){
        	_writer.append("/** Drop Table **/ \n");
        	sqlbuild.dropTable(table);
        	_writer.append("/** Create Table **/ \n");
			sqlbuild.createTable(database, table);
			_writer.append("/** Comments **/ \n");
			sqlbuild.createComment(table);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createGrants(table);
//				if(i==0||table.getPciYn().equals("N")){  //분리보관일경우 시노님 제외
			   sqlbuild.createSynonyms(table);
//				}
			//return _writer.toString(); 
			
			//drop & create 인경우 create index 구문 출력
			//변경상태인 경우에만 create index 구문 출력
//				if("U".equals(table.getRegTypCd())){
				indices = mapper.selectWahIndices(ddlTblId);
				if(indices.size() > 0){
					_writer.append("/** Create Index **/ \n");
				}
				for (Table index : indices) {
					//파티션 정보가 있을 경우 테이블에 셋팅한다.
					if (partition.getPartition() != null) {
						index.setPartition(partition.getPartition());
					}
					sqlbuild.createIndex(index);
				}	   
//				}		
        }
        
        else if("CRO".equals(tblChgTypCd)){
        	////logger.debug("CREATE PARTITION ONLY " + tblChgTypCd);
        	//파티션 정보가 있을 경우 테이블에 셋팅한다.
			if (partition.getPartition() != null) {
				table.setPartition(partition.getPartition());
			}
        	_writer.append("/** Drop Table **/ \n");
        	sqlbuild.dropTable(table);
		    _writer.append("/** Create Table **/ \n");
			sqlbuild.createTable(database, table);
			_writer.append("/** Comments **/ \n");
			sqlbuild.createComment(table);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createGrants(table);
//				if(i==0||table.getPciYn().equals("N")){  //분리보관일경우 시노님 제외
			   sqlbuild.createSynonyms(table);
//				}
			//return _writer.toString(); 
//				if("U".equals(table.getRegTypCd())){
			indices = mapper.selectWahIndices(ddlTblId);
			if(indices.size() > 0){
				_writer.append("/** Create Index **/ \n");
			}
			for (Table index : indices) {
				
				//파티션 정보가 있을 경우 테이블에 셋팅한다.
				if (partition.getPartition() != null) {
					index.setPartition(partition.getPartition());
				}
				sqlbuild.createIndex(index);
			}	   
//				}
		}
		//테이블 스크립트가 Drop & Create 인 경우....
		else if("DRP".equals(tblChgTypCd)) {
//				//logger.debug("DROP&CREATE TABLE " + tblChgTypCd);
			//파티션 정보가 있을 경우 테이블에 셋팅한다.
			if (partition.getPartition() != null) {
				table.setPartition(partition.getPartition());
			}
			_writer.append("/** Drop Table **/ \n");
			sqlbuild.dropTable(table);
			_writer.append("/** Create Table **/ \n");
			sqlbuild.createTable(database, table);
			_writer.append("/** Comments **/ \n");
			sqlbuild.createComment(table);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createGrants(table);
//				if(i==0||table.getPciYn().equals("N")){  //분리보관일경우 시노님 제외
			   sqlbuild.createSynonyms(table);
//				}
			//return _writer.toString(); 
			
			//drop & create 인경우 create index 구문 출력
			//변경상태인 경우에만 create index 구문 출력
//				if("U".equals(table.getRegTypCd())){
				indices = mapper.selectWahIndices(ddlTblId);
				if(indices.size() > 0){
					_writer.append("/** Create Index **/ \n");
				}
				for (Table index : indices) {
					//파티션 정보가 있을 경우 테이블에 셋팅한다.
					if (partition.getPartition() != null) {
						index.setPartition(partition.getPartition());
					}
					sqlbuild.createIndex(index);
				}	   
//				}		
		}
	    	
		//테이블 스크립트가 Alter 일 경우...
	    else if("ALT".equals(tblChgTypCd)) {
			//logger.debug("ALTER PARTITION:{}" , tblChgTypCd);
			//파티션 정보가 있을 경우 테이블에 셋팅한다.
			if (partitionAlter != null && partitionAlter.getPartition() != null) {
				table.setPartition(partitionAlter.getPartition());
			}
			
			sqlbuild.alterPartition(database, table);   
			
			//return _writer.toString();
		}
        
        //복제 테이블 스크립트 생성 수행
        //테이블명은 첫 글자 를  Z 로 변경 
        //테이블 스페이스 명은 "TSDZTB001" 변경가능성 없음
        if(UtilString.null2Blank(partition.getCudYn()).equals("Y")) {
        	String _zwriter = new String();
//	    		//buffer 초기화...
//	    		_zwriter.getBuffer().setLength(0);
    		_zwriter = _writer.toString();
    		
        	String zTableNm = "Z" + partition.getName().substring(1, partition.getName().length()) ;
        	String zTableSpaceNm = "TSDZTB001";
        	

        	
        	_zwriter = _zwriter.replaceAll(partition.getName(), zTableNm);
        	if(!UtilString.null2Blank(tmpTblSpacePnm).equals("")){
        		_zwriter = _zwriter.replaceAll(tmpTblSpacePnm, zTableSpaceNm);
        	}
        	if(!UtilString.null2Blank(partition.getTblSpacPnm()).equals("")){
        		_zwriter = _zwriter.replaceAll(partition.getTblSpacPnm(), zTableSpaceNm);
        	}
        	if(!UtilString.null2Blank(partition.getIdxSpacPnm()).equals("")){
        		_zwriter = _zwriter.replaceAll(partition.getIdxSpacPnm(), zTableSpaceNm);
        	}
        	
        	_writer.append("\n\n\n\n");
        	_writer.append(_zwriter.toString()); 

        }
			
		return _writer.toString(); 		
	}
	
	public String getDDlScriptPartByWaq(WaqDdlPart ddlVo, String tblChgTypCd) throws IOException { 
		
		/** sql결과 */
		StringWriter _writer = new StringWriter();;
		
		Database database = new Database();
		
		SqlBuilder sqlbuild = null;
		
		//파티션 정보를 조회해서 테이블에 셋팅한다....
		Table partition = mapper.selectPartitionbyPartidByWaq(ddlVo);
		
		if(partition == null){
			return "";
		}
		
		Table partitionAlter = mapper.selectPartitionForAlterByWaq(ddlVo); 
		
		if(partitionAlter == null){
			return "";
		}
		
		String ddlTblId = partition.getDdltbl().getDdlTblId();
		
		//테이블 조회
		//파티션 조회시 파티션 요청 정보를 조회 해야 한다.
//		Table table = mapper.selectTableOne(ddlTblId);
		Table table = mapper.selectTableOneByPartByWaq(ddlVo); 
		
		String tmpTblSpacePnm = "XXXXXXXXXXXX";
		if(table != null){
			tmpTblSpacePnm = table.getTblSpacPnm() == null ? "XXXXXXXXXXXX" : table.getTblSpacPnm();
		}
		
		List<Table> indices = null;
		
		String dbtype = table.getType();
		
		sqlbuild = getDbmsBuilder(dbtype);
	

		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);
      
		//스크립트 유형 미선택
		if(UtilString.null2Blank(tblChgTypCd).equals("")) { 
			//신규
			if("C".equals(partition.getRegTypCd())) {
				tblChgTypCd = "CRO";
			}
			//변경
			else if("U".equals(partition.getRegTypCd())) {
				tblChgTypCd = "ALT";
				//전일배치CUD일경우는 ??
			}
		}
		//테이블 DDL 설명 생성
		sqlbuild.writeTableComment(table);

		
        if("D".equals(partition.getRegTypCd()) || "DRO".equals(tblChgTypCd)  ){
        	_writer.append("/** Drop Table **/ \n");
        	sqlbuild.dropTable(table);
        	_writer.append("/** Create Table **/ \n");
			sqlbuild.createTable(database, table);
			_writer.append("/** Comments **/ \n");
			sqlbuild.createComment(table);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createGrants(table);

			sqlbuild.createSynonyms(table);

			//drop & create 인경우 create index 구문 출력
			//변경상태인 경우에만 create index 구문 출력

			indices = mapper.selectWahIndices(ddlTblId); 
			if(indices.size() > 0){
				_writer.append("/** Create Index **/ \n");
			}
			for (Table index : indices) {
				//파티션 정보가 있을 경우 테이블에 셋팅한다.
				if (partition.getPartition() != null) {
					index.setPartition(partition.getPartition());
				}
				sqlbuild.createIndex(index);
			}	   

        }
        
        else if("CRO".equals(tblChgTypCd)){
        	////logger.debug("CREATE PARTITION ONLY " + tblChgTypCd);
        	//파티션 정보가 있을 경우 테이블에 셋팅한다.
			if (partition.getPartition() != null) {
				table.setPartition(partition.getPartition());
			}
        	_writer.append("/** Drop Table **/ \n");
        	sqlbuild.dropTable(table);
		    _writer.append("/** Create Table **/ \n");
			sqlbuild.createTable(database, table);
			_writer.append("/** Comments **/ \n");
			sqlbuild.createComment(table);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createGrants(table);
//			if(i==0||table.getPciYn().equals("N")){  //분리보관일경우 시노님 제외
			   sqlbuild.createSynonyms(table);
//			}
			//return _writer.toString(); 
//			if("U".equals(table.getRegTypCd())){
			indices = mapper.selectWahIndices(ddlTblId);
			if(indices.size() > 0){
				_writer.append("/** Create Index **/ \n");
			}
			for (Table index : indices) {
				
				//파티션 정보가 있을 경우 테이블에 셋팅한다.
				if (partition.getPartition() != null) {
					index.setPartition(partition.getPartition());
				}
				sqlbuild.createIndex(index);
			}	   
//			}
		}
		//테이블 스크립트가 Drop & Create 인 경우....
		else if("DRP".equals(tblChgTypCd)) {
//			//logger.debug("DROP&CREATE TABLE " + tblChgTypCd);
			//파티션 정보가 있을 경우 테이블에 셋팅한다.
			if (partition.getPartition() != null) {
				table.setPartition(partition.getPartition());
			}
			_writer.append("/** Drop Table **/ \n");
			sqlbuild.dropTable(table);
			_writer.append("/** Create Table **/ \n");
			sqlbuild.createTable(database, table);
			_writer.append("/** Comments **/ \n");
			sqlbuild.createComment(table);
			_writer.append("/** Grant & Synonym **/ \n");
			sqlbuild.createGrants(table);
//			if(i==0||table.getPciYn().equals("N")){  //분리보관일경우 시노님 제외
			   sqlbuild.createSynonyms(table);
//			}
			//return _writer.toString(); 
			
			//drop & create 인경우 create index 구문 출력
			//변경상태인 경우에만 create index 구문 출력
//			if("U".equals(table.getRegTypCd())){
				indices = mapper.selectWahIndices(ddlTblId);
				if(indices.size() > 0){
					_writer.append("/** Create Index **/ \n");
				}
				for (Table index : indices) {
					//파티션 정보가 있을 경우 테이블에 셋팅한다.
					if (partition.getPartition() != null) {
						index.setPartition(partition.getPartition());
					}
					sqlbuild.createIndex(index);
				}	   
//			}		
		}
	    	
		//테이블 스크립트가 Alter 일 경우...
	    else if("ALT".equals(tblChgTypCd)) {
			//logger.debug("ALTER PARTITION:{}" , tblChgTypCd);
			//파티션 정보가 있을 경우 테이블에 셋팅한다.
			if (partitionAlter.getPartition() != null) {
				table.setPartition(partitionAlter.getPartition());
			}
			
			sqlbuild.alterPartition(database, table);   
			
			//return _writer.toString();
		}
        
        //복제 테이블 스크립트 생성 수행
        //테이블명은 첫 글자 를  Z 로 변경 
        //테이블 스페이스 명은 "TSDZTB001" 변경가능성 없음
        if(UtilString.null2Blank(partition.getCudYn()).equals("Y")) {
        	String _zwriter = new String();
//    		//buffer 초기화...
//    		_zwriter.getBuffer().setLength(0);
    		_zwriter = _writer.toString();
    		
        	String zTableNm = "Z" + partition.getName().substring(1, partition.getName().length()) ;
        	String zTableSpaceNm = "TSDZTB001";
        	
//        	logger.debug(partition.getName());
//        	logger.debug(partition.getTblSpacPnm());
//        	logger.debug(partition.getIdxSpacPnm());
        	
        	_zwriter = _zwriter.replaceAll(partition.getName(), zTableNm);
        	if(!UtilString.null2Blank(tmpTblSpacePnm).equals("")){
        		_zwriter = _zwriter.replaceAll(tmpTblSpacePnm, zTableSpaceNm);
        	}
        	if(!UtilString.null2Blank(partition.getTblSpacPnm()).equals("")){
        		_zwriter = _zwriter.replaceAll(partition.getTblSpacPnm(), zTableSpaceNm);
        	}
        	if(!UtilString.null2Blank(partition.getIdxSpacPnm()).equals("")){
        		_zwriter = _zwriter.replaceAll(partition.getIdxSpacPnm(), zTableSpaceNm);
        	}
        	
        	_writer.append("\n\n\n\n");
        	_writer.append(_zwriter.toString());

        }
		
        return _writer.toString();
	}

	@Override
	public String getDdlEtcScript(String objId) throws Exception {
		return mapper.selectDdlEtcByObjId(objId);
	}

	@Override
	public String getWahDdlTblScript(String ddlTblId, String rqstNo, String creDrpDcd) throws Exception {
		// TODO Auto-generated method stub
//		return mapper.getTblScrtInfo(ddlTblId, rqstNo);
		logger.debug("ddltblid:{}", ddlTblId);
		
		//테이블 조회
		Table table = mapper.selectTableForWah(ddlTblId, rqstNo);
		
		if(table == null) {

//			return "DDL 테이블과 관련된 컬럼정보 또는 DB정보가 없습니다.";
			return message.getMessage("ERR.DDL.NODATA", null, null);
		}

		logger.debug("table:{}", table);

		/** sql결과 */
		StringWriter _writer = null;
		
		_writer = new StringWriter();;

		Database database = new Database();

		SqlBuilder sqlbuild = null ;

		//DBMS 종류 분기
		String dbtype = null;
		
		dbtype = UtilString.null2Blank(table.getType());
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);

		logger.debug("script 1");
		sqlbuild.setWriter(_writer);
		logger.debug("script 2");

//		database.addTable(table);

		//테이블 DDL 설명 생성
		sqlbuild.writeTableComment(table);
		
		if("BAK".equals(creDrpDcd)){
			if(!"C".equals(table.getRegTypCd())) {
				sqlbuild.createBackupTable(database, table);
				
				if(!"D".equals(table.getRegTypCd())){
					sqlbuild.createComment(table);
					sqlbuild.dataCopy(table);
					sqlbuild.dropBackupTable(table);
				}
			}	
		} else if("CRE".equals(creDrpDcd)) {
			if(!"D".equals(table.getRegTypCd())){
				sqlbuild.createTable(database, table); 
				sqlbuild.createComment(table);
			}
		} else if("DRP".equals(creDrpDcd)) {
			if("D".equals(table.getRegTypCd())){
				sqlbuild.dropTable(table);
			} else{
				sqlbuild.dropTable(table);
				
				sqlbuild.createTable(database, table); 
				sqlbuild.createComment(table);
			}
		} else if("ALT".equals(creDrpDcd)) {
			if("C".equals(table.getRegTypCd()) || "D".equals(table.getRegTypCd())) {
				return "";
			}
			
			table.setRqstDcd("DD");
			sqlbuild.alterTable(table);
		} else {
			if("D".equals(table.getRegTypCd())) {
				sqlbuild.dropTable(table);
			} else{
				sqlbuild.dropTable(table);

				sqlbuild.createTable(database, table); 
				sqlbuild.createComment(table);
			}
	
		}
		
		
		//인덱스 조회
//		List<Table> idxTbl = mapper.selectTableIdxList(ddlTblId);  
//
//		for(Table idx : idxTbl){
//		
//			//인덱스 생성 
//			sqlbuild.createIndex(idx);
//		}
				
		// 권한 생성
		sqlbuild.createGrants(table);		

		return _writer.toString();
	}

	@Override
	public String getWahDdlIdxScirpt(String ddlIdxId, String rqstNo) throws Exception {
		// TODO Auto-generated method stub
//		return mapper.getIdxScrtInfo(ddlTblId, rqstNo);
		Table table = mapper.selectIndexForWah(ddlIdxId, rqstNo);   
		
		return getDDlScriptIndex(table);
	}

	@Override
	public String getWahDdlSeqScirpt(String ddlTblId, String rqstNo) throws Exception {
		// TODO Auto-generated method stub
//		return mapper.getSeqScrtInfo(ddlTblId, rqstNo);
		Sequence seq = mapper.selectSeqenceForWah(ddlTblId, rqstNo);   
		
		return getDDlScriptSeq(seq);
	}
	
	@Override
	public String getWahDdlEtcScript(String ddlTblId, String rqstNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getEtcScrtInfo(ddlTblId, rqstNo);
	}
	
	@Override
	public String getWahDdlPartScript(String ddlTblId, String rqstNo) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getPartScrtInfo(ddlTblId, rqstNo);
	}
	
	private String getPrivDDlScriptTable(Table table) throws IOException {
		logger.debug("table:{}", table);

		/** sql결과 */
		StringWriter _writer = null;
		
		_writer = new StringWriter();

		Database database = new Database();

		SqlBuilder sqlbuild = null;

		String dbtype = null;
		
		dbtype = UtilString.null2Blank(table.getType());
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);

        if("D".equals(table.getRegTypCd())) {
            return "";
        } else {
        	// 권한 생성
    		_writer.append("/** Grant & Synonym **/ \n");
    		sqlbuild.createGrants(table);
        }

		return _writer.toString();
	}

	@Override
	public List<WaqDdlGrt> updateDdlGrtScirptWaq(WaqMstr mstVo) throws IOException {
		List<WaqDdlGrt> waqlist = new ArrayList<WaqDdlGrt>();
		
		String rqstNo = mstVo.getRqstNo();
//			logger.debug("시퀀스 스크립트 생성 시작");
		List<Grant> grts = mapper.selectDdlGrtListWaq(rqstNo);
//			logger.debug("시퀀스 스크립트 생성1");
		for (Grant grt : grts) {
//				logger.debug("시퀀스 스크립트 생성2");
			//DDL 스크립트를 가져온다.
			String ddlscript = getDDlScriptGrt(grt);
			
			WaqDdlGrt waqgrt = new WaqDdlGrt();
			waqgrt.setRqstNo(grt.getRqstNo());
			waqgrt.setRqstSno(grt.getRqstSno());
			waqgrt.setScrtInfo(ddlscript);
			
			waqlist.add(waqgrt);
			
		}
		
		return waqlist;
	}
	
	/** @param grant
	/** @return syyoo
	 * @throws IOException */
	private String getDDlScriptGrt(Grant grt) throws IOException {
		logger.debug("grt:{}", grt);
		
		/** sql 결과 */
		StringWriter _writer = new StringWriter();;
		
		Database database = new Database();
		
		SqlBuilder sqlbuild = null;
		
		String dbtype = grt.getType();
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);
		
		//DDL 권한 설명 생성
		sqlbuild.writeGrantComment(grt);
		
		logger.info( _writer.toString());
		
		//신규

		if("C".equals(grt.getRegTypCd())) {
//			sqlbuild.createSequence(grt);
			sqlbuild.createObjectGrant(grt);
			return _writer.toString();
		}
		//변경
		if("U".equals(grt.getRegTypCd())) {
//			sqlbuild.dropSequence(grt);
//			sqlbuild.createSequence(grt);
			sqlbuild.revokeObjectGrant(grt);
			sqlbuild.createObjectGrant(grt);
			return _writer.toString();
		}
	
		//삭제
		if("D".equals(grt.getRegTypCd())) {
//			sqlbuild.dropSequence(grt);
			sqlbuild.revokeObjectGrant(grt);
			return _writer.toString();
		}
		
		return _writer.toString();
	}
	
	@Override
	public List<WaqDdlPart> updateDdlPartScirptWaq(WaqMstr mstVo) throws IOException {
		List<WaqDdlPart> waqlist = new ArrayList<WaqDdlPart>();
		
		String rqstNo = mstVo.getRqstNo();
		
//		List<Table> tables = mapper.selectDdlIndexListWaq(rqstNo);
		List<Table> tables = mapper.selectDdlPartbyRqstNo(rqstNo);
		if(tables.size() < 1){
			tables = mapper.selectDdlPartbyWaqDdlTblRqstNo(rqstNo);
		}
		
		for (Table table : tables) {
			
			//DDL 스크립트를 가져온다.
//			String ddlscript = getDDlScriptPartition(table);

			WaqDdlPart part = new WaqDdlPart();
			part.setRqstNo(table.getRqstNo());
			part.setRqstSno(table.getRqstSno());
			part.setDdlPartId(table.getPartition().getDdlPartId());
			String ddlscript = getDDlScriptPartByWaq(part, null);
			
			WaqDdlPart waqpart = new WaqDdlPart();
			waqpart.setRqstNo(table.getRqstNo());
			waqpart.setRqstSno(table.getRqstSno());
			waqpart.setScrtInfo(ddlscript);
			
			waqlist.add(waqpart);
			
		}
		
		return waqlist;
	}
	
	@Override
	public String getDdlGrtScript(String ddlGrtId) throws Exception {
		return mapper.selectDdlGrtByObjId(ddlGrtId);
	}
	
	@Override
	public List<WaqDdlEtc> updateDdlEtcScriptWaq(WaqMstr mstVo) throws IOException {
		List<WaqDdlEtc> waqlist = new ArrayList<WaqDdlEtc>();
		
		String rqstNo = mstVo.getRqstNo();
		
		List<EtcObject> etcs = mapper.selectDdlEtcListWaq(rqstNo);
		
		for (EtcObject etc : etcs) {
		
			//DDL 스크립트를 가져온다.
			String ddlscript = getDDlScriptEtc(etc);

//			logger.debug("\n ##ddlscript: {}", ddlscript);
			
			WaqDdlEtc waqetc = new WaqDdlEtc();
			waqetc.setRqstNo(etc.getRqstNo());
			waqetc.setRqstSno(etc.getRqstSno());
			waqetc.setScrtInfo(ddlscript);
			
			waqlist.add(waqetc);
			
		}
		
		return waqlist;
	}
	
	private String getDDlScriptEtc(EtcObject etc) throws IOException {
		logger.debug("\n etc:{}", etc);
	
		/** sql 결과 */
		StringWriter _writer = new StringWriter();;
		
		Database database = new Database();
		
		SqlBuilder sqlbuild = null;
		
		String dbtype = etc.getType();
		
		sqlbuild = getDbmsBuilder(dbtype);
		
		//buffer 초기화...
		_writer.getBuffer().setLength(0);
		sqlbuild.setWriter(_writer);
		
		//DDL 기타오브젝트 설명 생성
//		sqlbuild.writeEtcComment(etc);
//		logger.info( _writer.toString());
		
		logger.debug("\n ##regTypCd: {}", etc.getRegTypCd());
		
		// 신규 or 변경
		if("C".equals(etc.getRegTypCd()) || "U".equals(etc.getRegTypCd())) {
			sqlbuild.createEtcObject(etc);
			return _writer.toString();
		
		} 
		
		// 삭제
		else if("D".equals(etc.getRegTypCd())) {
			sqlbuild.dropEtcObject(etc);
			return _writer.toString();
		}
		
		return _writer.toString();


	}

	@Override
	public String getDdlSeqScriptWaqRqstSno(WaqDdlSeq seqVo) throws IOException {
		List<Sequence> seqs = mapper.selectDdlSeqListWaqRqstSno(seqVo);   

		String ddlscript = "";
		
		for (Sequence seq : seqs) {
			//DDL 스크립트를 가져온다.
			ddlscript += getDDlScriptSeq(seq);
		}

		return ddlscript;
	}

	@Override
	public String getWahDdlGrtScript(String ddlGrtId, String rqstNo) throws Exception {
		
		Grant grt = mapper.selectGrantForWah(ddlGrtId, rqstNo);   
		
		return getDDlScriptGrt(grt);
	}

}
