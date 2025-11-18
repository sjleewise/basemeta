package kr.wise.meta.stnd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.wise.commons.rqstmst.service.CommonRqstMapper;
import kr.wise.commons.rqstmst.service.WaqMstr;

import org.apache.ibatis.annotations.Param;

import kr.wise.commons.cmm.annotation.Mapper;


@Mapper
public interface WaqCdValMapper extends CommonRqstMapper{
	int deleteByPrimaryKey(WaqCdVal record);

    int insert(WaqCdVal record);

    int insertSelective(WaqCdVal record);

    WaqCdVal selectByPrimaryKey(@Param("rqstNo") String rqstNo, @Param("rqstSno") Integer rqstSno, @Param("rqstDtlSno") Integer rqstDtlSno);

    int updateByPrimaryKeySelective(WaqCdVal record);

    int updateByPrimaryKey(WaqCdVal record);

	/** @param search
	/** @return insomnia */
	List<WaqCdVal> selectCdvalRqstVal(WaqMstr search);

	/** @param saveVo insomnia */
	int deleteByrqstSno(WaqDmn saveVo);



	/** @param rqstNo insomnia */
	int updateCheckInit(String rqstNo);

	/** @param checkmap2 insomnia */
	int checkDupCdval(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNotExistCdVal(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNotExistDmn(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNotExistPrntCdVal(Map<String, Object> checkmap2);

	/** ibk신용정보용 부모유효값 체크 */
	int checkNotExistPrntCdValIbkC(Map<String, Object> checkmap2);
	
	/** @param checkmap2 insomnia */
	int checkChldCdValExist(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkCdValLen(Map<String, Object> checkmap2);

	/** @param checkmap2 insomnia */
	int checkNotChgData(Map<String, Object> checkmap2);
	
	/**대분류코드 존재 시 도메인의 유효값은 대분류코드와 같을 수 없다*/
	int checkCdValEqualDmnDscd(Map<String,Object> checkmap2);

	/** 코드값은 대분류코드 + 유효값 */
	int checkExistsDmnDscdCode(Map<String,Object> checkmap2);
	
	/** 단순코드는 2레벨을 넘을 수 없다 */
	int checkCodeLevel(Map<String, Object> checkmap2);
	
		//자식이 Y인 경우 부모를 사용여부 N으로 변경불가
    int checkExistsChildUseYn(Map<String,Object> checkmap2);
		//부모가 N인 경우 자식을 사용여부 Y로 변경불가
    int checkExistsPrntUseYn(Map<String,Object> checkmap2);
    
    //부모유효값과 자식유효값은 같을 수 없다 <EL> 제외
    int checkExistsPrntEqualChild(Map<String, Object> checkmap2);

    //코드값명은 길이 96자리를 넘을 수 없다.
    int checkLenCdValNm(Map<String ,Object> checkmap2);
    
    /** @param rqstno
	/** @return insomnia */
	List<WaqCdVal> selectWaqC(@Param("rqstNo") String rqstNo);

	/** @param savevo insomnia */
	int updateidByKey(WaqCdVal savevo);

	/** @param rqstno
	/** @return insomnia */
	int updateWaqId(@Param("rqstNo") String rqstNo);

	/** @param rqstno
	/** @return insomnia */
	int updateUppCdValId(@Param("rqstNo") String rqstNo);

	/** @param reqmst
	/** @param wamlist
	/** @return insomnia */
	int insertwam2waq(@Param("reqmst") WaqMstr reqmst, @Param("list") ArrayList<WaqDmn> wamlist);

	/** @param saveVo insomnia */
	int insertByDmnSno(WaqDmn saveVo);

	/** @param reqmst
	/** @return yeonho */
	int deleteAllCdValByRqstNo(WaqMstr reqmst);

	int insertWaqCdRejected(@Param("reqmst") WaqMstr reqmst, @Param("oldRqstNo") String oldRqstNo );
	
	/** 유효값 목록에 없을 경우 삭제로 추가*/
	int insertnoWaqdelCdVal(String rqstNo);

	/** @param checkmap2  */
	int checkCdValUppDmn(Map<String, Object> checkmap2);

//	void insertDmndelnoWaqdelCdVal(@Param("rqstNo") String rqstNo);

	void updateDmnDelCdValInfo(@Param("rqstNo")String rqstNo);

}