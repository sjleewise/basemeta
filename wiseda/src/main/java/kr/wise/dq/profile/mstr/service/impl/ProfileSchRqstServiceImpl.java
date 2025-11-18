package kr.wise.dq.profile.mstr.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaMapper;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaMapper;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfMapper;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaMapper;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaMapper;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaVO;
import kr.wise.dq.profile.mstr.service.ProfileSchRqstService;
import kr.wise.meta.dbc.service.WatDbcColMapper;

import org.springframework.stereotype.Service;

@Service("ColProfileBatchRqstService")
public class ProfileSchRqstServiceImpl implements ProfileSchRqstService {

	@Inject
	WatDbcColMapper watDbcColMapper;
	
	@Inject
	WaqPrfColAnaMapper waqPrfColAnaMapper;
	
	@Inject
	WaqPrfEfvaUserDfMapper waqPrfEfvaUserDfMapper;
	
	@Inject
	WaqPrfDtfrmAnaMapper waqPrfDtfrmAnaMapper;

	@Inject
	WaqPrfRngAnaMapper waqPrfRngAnaMapper;
	
	@Inject
	WaqPrfPtrAnaMapper waqPrfPtrAnaMapper;


	@Override
	public List<WaqPrfColAnaVO> getVrfProfileListIBS(WaqMstr search) {
		return waqPrfColAnaMapper.selectProfileRqstList(search);
	}


	@Override
	public List<WaqPrfColAnaVO> getDbcColListPC01(ArrayList<?> list) {
		List<WaqPrfColAnaVO> newlist = new ArrayList<WaqPrfColAnaVO>();
		for (Object record : list) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
			newlist.addAll(waqPrfColAnaMapper.getDbcColList(record));
		}
		return newlist;
	}

	
	@Override
	public List<WaqPrfEfvaUserDfVO> getDbcColListPC02(ArrayList<?> list) {
		List<WaqPrfEfvaUserDfVO> newlist = new ArrayList<WaqPrfEfvaUserDfVO>();
		for (Object record : list) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
			newlist.addAll(waqPrfEfvaUserDfMapper.getDbcColList(record));
		}
		return newlist;
	}


	@Override
	public List<WaqPrfDtfrmAnaVO> getDbcColListPC03(ArrayList<?> list) {
		List<WaqPrfDtfrmAnaVO> newlist = new ArrayList<WaqPrfDtfrmAnaVO>();
		for (Object record : list) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
			newlist.addAll(waqPrfDtfrmAnaMapper.getDbcColList(record));
		}
		return newlist;
	}


	@Override
	public List<WaqPrfRngAnaVO> getDbcColListPC04(ArrayList<?> list) {
		List<WaqPrfRngAnaVO> newlist = new ArrayList<WaqPrfRngAnaVO>();
		for (Object record : list) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
			newlist.addAll(waqPrfRngAnaMapper.getDbcColList(record));
		}
		return newlist;
	}

	@Override
	public List<WaqPrfPtrAnaVO> getDbcColListPC05(ArrayList<?> list) {
		List<WaqPrfPtrAnaVO> newlist = new ArrayList<WaqPrfPtrAnaVO>();
		for (Object record : list) {
			//그리드 상태가 있을 경우만 DB에 처리한다...
			newlist.addAll(waqPrfPtrAnaMapper.getDbcColList(record));
		}
		return newlist;
	}

}
