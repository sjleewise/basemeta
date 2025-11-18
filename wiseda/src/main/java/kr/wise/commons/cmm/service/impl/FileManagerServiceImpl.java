package kr.wise.commons.cmm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import kr.wise.commons.cmm.FileVO;
import kr.wise.commons.cmm.service.FileManagerService;
import kr.wise.commons.cmm.service.FileMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @Class Name : EgovFileMngServiceImpl.java
 * @Description : 파일정보의 관리를 위한 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 25.     이삼섭    최초생성
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 25.
 * @version
 * @see
 *
 */

@Service("fileMngService")
public class FileManagerServiceImpl  implements FileManagerService {
	
	private static final Logger log = LoggerFactory.getLogger(FileManagerServiceImpl.class);
	
	@Inject
	private FileMapper  fileMapper;
	
	
    /**
     * 여러 개의 파일을 삭제한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteFileInfs(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public void deleteFileInfs(List fvoList) throws Exception {

    	for (Object fvo : fvoList) {
			fileMapper.deleteFileDetail((FileVO)fvo);
		}
//    	fileMapper.deleteFileInfs(fvoList);
    }


    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#insertFileInf(egovframework.com.cmm.service.FileVO)
     */
    public String insertFileInf(FileVO fvo) throws Exception {

		String atchFileId = fvo.getAtchFileId();
		fileMapper.insertFileMaster(fvo);
		fileMapper.insertFileDetail(fvo);
		return atchFileId;
    }


    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#insertFileInfs(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public String insertFileInfs(List fvoList) throws Exception {

		String atchFileId = "";
		if(!fvoList.isEmpty() && fvoList.size() > 0) {
			FileVO vo = (FileVO)fvoList.get(0);
			atchFileId = vo.getAtchFileId();
			fileMapper.insertFileMaster(vo);
		}
		for (Object fvo : fvoList) {
			fileMapper.insertFileDetail((FileVO) fvo);
		}
//		
//		if (fvoList.size() != 0) {
//		    atchFileId = fileMapper.insertFileInfs(fvoList);
//		}
	
		if(atchFileId == ""){
			atchFileId = null;
		}
	
		return atchFileId;

    }


    /**
     * 파일에 대한 목록을 조회한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#selectFileInfs(egovframework.com.cmm.service.FileVO)
     */
    public List<FileVO> selectFileInfs(FileVO fvo) throws Exception {
    	return fileMapper.selectFileList(fvo);
    }



    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#updateFileInfs(java.util.List)
     */
    @SuppressWarnings("unchecked")
    public void updateFileInfs(List fvoList) throws Exception {
	//Delete & Insert
    	for (Object vo : fvoList) {
			fileMapper.insertFileDetail((FileVO) vo);
		}
//    	fileMapper.updateFileInfs(fvoList);
    }


    /**
     * 하나의 파일을 삭제한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteFileInf(egovframework.com.cmm.service.FileVO)
     */
    public void deleteFileInf(FileVO fvo) throws Exception {
    	fileMapper.deleteFileDetail(fvo);
    }


    /**
     * 파일에 대한 상세정보를 조회한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#selectFileInf(egovframework.com.cmm.service.FileVO)
     */
    public FileVO selectFileInf(FileVO fvo) throws Exception {
    	return fileMapper.selectFileInf(fvo);
    }



    /**
     * 파일 구분자에 대한 최대값을 구한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#getMaxFileSN(egovframework.com.cmm.service.FileVO)
     */
    public int getMaxFileSN(FileVO fvo) throws Exception {
	return fileMapper.getMaxFileSN(fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#deleteAllFileInf(egovframework.com.cmm.service.FileVO)
     */
    public void deleteAllFileInf(FileVO fvo) throws Exception {
    	fileMapper.deleteCOMTNFILE(fvo);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#selectFileListByFileNm(egovframework.com.cmm.service.FileVO)
     */
    public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws Exception {
		List<FileVO>  result = fileMapper.selectFileListByFileNm(fvo);
		int cnt = fileMapper.selectFileListCntByFileNm(fvo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", result);
		map.put("resultCnt", Integer.toString(cnt));
		return map;
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     * 
     * @see egovframework.com.cmm.service.EgovFileMngService#selectImageFileList(egovframework.com.cmm.service.FileVO)
     */
    public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
    	return fileMapper.selectImageFileList(vo);
    }
	


}






