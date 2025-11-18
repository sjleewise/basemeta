package kr.wise.dq.profile.colptrana.service;

import kr.wise.dq.profile.mstr.service.WamPrfMstrVO;

public class WamPrfPtrAnaVO extends WamPrfMstrVO {

    private String userDfPtr;

    private String userDfPtrNm;

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
}