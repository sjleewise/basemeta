package kr.wise.commons.cmm.service;

import java.util.List;

import kr.wise.commons.cmm.FileVO;

import kr.wise.commons.cmm.annotation.Mapper;

@Mapper
public interface FileMapper {

	List<FileVO> selectImageFileList(FileVO vo);

	int selectFileListCntByFileNm(FileVO fvo);

//	void deleteAllFileInf(FileVO fvo);

//	void deleteFileInfs(List fvoList);

//	void insertFileInf(FileVO fvo);

	int getMaxFileSN(FileVO fvo);

	FileVO selectFileInf(FileVO fvo);

//	void deleteFileInf(FileVO fvo);

//	void updateFileInfs(List fvoList);

//	List<FileVO> selectFileInfs(FileVO fvo);

//	String insertFileInfs(List fvoList);

	List<FileVO> selectFileListByFileNm(FileVO fvo);

	void insertFileMaster(FileVO vo);

	void insertFileDetail(FileVO fvo);

	void deleteFileDetail(FileVO fvo);

	List<FileVO> selectFileList(FileVO fvo);

	void deleteCOMTNFILE(FileVO fvo);

}
