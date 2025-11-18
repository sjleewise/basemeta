package kr.wise.commons.util;

public class TargetDbmsDM {

	private String dbms_id;
	private String dbSchId;
	private String dbms_enm;
	private String dbms_type_cd;
	private String dbms_vers_cd;
	private String db_user;
	private String db_pwd;
	private String owner_nm;
	private String dbc_owner_nm;
	private String jdbc_driver;
	private String connect_string;
	private String conn_trg_db_lnk_chrw;
	
	private String dbLinkNm;
	
	private String colPrfYn;
	private String dmnPdtYn;
	
	public String getDbms_id() {
		return dbms_id;
	}
	public void setDbms_id(String dbms_id) {
		this.dbms_id = dbms_id;
	}
	public String getDbSchId() {
		return dbSchId;
	}
	public void setDbSchId(String dbSchId) {
		this.dbSchId = dbSchId;
	}
	public String getDbms_enm() {
		return dbms_enm;
	}
	public void setDbms_enm(String dbms_enm) {
		this.dbms_enm = dbms_enm;
	}
	public String getDbms_type_cd() {
		return dbms_type_cd;
	}
	public void setDbms_type_cd(String dbms_type_cd) {
		this.dbms_type_cd = dbms_type_cd;
	}
	public String getDb_user() {
		return db_user;
	}
	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}
	public String getDb_pwd() {
		return db_pwd;
	}
	public void setDb_pwd(String db_pwd) {
		this.db_pwd = db_pwd;
	}
	public String getOwner_nm() {
		return owner_nm;
	}
	public void setOwner_nm(String owner_nm) {
		this.owner_nm = owner_nm;
	}
	public String getJdbc_driver() {
		return jdbc_driver;
	}
	public void setJdbc_driver(String jdbc_driver) {
		this.jdbc_driver = jdbc_driver;
	}
	public String getConnect_string() {
		return connect_string;
	}
	public void setConnect_string(String connect_string) {
		this.connect_string = connect_string;
	}
	public String getDbc_owner_nm() {
		return dbc_owner_nm;
	}
	public void setDbc_owner_nm(String dbc_owner_nm) {
		this.dbc_owner_nm = dbc_owner_nm;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TargetDbmsDM [dbms_id=").append(dbms_id)
				.append(", dbms_enm=").append(dbms_enm)
				.append(", dbms_type_cd=").append(dbms_type_cd)
				.append(", db_user=").append(db_user).append(", db_pwd=")
				.append(db_pwd).append(", owner_nm=").append(owner_nm)
				.append(", dbc_owner_nm=").append(dbc_owner_nm)
				.append(", jdbc_driver=").append(jdbc_driver)
				.append(", connect_string=").append(connect_string).append("]");
		return builder.toString();
	}
	/**
	 * @return the dbLinkNm
	 */
	public String getDbLinkNm() {
		return dbLinkNm;
	}
	/**
	 * @param dbLinkNm the dbLinkNm to set
	 */
	public void setDbLinkNm(String dbLinkNm) {
		this.dbLinkNm = dbLinkNm;
	}
	/**
	 * @return the colPrfYn
	 */
	public String getColPrfYn() {
		return colPrfYn;
	}
	/**
	 * @param colPrfYn the colPrfYn to set
	 */
	public void setColPrfYn(String colPrfYn) {
		this.colPrfYn = colPrfYn;
	}
	/**
	 * @return the dmnPdtYn
	 */
	public String getDmnPdtYn() {
		return dmnPdtYn;
	}
	/**
	 * @param dmnPdtYn the dmnPdtYn to set
	 */
	public void setDmnPdtYn(String dmnPdtYn) {
		this.dmnPdtYn = dmnPdtYn;
	}
	
	public String getDbms_vers_cd() {
		return dbms_vers_cd;
	}
	
	public void setDbms_vers_cd(String dbms_vers_cd) {
		this.dbms_vers_cd = dbms_vers_cd;
	}
	public String getConn_trg_db_lnk_chrw() {
		return conn_trg_db_lnk_chrw;
	}
	public void setConn_trg_db_lnk_chrw(String conn_trg_db_lnk_chrw) {
		this.conn_trg_db_lnk_chrw = conn_trg_db_lnk_chrw;
	}
	
	
	
}
