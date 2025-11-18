package kr.wise.commons.code.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import kr.wise.commons.code.service.CmcdCodeService;
import kr.wise.commons.code.service.CodeListVo;
import kr.wise.commons.code.service.ComboIBSVo;
import kr.wise.commons.code.service.CommonCodeMapper;
import kr.wise.commons.code.service.WaaCommDcd;
import kr.wise.commons.code.service.WaaCommDtlCd;
import kr.wise.commons.util.UtilString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
/*import kr.wise.edu.mapper.WaaCommonCodeMapper;
import kr.wise.edu.model.WaaCommonCode;
import kr.wise.egmd.aprv.mapper.AprvRqstMapper;
import kr.wise.egmd.aprv.model.AprvRqstDto;
import kr.wise.egmd.cmcd.mapper.CmcdCodeMapper;
import kr.wise.egmd.cmcd.mapper.WaaCommDcdMapper;
import kr.wise.egmd.cmcd.mapper.WaaCommDtlCdMapper;
import kr.wise.egmd.cmcd.model.CmcdCodeDto;
import kr.wise.egmd.cmcd.model.CodeListVo;
import kr.wise.egmd.cmcd.model.ComboIBSVo;
import kr.wise.egmd.cmcd.model.WaaCommDcd;
import kr.wise.egmd.cmcd.model.WaaCommDtlCd;
import kr.wise.egmd.helper.IBSheetJsonModel;
import kr.wise.egmd.hist.service.HistPrcsService;
import kr.wise.egmd.rqstmst.model.CommonDto;
import kr.wise.egmd.rqstmst.model.RqstMstDto;
import kr.wise.egmd.rqstmst.service.RqstMstService;
import kr.wise.egmd.util.UtilString;
*/

import com.fasterxml.jackson.databind.ObjectMapper;



/**
 * <PRE>
 * 1. ClassName : CmcdCodeService
 * 2. FileName  : CmcdCodeService.java
 * 3. Package  : kr.wise.egmd.cmcd.service
 * 4. Comment  : 메타에서 사용하는 공통코드 리스트를 DB에서 조회해서 Map<String, List> 형태로 담는다.
 * 				 클래스를 빈으로 등록하고 빈 초기화시 실행한다..
 * 5. 작성자   : insomnia(장명수)
 * 6. 작성일   : 2013. 3. 28. 오후 4:28:09
 * </PRE>
 */
@Service("cmcdCodeService")
public class CmcdCodeServiceImpl implements CmcdCodeService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

//	private List<WaaCommonCode> waaCodeList;

//	private List<WaaCommDcd> commDcdList;

	private Map<String,WaaCommDcd> cmcdCodeMap; //공통코드 전체 맵 (코드, 객체)

//	@Autowired
//	private CmcdCodeMapper cmcdCodeMapper;

//	@Autowired
//	private WaaCommonCodeMapper commCodeMapper;

//	@Autowired
//	private WaaCommDcdMapper commonCodeMapper;

	@Inject
	private CommonCodeMapper commonCodeMapper;

//	@Autowired
//	private WaaCommDtlCdMapper waaCommDtlCdMapper;



	/**
	 * 공통코드 조회 (Jason 형식)
	 * @param dto
	 * @return
	 */
/*	public List<HashMap> selectCmcdCodeList(CmcdCodeDto dto)
	{
		List<HashMap> objList = cmcdCodeMapper.selectCmcdCodeList(dto);

		return objList;
	}*/

	/**
	 * 공통코드관리 조회
	 * @param dto
	 * @return
	 */
