package kr.wise.dq.profile.colptrana.service;

import kr.wise.dq.profile.mstr.service.WaqPrfMstrVO;

public class WaqPrfPtrAnaVO  extends WaqPrfMstrVO  {
	private Integer rqstDtlSno; 	// 요청상세일련번호
	
    private String userDfPtr;

    private String userDfPtrNm;
    
	public Integer getRqstDtlSno() {
		return rqstDtlSno;
	}

	public void setRqstDtlSno(Integer rqstDtlSno) {
		this.rqstDtlSno = rqstDtlSno;
	}

	public String getUserDfPtr() {
		return userDfPtr;
	}

	public void setUserDfPtr(String userDfPtr) {
		this.userDfPtr = userDfPtr;
	}

	public String getUserDfPtrNm() {
		return userDfPtrNm;
	}

	public void setUserDfPtrNm(String userDfPtrNm) {
		this.userDfPtrNm = userDfPtrNm;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WaqPrfPtrAnaVO [userDfPtr=").append(userDfPtr)
				.append(", userDfPtrNm=").append(userDfPtrNm).append("]");
		return builder.toString();
	}

   
}