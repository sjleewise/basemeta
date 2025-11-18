package kr.wise.commons.cmm;


import java.io.Serializable;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.03.03    박지욱          최초 생성
 *
 *  @author 공통서비스 개발팀 박지욱
 *  @since 2009.03.03
 *  @version 1.0
 *  @see
 *
 */
public class LoginVO implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8274004534207618049L;

	/** 아이디 */
	private String id;
	/** 이름 */
	private String name;
	/** 주민등록번호 */
	private String ihidNum;
	/** 이메일주소 */
	private String email;
	/** 비밀번호 */
	private String password;
	/** 비밀번호 힌트 */
	private String passwordHint;
	/** 비밀번호 정답 */
	private String passwordCnsr;
	/** 사용자구분 */
	private String userSe;
	/** 조직(부서)ID */
	private String orgnztId;
	/** 조직(부서)명 */
	private String orgnztNm;
	/** 고유아이디 */
	private String uniqId;
	/** 로그인 후 이동할 페이지 */
	private String url;
	/** 사용자 IP정보 */
	private String ip;
	/** GPKI인증 DN */
	private String dn;
	/** 관리자 여부 */
	private String isAdminYn;

	private String usergId;
	
	private String adminId; //업무대행을 수행한 관리자 ID

	/** RSA 보안 로그인용 필드 */
	private String securedUsername;
	private String securedPassword;
	private String securedPasswordEnc;
	private String securedBfPassword;
	
	/** 비밀번호 만기 여부 */
	private String isPwdExpYn;
	
	/** 회사코드 */
	private String comCd;

	//=======범정부===============
	private String orgCd;
	
	private String orgLvlUseYn;

	private String topOrgCd;
	
	private String isSysAdminYn;
	
	private String orgNm;
	
	private String userHtelno;
	//========범정부 END==========
	
	//비밀번호 암호화 관련
	private int loginFailCount;
	private String isLock;
	private int lockCount;
	private String saltKey;
	private String passwordEnc;
	
	private String partNm;
	
	
	public String getPartNm() {
		return partNm;
	}
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	public String getPasswordEnc() {
		return passwordEnc;
	}
	public void setPasswordEnc(String passwordEnc) {
		this.passwordEnc = passwordEnc;
	}
	private String errType;
	////////////////////
	
	/**
	 * id attribute 를 리턴한다.
	 * @return String
	 */
	public String getId() {
		return id;
	}
	/**
	 * id attribute 값을 설정한다.
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * name attribute 를 리턴한다.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	/**
	 * name attribute 값을 설정한다.
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * ihidNum attribute 를 리턴한다.
	 * @return String
	 */
	public String getIhidNum() {
		return ihidNum;
	}
	/**
	 * ihidNum attribute 값을 설정한다.
	 * @param ihidNum String
	 */
	public void setIhidNum(String ihidNum) {
		this.ihidNum = ihidNum;
	}
	/**
	 * email attribute 를 리턴한다.
	 * @return String
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * email attribute 값을 설정한다.
	 * @param email String
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * password attribute 를 리턴한다.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * password attribute 값을 설정한다.
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * passwordHint attribute 를 리턴한다.
	 * @return String
	 */
	public String getPasswordHint() {
		return passwordHint;
	}
	/**
	 * passwordHint attribute 값을 설정한다.
	 * @param passwordHint String
	 */
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}
	/**
	 * passwordCnsr attribute 를 리턴한다.
	 * @return String
	 */
	public String getPasswordCnsr() {
		return passwordCnsr;
	}
	/**
	 * passwordCnsr attribute 값을 설정한다.
	 * @param passwordCnsr String
	 */
	public void setPasswordCnsr(String passwordCnsr) {
		this.passwordCnsr = passwordCnsr;
	}
	/**
	 * userSe attribute 를 리턴한다.
	 * @return String
	 */
	public String getUserSe() {
		return userSe;
	}
	/**
	 * userSe attribute 값을 설정한다.
	 * @param userSe String
	 */
	public void setUserSe(String userSe) {
		this.userSe = userSe;
	}
	/**
	 * orgnztId attribute 를 리턴한다.
	 * @return String
	 */
	public String getOrgnztId() {
		return orgnztId;
	}
	/**
	 * orgnztId attribute 값을 설정한다.
	 * @param orgnztId String
	 */
	public void setOrgnztId(String orgnztId) {
		this.orgnztId = orgnztId;
	}
	/**
	 * uniqId attribute 를 리턴한다.
	 * @return String
	 */
	public String getUniqId() {
		return uniqId;
	}
	/**
	 * uniqId attribute 값을 설정한다.
	 * @param uniqId String
	 */
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	/**
	 * url attribute 를 리턴한다.
	 * @return String
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * url attribute 값을 설정한다.
	 * @param url String
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * ip attribute 를 리턴한다.
	 * @return String
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * ip attribute 값을 설정한다.
	 * @param ip String
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * dn attribute 를 리턴한다.
	 * @return String
	 */
	public String getDn() {
		return dn;
	}
	/**
	 * dn attribute 값을 설정한다.
	 * @param dn String
	 */
	public void setDn(String dn) {
		this.dn = dn;
	}
	/**
	 * @return the orgnztNm
	 */
	public String getOrgnztNm() {
		return orgnztNm;
	}
	/**
	 * @param orgnztNm the orgnztNm to set
	 */
	public void setOrgnztNm(String orgnztNm) {
		this.orgnztNm = orgnztNm;
	}
	public String getIsAdminYn() {
		return isAdminYn;
	}
	public void setIsAdminYn(String isAdminYn) {
		this.isAdminYn = isAdminYn;
	}
	public String getSecuredUsername() {
		return securedUsername;
	}
	public void setSecuredUsername(String securedUsername) {
		this.securedUsername = securedUsername;
	}
	public String getSecuredPassword() {
		return securedPassword;
	}
	public void setSecuredPassword(String securedPassword) {
		this.securedPassword = securedPassword;
	}
	public String getIsPwdExpYn() {
		return isPwdExpYn;
	}
	public void setIsPwdExpYn(String isPwdExpYn) {
		this.isPwdExpYn = isPwdExpYn;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginVO [id=").append(id).append(", name=")
				.append(name).append(", ihidNum=").append(ihidNum)
				.append(", email=").append(email).append(", password=")
				.append(password).append(", passwordHint=")
				.append(passwordHint).append(", passwordCnsr=")
				.append(passwordCnsr).append(", userSe=").append(userSe)
				.append(", orgnztId=").append(orgnztId).append(", orgnztNm=")
				.append(orgnztNm).append(", uniqId=").append(uniqId)
				.append(", url=").append(url).append(", ip=").append(ip)
				.append(", dn=").append(dn).append(", isAdminYn=")
				.append(isAdminYn).append(", securedUsername=")
				.append(securedUsername).append(", securedPassword=")
				.append(securedPassword).append("]");
		return builder.toString();
	}
	/**
	 * @return the usergId
	 */
	public String getUsergId() {
		return usergId;
	}
	/**
	 * @param usergId the usergId to set
	 */
	public void setUsergId(String usergId) {
		this.usergId = usergId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getOrgCd() {
		return orgCd;
	}
	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd; 
	}
	
	public String getOrgLvlUseYn() {
		return orgLvlUseYn;
	}
	public void setOrgLvlUseYn(String orgLvlUseYn) {
		this.orgLvlUseYn = orgLvlUseYn;
	}
	
	public String getTopOrgCd() {
		return topOrgCd;
	}
	public void setTopOrgCd(String topOrgCd) {
		this.topOrgCd = topOrgCd;
	}
	
	public String getIsSysAdminYn() {
		return isSysAdminYn;
	}
	public void setIsSysAdminYn(String isSysAdminYn) {
		this.isSysAdminYn = isSysAdminYn;
	}
	public int getLoginFailCount() {
		return loginFailCount;
	}
	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public int getLockCount() {
		return lockCount;
	}
	public void setLockCount(int lockCount) {
		this.lockCount = lockCount;
	}
	public String getErrType() {
		return errType;
	}
	public void setErrType(String errType) {
		this.errType = errType;
	}
	public String getSecuredBfPassword() {
		return securedBfPassword;
	}
	public void setSecuredBfPassword(String securedBfPassword) {
		this.securedBfPassword = securedBfPassword;
	}
	public String getSaltKey() {
		return saltKey;
	}
	public void setSaltKey(String saltKey) {
		this.saltKey = saltKey;
	}
	public String getSecuredPasswordEnc() {
		return securedPasswordEnc;
	}
	public void setSecuredPasswordEnc(String securedPasswordEnc) {
		this.securedPasswordEnc = securedPasswordEnc;
	}
	public String getComCd() {
		return comCd;
	}
	public void setComCd(String comCd) {
		this.comCd = comCd;
	}
	public String getOrgNm() {
		return orgNm;
	}
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	public String getUserHtelno() {
		return userHtelno;
	}
	public void setUserHtelno(String userHtelno) {
		this.userHtelno = userHtelno;
	}
	
	

}
