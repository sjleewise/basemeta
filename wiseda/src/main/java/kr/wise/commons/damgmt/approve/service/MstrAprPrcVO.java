package kr.wise.commons.damgmt.approve.service;

public class MstrAprPrcVO {
	
//	select A.RQST_NO, A.RQST_NM, A.RQST_STEP_CD, A.WRIT_USER_ID, B.APRV_DTM , B.APR_LVL, B.USER_ID, B.RVW_STS_CD
//	from waq_mstr A,waa_apr_prc B
//	where A.RQST_NO = B.RQST_NO
//	and RQST_STEP_CD='Q'
//	and APR_LVL='1'
//	and B.APRV_DTM IS NULL
//	and RQST_NO=#{RQST_NO}
//	and WRIT_USER_ID=#{WRIT_USER_ID};
	
	private String rqst_no;
	private String rqst_nm;
	private String rqst_step_cd;
	private String writ_user_id;
	private String aprv_dtm;
	private int apr_lvl;
	private String user_id;
	private int rvw_sts_cd;
	private String biz_dcd;
	
	
	
	public String getBiz_dcd() {
		return biz_dcd;
	}
	public void setBiz_dcd(String biz_dcd) {
		this.biz_dcd = biz_dcd;
	}
	public String getRqst_no() {
		return rqst_no;
	}
	public void setRqst_no(String rqst_no) {
		this.rqst_no = rqst_no;
	}
	public String getRqst_nm() {
		return rqst_nm;
	}
	public void setRqst_nm(String rqst_nm) {
		this.rqst_nm = rqst_nm;
	}
	public String getRqst_step_cd() {
		return rqst_step_cd;
	}
	public void setRqst_step_cd(String rqst_step_cd) {
		this.rqst_step_cd = rqst_step_cd;
	}
	public String getWrit_user_id() {
		return writ_user_id;
	}
	public void setWrit_user_id(String writ_user_id) {
		this.writ_user_id = writ_user_id;
	}
	public String getAprv_dtm() {
		return aprv_dtm;
	}
	public void setAprv_dtm(String aprv_dtm) {
		this.aprv_dtm = aprv_dtm;
	}
	public int getApr_lvl() {
		return apr_lvl;
	}
	public void setApr_lvl(int apr_lvl) {
		this.apr_lvl = apr_lvl;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getRvw_sts_cd() {
		return rvw_sts_cd;
	}
	public void setRvw_sts_cd(int rvw_sts_cd) {
		this.rvw_sts_cd = rvw_sts_cd;
	}
	@Override
	public String toString() {
		return "MstrAprPrcVO [rqst_no=" + rqst_no + ", rqst_nm=" + rqst_nm
				+ ", rqst_step_cd=" + rqst_step_cd + ", writ_user_id="
				+ writ_user_id + ", aprv_dtm=" + aprv_dtm + ", apr_lvl="
				+ apr_lvl + ", user_id=" + user_id + ", rvw_sts_cd="
				+ rvw_sts_cd + "]";
	}
	
	
	 

}
