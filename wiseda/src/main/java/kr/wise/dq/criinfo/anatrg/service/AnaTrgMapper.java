package kr.wise.dq.criinfo.anatrg.service;

import java.util.List;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface AnaTrgMapper {

	List<AnaTrgTblVO> selectAnaTrgTblLst(AnaTrgTblVO search);
	
	List<AnaTrgColVO> selectAnaTrgColLst(AnaTrgTblVO search);
	
	List<AnaTrgTblVO> selectPrfTblLst(AnaTrgTblVO search);
	
	List<AnaTrgColVO> selectPrfColLst(AnaTrgTblVO search);
	
	AnaTrgTblVO selectAnaTrgTblDtl(AnaTrgTblVO search);
	
	 AnaTrgTblVO selectAnaTrgColDtl(AnaTrgTblVO search);

	/** @param search
	/** @return meta */
	List<AnaTrgTblVO> selectPrfTblLstNotRedline(AnaTrgTblVO search);

	/** @param search
	/** @return meta */
	List<AnaTrgColVO> selectPrfColLstNotRedline(AnaTrgTblVO search);
	
	AnaTrgTblVO selectAnaTrgTblReacDtl(AnaTrgTblVO search);

	List<AnaTrgColVO> selectPrfColLstCheck(AnaTrgTblVO search);

	List<AnaTrgTblVO> selectPrfTblLstAna(AnaTrgTblVO search);	
}