package kr.wise.commons.damgmt.approve.service;

import java.util.ArrayList;
import java.util.List;

public interface AprgService {

	public List<WaaAprg> getList(WaaAprg search) ;

	public WaaAprg findAprg(WaaAprg search) ;

	public int regAprg(WaaAprg record) throws Exception ;

	public int regList(ArrayList<WaaAprg> list) throws Exception ;


	public int delList(ArrayList<WaaAprg> list) throws Exception ;

	public int getAprgCnt(WaaAprg waaAprg);

}