/*	//공통코드조회
	public List<WaaCommDcd> getCodeList(WaaCommDcd search) {
		List<WaaCommDcd> list = waaCommDcdMapper.selectList(search);
		return list;
	}
	//공통코드상세조회
	public List<WaaCommDtlCd> getCodeDtlList(WaaCommDtlCd search) {
		List<WaaCommDtlCd> list = waaCommDtlCdMapper.selectList(search);
		return list;
	}
	//공통코드입력
	public void regCmcdCodeList(ArrayList<WaaCommDcd> list) {
		for (WaaCommDcd waaCommDcd : list) {
			regCodearea(waaCommDcd);
		}
	}
	//공통코드상세입력
	public void regCmcdCodeDtlList(ArrayList<WaaCommDtlCd> list) {
		for (WaaCommDtlCd waaCommDtlCd : list) {
			regCodeDtlarea(waaCommDtlCd);
		}
	}*/

/*	public int regCodearea(WaaCommDcd record) {
		String tmpStatus = record.getIbsStatus();
		int result = -1;

		if("I".equals(tmpStatus)) {
			System.out.println("생성");
			record.setRegTypCd("C");
			result = waaCommDcdMapper.insertSelective(record);
		} else if("U".equals(tmpStatus)) {
			System.out.println("수정");
			record.setRegTypCd("U");
			result = waaCommDcdMapper.updateByPrimaryKeySelective(record);
		} else if("D".equals(tmpStatus)) {
			System.out.println("삭제");
			record.setRegTypCd("D");

			result = waaCommDcdMapper.deleteDtlByPrimaryKey(record.getCommDcdId()); //상세코드 삭제
			result = waaCommDcdMapper.deleteByPrimaryKey(record.getCommDcdId());
		}
		return result;
	}*/
/*	public int regCodeDtlarea(WaaCommDtlCd record) {
		String tmpStatus = record.getIbsStatus();
		int result = -1;
		System.out.println("================일단");
		if("I".equals(tmpStatus)) {
			System.out.println("생성");
			record.setRegTypCd("C");
			result = waaCommDtlCdMapper.insertSelective(record);
		} else if("U".equals(tmpStatus)) {
			System.out.println("수정");
			record.setRegTypCd("U");
			result = waaCommDtlCdMapper.updateByPrimaryKeySelective(record);
		} else if("D".equals(tmpStatus)) {
			System.out.println("삭제");
			record.setRegTypCd("D");
			result = waaCommDtlCdMapper.deleteByPrimaryKey(record.getCommDtlCdId(),record.getExpDtm());
		}
		return result;
	}*/

/*	public void delCmcdCodeList(ArrayList<WaaCommDcd> list) {
		for (WaaCommDcd waaCommDcd : list) {
			String id = waaCommDcd.getCommDcdId();
			if (id != null && !"".equals(id)) {
				waaCommDcd.setIbsStatus("D");
				regCodearea(waaCommDcd);
			}
		}

	}*/
