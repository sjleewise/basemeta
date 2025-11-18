package kr.wise.portal.myjob.impl;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.portal.myjob.service.OwnShipMapper;
import kr.wise.portal.myjob.service.OwnShipService;
import kr.wise.portal.myjob.service.TblChangeVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service ("ownshipService")
public class OwnShipServiceImpl implements OwnShipService{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	private OwnShipMapper ownshipMapper;
	
	//데이터 리스트
	public List<TblChangeVO> selectTblChangeParam(Map<String, Object> param)throws Exception {
		return ownshipMapper.selectTblChangeParam(param);
	}
	//데이터 갯수
	public TblChangeVO selectTblChangeSub(Map<String, Object> param) throws Exception {
		
		return ownshipMapper.selectTblChangeSub(param);
	}
	//담당자ID 및 담당자 변경
	public void ownershipUpdate(Map<String, String> param)throws Exception {
		
		ownshipMapper.ownershipUpdate(param);
	}
	
	//변경 후 데이터 리스트
	public List<TblChangeVO> selectTblUpdateParam(Map<String, Object> param)throws Exception {

		return ownshipMapper.selectTblUpdateParam(param);
	}
	
	//변경 후 데이터 갯수
	public TblChangeVO selectTblUpdateSub(Map<String, Object> param)throws Exception {

		return ownshipMapper.selectTblUpdateSub(param);
	}
	
	//테이블 담당자 변경...
	public int ownerShipUpdate(Map<String, Object> param) {
//		ArrayList<String> objids = new ArrayList<String>();
		String objids = (String)param.get("objIds");
		if( StringUtils.hasLength(objids)) {
			String[] ids = objids.split(",");
			param.put("ids", ids);
			
			return ownshipMapper.updateOwnShip(param);
		}
		
		return 0;
	}

}
