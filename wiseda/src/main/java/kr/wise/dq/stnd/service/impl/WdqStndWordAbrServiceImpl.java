package kr.wise.dq.stnd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.inject.Inject;

import kr.wise.commons.cmm.LoginVO;
import kr.wise.commons.helper.UserDetailHelper;
import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.util.UtilString;
import kr.wise.dq.stnd.service.WdqStndWordAbrService;
import kr.wise.dq.stnd.service.WdqmStwdAbr;
import kr.wise.dq.stnd.service.WdqmStwdAbrMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("wdqStndWordAbrService")
public class WdqStndWordAbrServiceImpl implements WdqStndWordAbrService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Inject
	private WdqmStwdAbrMapper abrMapper;
	
	@Inject
	private RequestMstService requestMstService;
	
	@Override
	public List<WdqmStwdAbr> selectStwdAbbreviatedList(WdqmStwdAbr searchVO) {
		return abrMapper.selectStwdAbbreviatedList(searchVO);
	}
	
	/**
	 * TDODO 대문자로 변환 후 영문과 공백을 제외한 문자 제거(숫자는 맨뒤로)
	 */
	public String delSpec(String inLine) {

		StringBuffer sb = new StringBuffer();
		String Number = "";

		inLine = inLine.toUpperCase().replace('\\', '/');
		byte[] byteStrArr = inLine.getBytes();
		for (int i = 0; i < byteStrArr.length; i++) {
			if ((byteStrArr[i] == 32)
					|| (byteStrArr[i] > 64 && byteStrArr[i] <= 90))
				sb.append((char) byteStrArr[i] + "");
			if (byteStrArr[i] > 47 && byteStrArr[i] <= 57) {
				Number += (char) byteStrArr[i];
			}
		}
		if (Number.length() > 0)
			sb.append(" " + Number);
		return sb.toString();
	}

	public String getConsonant(String input) {
		StringBuffer sb = new StringBuffer();

		sb.append((char) input.charAt(0));
		for (int i = 1; i < input.length(); i++) {
			char c = input.charAt(i);

			if (c < 'A' || !isVowel(c) && sb.charAt(sb.length() - 1) != c) {
				sb.append(c);
			}
		}
		return sb.toString().trim();
	}

	// TODO 영문약어를 생성한다.
	//else if에서 true 표시된 절은 IWiseMetaConst.STWD_ABVT_RULE 를 치환한 것
	public ArrayList generateInitEnm(String orglEnm, String consEnm,
			String Length, String rule, String stndAsrt) throws Exception {

		String[] termInitEnm = new String[5];
		String[] errTxt = new String[5];

		String[] ruletermInitEnm = new String[termInitEnm.length];
		String[] ruleerrTxt = new String[errTxt.length];

		String returnValue = "";
		String genEnm = "";
		int initLength = Integer.parseInt(Length);
		int cnt = 1;

		ArrayList listValue = new ArrayList();

		int tmpi = 0;
		int divsNum = initLength;
		int getNum = 0;

		// 띄어쓰기 기준으로 단어를 썬다.
		ArrayList lso = getStringtoArray(orglEnm);
		ArrayList lsc = getStringtoArray(consEnm);
		
		try {
			// 1순위 영문의미가 여러 문자로 구성된 경우 각 문자의 앞자리만 가져온다.(숫자의 경우 전부 표시)
			if (lso.size() > 1) {
				getNum = (int) Math.floor(initLength / lso.size());
				if (getNum < 1)
					getNum = 1;
				genEnm = UtilString.getLeft(lso.get(0).toString(), getNum);
				divsNum = initLength - getNum;
				for (int i = 1; i < lso.size(); i++) {
					getNum = (int) Math.floor(divsNum / (lso.size() - i));
					if (!isNumber(lso.get(i).toString())) {
						genEnm += UtilString.getLeft(lso.get(i).toString(),
								getNum);
					}
					divsNum -= getNum;
				}
				if (isNumber(lso.get(lso.size() - 1).toString())) {
					getNum = initLength
							- lso.get(lso.size() - 1).toString().length();
					if (getNum < 1)
						getNum = 1;
					genEnm = UtilString.getLeft(genEnm, getNum)
							+ lso.get(lso.size() - 1).toString();
				}
				if (!isExist(termInitEnm, genEnm, tmpi)) {
					termInitEnm[tmpi] = genEnm;
					tmpi++;
				} else if (true) {
					termInitEnm[tmpi] = "";
					tmpi++;
				}
			}
			// 2순위 첫문자의 앞에서 약어길이만큼 가져온다.(영문의미 모음제거)
			genEnm = UtilString.getLeft(lsc.get(0).toString(), initLength);
			if (!isExist(termInitEnm, genEnm, tmpi)) {
				termInitEnm[tmpi] = genEnm;
				tmpi++;
			} else {
				termInitEnm[tmpi] = "";
				tmpi++;
			}
			// 3순위 첫문자의 앞에서 약어길이만큼 가져온다.(영문의미)
			genEnm = UtilString.getLeft(lso.get(0).toString(), initLength);
			if (!isExist(termInitEnm, genEnm, tmpi)) {
				termInitEnm[tmpi] = genEnm;
				tmpi++;
			} else if (true) {
				termInitEnm[tmpi] = "";
				tmpi++;
			}
			
			
			
			
			// 4순위 첫문자의 앞과 뒤에서 약어길이만큼 가져온다.(영문의미 모음제거)
			getNum = initLength - (int) Math.floor(initLength / 2);
			genEnm = UtilString.getLeft(lsc.get(0).toString(), getNum);
			if (lsc.get(0).toString().length() - getNum < (int) Math
					.floor(initLength / 2)) {
				getNum = lsc.get(0).toString().length() - getNum;
				if (getNum < 0)
					getNum = 0;
			} else {
				getNum = (int) Math.floor(initLength / 2);
			}
			genEnm += UtilString.getRight(lsc.get(0).toString(), getNum);
			if (!isExist(termInitEnm, genEnm, tmpi)) {
				termInitEnm[tmpi] = genEnm;
				tmpi++;
			} else if (true) {
				termInitEnm[tmpi] = "";
				tmpi++;
			}
			// 5순위 첫문자의 앞과 뒤에서 약어길이만큼 가져온다.(영문의미)
			getNum = initLength - (int) Math.floor(initLength / 2);
			genEnm = UtilString.getLeft(lso.get(0).toString(), getNum);
			if (lso.get(0).toString().length() - getNum < (int) Math
					.floor(initLength / 2)) {
				getNum = lso.get(0).toString().length() - getNum;
				if (getNum < 0)
					getNum = 0;
			} else {
				getNum = (int) Math.floor(initLength / 2);
			}
			genEnm += UtilString.getRight(lso.get(0).toString(), getNum);
			if (!isExist(termInitEnm, genEnm, tmpi)) {
				termInitEnm[tmpi] = genEnm;
				tmpi++;
			} else if (true) {
				termInitEnm[tmpi] = "";
				tmpi++;
			}

			// 영문약어 기존재 검증
			for (int i = 0; i < tmpi; i++) {
				returnValue = selectDic(termInitEnm[i],stndAsrt);
				errTxt[i] = returnValue;
			}
			if (!rule.equals("")) {
				// Rule 순서변경
				for (int i = 0; i < tmpi; i++) {
					if (rule.equals(Integer.toString(i + 1))) {
						ruletermInitEnm[0] = termInitEnm[i];
						ruleerrTxt[0] = errTxt[i];
					} else {
						ruletermInitEnm[cnt] = termInitEnm[i];
						ruleerrTxt[cnt++] = errTxt[i];
					}
				}
				listValue.add(0, ruletermInitEnm);
				listValue.add(1, ruleerrTxt);
			} else {
				listValue.add(0, termInitEnm);
				listValue.add(1, errTxt);
			}
		} catch (Exception e) {

			logger.debug("\nException Error termNm:" + termInitEnm.toString());
			throw e;
		} finally {

			if (termInitEnm != null) {
				termInitEnm = null;
			}
			if (errTxt != null) {
				errTxt = null;
			}
		}
		return listValue;
	}

	public boolean isNumber(String number) {
		boolean result = true;
		if (number == "") {
			result = false;
		}
		for (int i = 0; i < number.length(); i++) {
			if (!Character.isDigit(number.charAt(i)))
				result = false;
		}
		return result;
	}

	// 기존에 존재하는지를 확인
	public boolean isExist(String[] termInitEnm, String genEnm, int tmpi) {
		boolean result = false;

		for (int i = 0; i < tmpi; i++) {
			if (termInitEnm[i].equals(genEnm))
				result = true;
		}
		return result;
	}

	public String selectDic(String checkWord,String stndAsrt) throws Exception {

		
		String result = "";
		
		WdqmStwdAbr list = abrMapper.checkOverlap(UtilString.null2Blank(checkWord),stndAsrt);
		
		if(list.getOverlapCount() == 0) {
			result = "";
		} else {
			result = "영문약어 존재";
		}
			

		return result;
	}
	
	public ArrayList getStringtoArray(String inEnm) {
		StringTokenizer st = new StringTokenizer(inEnm, " ");
		ArrayList al = new ArrayList(); 

		String s = null;

		while(st.hasMoreTokens())
		{
			s = st.nextToken();
			if(!(s.length() < 2 || s.equals("THE")) || Character.isDigit(s.charAt(0)))
			{
				al.add(s);
			}
		}
		if(al.isEmpty())
			al.add(s);
		return al;
	}
	
	/**
	 * 모음여부
	 */
	public boolean isVowel(char cinput)
	{
		boolean brst = false;
		String moum  = "AEIOU";
		
		for(int i=0; i < moum.length(); i++)
		{
			if(moum.charAt(i)==cinput)
				brst = true;
		}
		return brst;
	}
	
	public int regStndWordByAbr(WaqMstr mstVo, ArrayList<WdqmStwdAbr> list){
		
		LoginVO user = (LoginVO) UserDetailHelper.getAuthenticatedUser();
		String userid = user.getUniqId();

		//마스터 정보 확인 : 상태정보가 작성전("N")일 경우 신규 등록 처리
		if( "N".equals(mstVo.getRqstStepCd())) {
			requestMstService.insertWaqMst(mstVo);
		}

		String rqstNo = mstVo.getRqstNo();

		int result = 0;

		if(list != null) {
			for (WdqmStwdAbr saveVo : (ArrayList<WdqmStwdAbr>)list) {
				//선택된 데이터 만
				if(saveVo.getIbsCheck().equals("1") ){
					//요청번호 셋팅...
					saveVo.setFrsRqstUserId(userid);
					saveVo.setRqstUserId(userid);
					saveVo.setRqstNo(rqstNo);
					saveVo.setRqstDcd("CU");
					//단건 저장...
					result += abrMapper.insertStndWordByAbr(saveVo);
				}
			}
		}

		mstVo.setRqstStepCd("S"); //임시저장 상태로 변경....
		requestMstService.updateRqstPrcStep(mstVo);
		return result;
	}
	
	@Override
	public Map<String, String> delStwdAbrLst(List<WdqmStwdAbr> list) throws Exception {
		Map<String, String> resultMap =  new HashMap<String, String>();
		int rtnCnt = 0;
		String abrId = "";
		for (WdqmStwdAbr savevo : list) {
			abrId = savevo.getAbrId();
			rtnCnt += abrMapper.delStwdAbrLst(savevo);
		}
		
		
		resultMap.put("result", Integer.toString(rtnCnt) );
		resultMap.put("abrId", abrId);
		
		return resultMap;
		
	}

	
}
