package kr.wise.meta.intf.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service("EraDataelmtService")
public class EraDataelmtServiceImpl implements EraDataelmtService {

	
	@Inject
	private EraDataelmtMapper eraDataelmtMapper;


	@Override
	public List<EraDataelmt> getEraDataelmtList(List<EraDataelmt> data, String trdGb) {
		
		List<EraDataelmt> list = new ArrayList<EraDataelmt>();
		EraDataelmt row1 = null;
		for(EraDataelmt row : data){
			if("gap".equals(trdGb)){
				row1 = eraDataelmtMapper.selectEraDataelmtOne(row.getSearchval(),row.getModelnm(),row.getSubjectnm());
				if(row1 != null){
					row.setTermnm(row1.getTermnm());
					row.setTermnmen(row1.getTermnmen());
					row.setDataType(row1.getDataType());
					row.setDomainnm(row1.getDomainnm());
					row.setDomainnmen(row1.getDomainnmen());
					row.setInfotype(row1.getInfotype());
					row.setDescription(row1.getDescription());
				}
				list.add(row);
			}else{
				if(StringUtils.isEmpty(row.getSearchcon())){
					row.setSearchcon("E"); //default equal 검색 set
				}
				
				if("Y".equals(row.getChecktermnmen())){
					list.addAll(eraDataelmtMapper.selectEraDataelmtEn(row.getSearchval(),row.getSearchcon(),row.getModelnm(),row.getSubjectnm()));
				}else{
					list.addAll(eraDataelmtMapper.selectEraDataelmt(row.getSearchval(),row.getSearchcon(),row.getModelnm(),row.getSubjectnm()));
				}
			}
		}
		
		return list;
	}

}