/*	public void delCmcdCodeDtlList(ArrayList<WaaCommDtlCd> list) {
		for (WaaCommDtlCd waaCommDtlCd : list) {
			String id = waaCommDtlCd.getCommDtlCdId();
			if (id != null && !"".equals(id)) {
				waaCommDtlCd.setIbsStatus("D");
				regCodeDtlarea(waaCommDtlCd);
			}
		}

	}*/

	/**
	 * <PRE>
	 * 1. MethodName : init
	 * 2. Comment    : 공통코드를 DB에서 가져와 맵에 저장하는 초기화 메소드
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 3. 28.
	 * </PRE>
	 *   @return void
	 */
	@Override
	@PostConstruct
	public void init() {
		logger.debug("Cmcd init start");

		//공통코드 맵을 확인 후 있으면 비운다.
		if(cmcdCodeMap !=null &&  !cmcdCodeMap.isEmpty()) {
			cmcdCodeMap = null;
		}

		//공통코드 전체 리스트를 매퍼를 통해 리스트에 담는다.
		List<WaaCommDcd> commDcdList = new ArrayList<WaaCommDcd>();
//		commDcdList = waaCommDcdMapper.selectListCollect(null);
		commDcdList = commonCodeMapper.selectCodeList(null);

		//공통코드 맵을 초기화 한다.
		cmcdCodeMap = new HashMap<String, WaaCommDcd>();

		//리스트의 내용을 공통코드, 공통코드객체 형태의 맵으로 변
		for (WaaCommDcd commDcd : commDcdList) {
			cmcdCodeMap.put(commDcd.getCommDcd(), commDcd);
		}

		logger.debug("codelist : {} ", cmcdCodeMap.size() );

	}


	public Map<String, WaaCommDcd> getCmcdCodeMap() {
		return cmcdCodeMap;
	}


	public void setCmcdCodeMap(Map<String, WaaCommDcd> cmcdCodeMap) {
		this.cmcdCodeMap = cmcdCodeMap;
	}


	/** 공통코드 조회 - 코드값 */
	public WaaCommDcd getCmcdDcdbyCode(String Code) {
		//공통코드 맵에 내용 확인 후 없으면 초기화 한다.
		if(cmcdCodeMap ==null || cmcdCodeMap.isEmpty()) {
			init();
		}

//		해당 코드를 찾아서 리턴한다.
		try {
			if(this.cmcdCodeMap.get(Code) == null) {
				return new WaaCommDcd();
			} else {
				return this.cmcdCodeMap.get(Code);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return new WaaCommDcd();
		}

	}

	/** 공통코드의 상세코드 리스트 조회 */
	@Override
	public List<CodeListVo> getCodeList(String codenm) {
		if(getCmcdDcdbyCode(codenm).getCodeLists() == null || getCmcdDcdbyCode(codenm).getCodeLists().isEmpty()) {
			return new ArrayList<CodeListVo>();
		} else {
			return getCmcdDcdbyCode(codenm).getCodeLists();
		}
		
	}

	/** IBSheet Combo 코드용 코드 조회  */
	@Override
	public ComboIBSVo getCodeListIBS(String codenm) {

		ComboIBSVo comboIbs = new ComboIBSVo();
		StringBuilder code = new StringBuilder();
		StringBuilder text = new StringBuilder();

		List<CodeListVo> codeList = getCodeList(codenm);

		int i = 0;
		for (CodeListVo vo : codeList) {
			if(i++ > 0) {
				code.append("|");
				text.append("|");
			}
			code.append(vo.getCodeCd());
			text.append(vo.getCodeLnm());
		}

		comboIbs.ComboCode = code.toString();
		comboIbs.ComboText = text.toString();

		return comboIbs;
	}

	/**
	 * <PRE>
	 * 1. MethodName : getCommDtlCdforIBS
	 * 2. Comment    : 코드부구분에 따른 상세 코드를 "|"로 조인하여 리턴한다. (IBSheet 용도)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 1.
	 * </PRE>
	 *   @return String
	 *   @param commCd
	 *   @return
	 */
	public String getCommDtlCdforIBS(String commCd) {
		StringBuilder sb = new StringBuilder();

		WaaCommDcd waacommDcd =  getCmcdDcdbyCode(commCd);

		int i = 0;
		for (WaaCommDtlCd commDtlCd : waacommDcd.getCommDtlCds()) {
			if(i++ > 0) {
				sb.append("|");
			}
			sb.append(commDtlCd.getCommDtlCd()) ;
		}

		return sb.toString();

	}

	/**
	 * <PRE>
	 * 1. MethodName : getCommDtlCdNmforIBS
	 * 2. Comment    : 코드부구분에 따른 상세 코드명을 "|"로 조인하여 리턴한다. (IBSheet 용도)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 1.
	 * </PRE>
	 *   @return String
	 *   @param commCd
	 *   @return
	 */
	public String getCommDtlCdNmforIBS(String commCd) {
		StringBuilder sb = new StringBuilder();

		WaaCommDcd waacommDcd =  getCmcdDcdbyCode(commCd);

		int i = 0;
		for (WaaCommDtlCd commDtlCd : waacommDcd.getCommDtlCds()) {
			if(i++ > 0) {
				sb.append("|");
			}
			sb.append(commDtlCd.getCommDtlCdNm()) ;
		}

		return sb.toString();

	}

	/**
	 * <PRE>
	 * 1. MethodName : getCodeForIBS
	 * 2. Comment    : IBS용 공통코드 제공 - String[0]-상세코드 "|" 조인,  String[1]-상세코드명 "|" 조인
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 1.
	 * </PRE>
	 *   @return String[]
	 *   @param commCd
	 *   @return
	 */
	public String[] getCodeForIBS(String commCd) {
		StringBuilder sbcd = new StringBuilder();
		StringBuilder sbnm = new StringBuilder();

		WaaCommDcd waacommDcd =  getCmcdDcdbyCode(commCd);
		if(waacommDcd != null && !waacommDcd.getCommDtlCds().isEmpty()) {

			int i = 0;
			for (CodeListVo commDtlCd : waacommDcd.getCodeLists()) {
				if(i++ > 0) {
					sbcd.append("|");
					sbnm.append("|");
				}
				sbcd.append(commDtlCd.getCodeCd()) ;
				sbnm.append(commDtlCd.getCodeLnm()) ;
			}
//			for (WaaCommDtlCd commDtlCd : waacommDcd.getCommDtlCds()) {
//				if(i++ > 0) {
//					sbcd.append("|");
//					sbnm.append("|");
//				}
//				sbcd.append(commDtlCd.getCommDtlCd()) ;
//				sbnm.append(commDtlCd.getCommDtlCdNm()) ;
//			}
		}

		return new String[]{sbcd.toString(), sbnm.toString()};

	}

	/**
	 * <PRE>
	 * 1. MethodName : getCodeJsonIBS
	 * 2. Comment    : IBSheet용 콤보 코드를 VO 형태로 제공(Json으로 변환하여 사용 가능...)
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 18.
	 * </PRE>
	 *   @return ComboIBSVo
	 *   @param commCd
	 *   @return
	 */
	public ComboIBSVo getCodeJsonIBS(String commCd) {

		ComboIBSVo comboIbs = new ComboIBSVo();

		String[] combo = getCodeForIBS(commCd);

		comboIbs.ComboCode = combo[1];
		comboIbs.ComboText = combo[2];

		return comboIbs;
	}

	/**
	 * <PRE>
	 * 1. MethodName : getComboIBSJson
	 * 2. Comment    : 공통 코드를 IBSheet용 Json String 형태로 제공...
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 18.
	 * </PRE>
	 *   @return String
	 *   @param codenm
	 *   @return
	 */
	public String getComboIBSJson(String codenm) {

		String result = null;

		ComboIBSVo vo = getCodeJsonIBS(codenm);

		ObjectMapper om = new ObjectMapper();

		try {
			result = om.writeValueAsString(vo);
		} catch (Exception e) {
			logger.error("[ERROR] : getComboIBSJson({})", codenm);
		}

		logger.debug(result);

		return result;
	}


	/** 공통 상세코드명을 가져온다. insomnia */
	public String getDetailCodeNm(String commDcd, String commDtlCd) {
		List<CodeListVo> codeList = getCodeList(commDcd);

		if(!UtilString.null2Blank(commDtlCd).equals("")){
			for (CodeListVo codevo : codeList) {
				if (commDtlCd.equals(codevo.getCodeCd())) {
					return codevo.getCodeLnm();
				}
			}
		}
		return null;
	}


	/**
	 * <PRE>
	 * 1. MethodName : codelist
	 * 2. Comment    : 공통코드구분 리스트를 가져온다.
	 * 3. 작성자       : insomnia(장명수)
	 * 4. 작성일       : 2013. 4. 6.
	 * </PRE>
	 *   @return void
	 *   @param waaCommDcd
	 * @return
	 */
/*	public List<WaaCommDcd> codelist(WaaCommDcd waaCommDcd) {
		//공통코드 전체 리스트를 매퍼를 통해 리스트에 담는다.
		List<WaaCommDcd> commDcdList = new ArrayList<WaaCommDcd>();
		commDcdList = commonCodeMapper.selectListCollect(waaCommDcd);
		return commDcdList;

	}*/





}
