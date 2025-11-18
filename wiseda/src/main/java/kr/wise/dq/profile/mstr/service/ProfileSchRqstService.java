package kr.wise.dq.profile.mstr.service;

import java.util.ArrayList;
import java.util.List;

import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.dq.profile.colana.service.WaqPrfColAnaVO;
import kr.wise.dq.profile.coldtfrmana.service.WaqPrfDtfrmAnaVO;
import kr.wise.dq.profile.colefvaana.service.WaqPrfEfvaUserDfVO;
import kr.wise.dq.profile.colptrana.service.WaqPrfPtrAnaVO;
import kr.wise.dq.profile.colrngana.service.WaqPrfRngAnaVO;

public interface ProfileSchRqstService {

	List<WaqPrfColAnaVO> getDbcColListPC01(ArrayList<?> list);

	List<WaqPrfColAnaVO> getVrfProfileListIBS(WaqMstr search);

	List<WaqPrfEfvaUserDfVO> getDbcColListPC02(ArrayList<?> list);

	List<WaqPrfDtfrmAnaVO> getDbcColListPC03(ArrayList<?> list);

	List<WaqPrfRngAnaVO> getDbcColListPC04(ArrayList<?> list);

	List<WaqPrfPtrAnaVO> getDbcColListPC05(ArrayList<?> list);

}
