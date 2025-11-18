/**
 * 0. Project  : 범정부 메타데이터 플랫폼 구축 사업(1단계)
 *
 * 1. FileName : EsbConfig.java
 * 2. Package : kr.wise.esb
 * 3. Comment :
 * 4. 작성자  : eychoi
 * 5. 작성일  : 2018.10.22.
 * 6. 변경이력 :
 *                    이름     : 일자          : 근거자료   : 변경내용
 *                   ------------------------------------------------------
 *                    eychoi : 2018.10.22. :            : 신규 개발.
 */
package kr.wise.esb;


public class EsbConfig {
	
	public static final String MTA  = "M";
	public static final String TGT_ORG_CD = "0000000";
	public static final String TGT_SYS_CD = "HUBS";
	
	//public static final String SYS_CD = "M020"; //차후 전역으로 변경
	
	//public static final String MTA_FILE_SEND_PATH = "C:\\fsapp\\megaware\\m001\\file\\send\\mta\\";
	
	//public static final String MTA_FILE_SEND_PATH = "/fsapp/megaware/m001/file/send/mta";
	
	//public static final String MTA_FILE_RECV_PATH = "/fsapp/megaware/hubs/file/recv/mta";
	
	public static final String MTA_SEND = "send";
	
	public static final String MTA_RECV = "recv";
	
	///fsapp/megaware/m001/file/send	/fsapp/megaware/hub1/file/recv
	
	public static final String [] MTA_CSV_HEADER_INFOSYS = 
			{"orgCd", "orgNm", "infoSysCd", "infoSysNm", "relLaw", "constPurp", "constYy", "operDeptNm", "crgUserNm", "crgTelNo", "crgEmailAddr"};
	
	public static final String [] MTA_CSV_HEADER_DBCON = 
		     {"db_conn_trg_id", "db_conn_trg_pnm", "db_conn_trg_lnm", "org_cd", "info_sys_cd", "dbms_typ_cd", "dbms_vers_cd"
		    , "obj_vers", "reg_typ_cd","writ_user_id","writ_dtm"
			, "crgp_nm", "crgp_cntel", "obj_descn", "tbl_cnt", "const_dt", "data_cpct", "poss_obj", "os_ver_nm", "pdata_expt_rsn"
			};
	
	public static final String [] MTA_CSV_HEADER_TBL = {"mta_tbl_id", "mta_tbl_pnm", "mta_tbl_lnm", "org_cd", "info_sys_cd", "db_conn_trg_id", "db_conn_trg_pnm", "db_sch_id", "db_sch_pnm", "obj_descn"
			, "obj_vers", "reg_typ_cd", "frs_rqst_dtm", "frs_rqst_user_id", "rqst_no", "rqst_sno", "rqst_dtm", "rqst_user_id", "aprv_dtm", "aprv_user_id"
			, "tbl_typ_nm", "rel_enty_nm", "subj_nm", "tag_inf_nm", "prsv_term", "tbl_vol", "expt_occr_cnt", "tbl_cre_dt", "nopen_rsn"
			, "open_data_lst", "occr_cyl", "open_rsn_cd","nopen_dtl_rel_bss","dq_dgns_yn"};  
	
	public static final String [] MTA_CSV_HEADER_COL = {"mta_col_id","mta_col_pnm","mta_col_lnm","mta_tbl_id","col_rel_enty_nm","col_rel_attr_nm"
			,"col_ord","pk_yn","pk_ord","fk_yn","data_type","data_len","data_scal","data_fmt","nonul_yn"
			,"deflt_val","const_cnd","open_yn","prsn_info_yn","enc_trg_yn","pri_rsn","rqst_no","rqst_sno"
			,"rqst_dtl_sno","obj_descn","obj_vers","reg_typ_cd","frs_rqst_dtm","frs_rqst_user_id"
			,"rqst_dtm","rqst_user_id","aprv_dtm","aprv_user_id"};	

	public static final String[] MTA_CSV_HEADER_TOTAL = {"db_conn_trg_id","db_conn_trg_pnm","db_conn_trg_lnm","org_cd","info_sys_cd","dbms_typ_cd","dbms_vers_cd","obj_vers","reg_typ_cd","writ_user_id","writ_dtm","crgp_nm","crgp_cntel","db_conn_trg_obj_descn","tbl_cnt","const_dt","data_cpct","poss_obj","os_ver_nm","pdata_expt_rsn",
			"mta_tbl_id","mta_tbl_pnm","mta_tbl_lnm","org_cd","info_sys_cd","db_conn_trg_id","db_conn_trg_pnm","db_sch_id","db_sch_pnm","mta_tbl_obj_descn","obj_vers","reg_typ_cd","frs_rqst_dtm","frs_rqst_user_id","rqst_no","rqst_sno","rqst_dtm","rqst_user_id","aprv_dtm","aprv_user_id","tbl_typ_nm","rel_enty_nm","subj_nm","tag_inf_nm","prsv_term","tbl_vol","expt_occr_cnt","tbl_cre_dt","nopen_rsn","open_data_lst","occr_cyl","open_rsn_cd","nopen_dtl_rel_bss","dq_dgns_yn",
			"mta_col_id","mta_col_pnm","mta_col_lnm","mta_tbl_id","col_rel_enty_nm","col_rel_attr_nm","col_ord","pk_yn","pk_ord","fk_yn","data_type","data_len","data_scal","data_fmt","nonul_yn","deflt_val","const_cnd","open_yn","prsn_info_yn","enc_trg_yn","pri_rsn","rqst_no","rqst_sno","rqst_dtl_sno","mta_col_obj_descn","obj_vers","reg_typ_cd","frs_rqst_dtm","frs_rqst_user_id","rqst_dtm","rqst_user_id","aprv_dtm","aprv_user_id"
	};

	public static final String MTA_INFOSYS	= "infosys";
	public static final String MTA_DBCON	= "dbcon";
	public static final String MTA_TBL		= "tbl";
	public static final String MTA_COL		= "col";
	public static final String MTA_TOTAL	= "total";
	
	
}
