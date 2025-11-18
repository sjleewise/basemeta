package kr.wise.dq.result.service;

import java.math.BigDecimal;

import kr.wise.commons.cmm.CommonVo;


/**
 * <PRE>
 * 1. ClassName : 
 * 2. FileName  : ProgChartVO.java
 * 3. Package  : kr.wise.dq.report.profile.service
 * 4. Comment  : 
 * 5. 작성자   : meta
 * 6. 작성일   : 2014. 6. 18. 오후 3:27:30
 * </PRE>
 */ 
public class ResultDataVO extends CommonVo{
	private String dqiLnm;	
	private String anaCnt;
	private String errCnt;
	private Float errRate;
	private String dbcTblNm; 
	private String dbcTblKorNm;
	private String dbcColNm;
	private String dbcColKorNm;
	private String prfKndCd;
	private String prfNm;
	private String prfId;
	
    private String rng;
    private String dtm;
    private String rel;
    private String ptr;
    private String efva;
    private String unq;
    
    private String errSql;
    
    private int colCnt;
    
    private String colNm1;
	private String colNm2;
	private String colNm3;
	private String colNm4;
	private String colNm5;
	private String colNm6;
	private String colNm7;
	private String colNm8;
	private String colNm9;
	private String colNm10;
	private String colNm11;
	private String colNm12;
	private String colNm13;
	private String colNm14;
	private String colNm15;
	private String colNm16;
	private String colNm17;
	private String colNm18;
	private String colNm19;
	private String colNm20;
	private String colNm21;
	private String colNm22;
	private String colNm23;
	private String colNm24;
	private String colNm25;
	private String colNm26;
	private String colNm27;
	private String colNm28;
	private String colNm29;
	private String colNm30;
	private String colNm31;
	private String colNm32;
	private String colNm33;
	private String colNm34;
	private String colNm35;
	private String colNm36;
	private String colNm37;
	private String colNm38;
	private String colNm39;
	private String colNm40;
	private String colNm41;
	private String colNm42;
	private String colNm43;
	private String colNm44;
	private String colNm45;
	private String colNm46;
	private String colNm47;
	private String colNm48;
	private String colNm49;
	private String colNm50;
	private String colNm51;
	private String colNm52;
	private String colNm53;
	private String colNm54;
	private String colNm55;
	private String colNm56;
	private String colNm57;
	private String colNm58;
	private String colNm59;
	private String colNm60;
	private String colNm61;
	private String colNm62;
	private String colNm63;
	private String colNm64;
	private String colNm65;
	private String colNm66;
	private String colNm67;
	private String colNm68;
	private String colNm69;
	private String colNm70;
	private String colNm71;
	private String colNm72;
	private String colNm73;
	private String colNm74;
	private String colNm75;
	private String colNm76;
	private String colNm77;
	private String colNm78;
	private String colNm79;
	private String colNm80;
	private String colNm81;
	private String colNm82;
	private String colNm83;
	private String colNm84;
	private String colNm85;
	private String colNm86;
	private String colNm87;
	private String colNm88;
	private String colNm89;
	private String colNm90;
	private String colNm91;
	private String colNm92;
	private String colNm93;
	private String colNm94;
	private String colNm95;
	private String colNm96;
	private String colNm97;
	private String colNm98;
	private String colNm99;
	private String colNm100;
	
	public String getRng() {
		return rng;
	}
	public void setRng(String rng) {
		this.rng = rng;
	}
	public String getDtm() {
		return dtm;
	}
	public void setDtm(String dtm) {
		this.dtm = dtm;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getPtr() {
		return ptr;
	}
	public void setPtr(String ptr) {
		this.ptr = ptr;
	}
	public String getEfva() {
		return efva;
	}
	public void setEfva(String efva) {
		this.efva = efva;
	}
	public String getUnq() {
		return unq;
	}
	public void setUnq(String unq) {
		this.unq = unq;
	}
	public String getColNm1() {
		return colNm1;
	}
	public void setColNm1(String colNm1) {
		this.colNm1 = colNm1;
	}
	public String getColNm2() {
		return colNm2;
	}
	public void setColNm2(String colNm2) {
		this.colNm2 = colNm2;
	}
	public String getColNm3() {
		return colNm3;
	}
	public void setColNm3(String colNm3) {
		this.colNm3 = colNm3;
	}
	public String getColNm4() {
		return colNm4;
	}
	public void setColNm4(String colNm4) {
		this.colNm4 = colNm4;
	}
	public String getColNm5() {
		return colNm5;
	}
	public void setColNm5(String colNm5) {
		this.colNm5 = colNm5;
	}
	public String getColNm6() {
		return colNm6;
	}
	public void setColNm6(String colNm6) {
		this.colNm6 = colNm6;
	}
	public String getColNm7() {
		return colNm7;
	}
	public void setColNm7(String colNm7) {
		this.colNm7 = colNm7;
	}
	public String getColNm8() {
		return colNm8;
	}
	public void setColNm8(String colNm8) {
		this.colNm8 = colNm8;
	}
	public String getColNm9() {
		return colNm9;
	}
	public void setColNm9(String colNm9) {
		this.colNm9 = colNm9;
	}
	public String getColNm10() {
		return colNm10;
	}
	public void setColNm10(String colNm10) {
		this.colNm10 = colNm10;
	}
	public String getColNm11() {
		return colNm11;
	}
	public void setColNm11(String colNm11) {
		this.colNm11 = colNm11;
	}
	public String getColNm12() {
		return colNm12;
	}
	public void setColNm12(String colNm12) {
		this.colNm12 = colNm12;
	}
	public String getColNm13() {
		return colNm13;
	}
	public void setColNm13(String colNm13) {
		this.colNm13 = colNm13;
	}
	public String getColNm14() {
		return colNm14;
	}
	public void setColNm14(String colNm14) {
		this.colNm14 = colNm14;
	}
	public String getColNm15() {
		return colNm15;
	}
	public void setColNm15(String colNm15) {
		this.colNm15 = colNm15;
	}
	public String getColNm16() {
		return colNm16;
	}
	public void setColNm16(String colNm16) {
		this.colNm16 = colNm16;
	}
	public String getColNm17() {
		return colNm17;
	}
	public void setColNm17(String colNm17) {
		this.colNm17 = colNm17;
	}
	public String getColNm18() {
		return colNm18;
	}
	public void setColNm18(String colNm18) {
		this.colNm18 = colNm18;
	}
	public String getColNm19() {
		return colNm19;
	}
	public void setColNm19(String colNm19) {
		this.colNm19 = colNm19;
	}
	public String getColNm20() {
		return colNm20;
	}
	public void setColNm20(String colNm20) {
		this.colNm20 = colNm20;
	}
	public String getColNm21() {
		return colNm21;
	}
	public void setColNm21(String colNm21) {
		this.colNm21 = colNm21;
	}
	public String getColNm22() {
		return colNm22;
	}
	public void setColNm22(String colNm22) {
		this.colNm22 = colNm22;
	}
	public String getColNm23() {
		return colNm23;
	}
	public void setColNm23(String colNm23) {
		this.colNm23 = colNm23;
	}
	public String getColNm24() {
		return colNm24;
	}
	public void setColNm24(String colNm24) {
		this.colNm24 = colNm24;
	}
	public String getColNm25() {
		return colNm25;
	}
	public void setColNm25(String colNm25) {
		this.colNm25 = colNm25;
	}
	public String getColNm26() {
		return colNm26;
	}
	public void setColNm26(String colNm26) {
		this.colNm26 = colNm26;
	}
	public String getColNm27() {
		return colNm27;
	}
	public void setColNm27(String colNm27) {
		this.colNm27 = colNm27;
	}
	public String getColNm28() {
		return colNm28;
	}
	public void setColNm28(String colNm28) {
		this.colNm28 = colNm28;
	}
	public String getColNm29() {
		return colNm29;
	}
	public void setColNm29(String colNm29) {
		this.colNm29 = colNm29;
	}
	public String getColNm30() {
		return colNm30;
	}
	public void setColNm30(String colNm30) {
		this.colNm30 = colNm30;
	}
	public String getColNm31() {
		return colNm31;
	}
	public void setColNm31(String colNm31) {
		this.colNm31 = colNm31;
	}
	public String getColNm32() {
		return colNm32;
	}
	public void setColNm32(String colNm32) {
		this.colNm32 = colNm32;
	}
	public String getColNm33() {
		return colNm33;
	}
	public void setColNm33(String colNm33) {
		this.colNm33 = colNm33;
	}
	public String getColNm34() {
		return colNm34;
	}
	public void setColNm34(String colNm34) {
		this.colNm34 = colNm34;
	}
	public String getColNm35() {
		return colNm35;
	}
	public void setColNm35(String colNm35) {
		this.colNm35 = colNm35;
	}
	public String getColNm36() {
		return colNm36;
	}
	public void setColNm36(String colNm36) {
		this.colNm36 = colNm36;
	}
	public String getColNm37() {
		return colNm37;
	}
	public void setColNm37(String colNm37) {
		this.colNm37 = colNm37;
	}
	public String getColNm38() {
		return colNm38;
	}
	public void setColNm38(String colNm38) {
		this.colNm38 = colNm38;
	}
	public String getColNm39() {
		return colNm39;
	}
	public void setColNm39(String colNm39) {
		this.colNm39 = colNm39;
	}
	public String getColNm40() {
		return colNm40;
	}
	public void setColNm40(String colNm40) {
		this.colNm40 = colNm40;
	}
	public String getColNm41() {
		return colNm41;
	}
	public void setColNm41(String colNm41) {
		this.colNm41 = colNm41;
	}
	public String getColNm42() {
		return colNm42;
	}
	public void setColNm42(String colNm42) {
		this.colNm42 = colNm42;
	}
	public String getColNm43() {
		return colNm43;
	}
	public void setColNm43(String colNm43) {
		this.colNm43 = colNm43;
	}
	public String getColNm44() {
		return colNm44;
	}
	public void setColNm44(String colNm44) {
		this.colNm44 = colNm44;
	}
	public String getColNm45() {
		return colNm45;
	}
	public void setColNm45(String colNm45) {
		this.colNm45 = colNm45;
	}
	public String getColNm46() {
		return colNm46;
	}
	public void setColNm46(String colNm46) {
		this.colNm46 = colNm46;
	}
	public String getColNm47() {
		return colNm47;
	}
	public void setColNm47(String colNm47) {
		this.colNm47 = colNm47;
	}
	public String getColNm48() {
		return colNm48;
	}
	public void setColNm48(String colNm48) {
		this.colNm48 = colNm48;
	}
	public String getColNm49() {
		return colNm49;
	}
	public void setColNm49(String colNm49) {
		this.colNm49 = colNm49;
	}
	public String getColNm50() {
		return colNm50;
	}
	public void setColNm50(String colNm50) {
		this.colNm50 = colNm50;
	}
	public String getColNm51() {
		return colNm51;
	}
	public void setColNm51(String colNm51) {
		this.colNm51 = colNm51;
	}
	public String getColNm52() {
		return colNm52;
	}
	public void setColNm52(String colNm52) {
		this.colNm52 = colNm52;
	}
	public String getColNm53() {
		return colNm53;
	}
	public void setColNm53(String colNm53) {
		this.colNm53 = colNm53;
	}
	public String getColNm54() {
		return colNm54;
	}
	public void setColNm54(String colNm54) {
		this.colNm54 = colNm54;
	}
	public String getColNm55() {
		return colNm55;
	}
	public void setColNm55(String colNm55) {
		this.colNm55 = colNm55;
	}
	public String getColNm56() {
		return colNm56;
	}
	public void setColNm56(String colNm56) {
		this.colNm56 = colNm56;
	}
	public String getColNm57() {
		return colNm57;
	}
	public void setColNm57(String colNm57) {
		this.colNm57 = colNm57;
	}
	public String getColNm58() {
		return colNm58;
	}
	public void setColNm58(String colNm58) {
		this.colNm58 = colNm58;
	}
	public String getColNm59() {
		return colNm59;
	}
	public void setColNm59(String colNm59) {
		this.colNm59 = colNm59;
	}
	public String getColNm60() {
		return colNm60;
	}
	public void setColNm60(String colNm60) {
		this.colNm60 = colNm60;
	}
	public String getColNm61() {
		return colNm61;
	}
	public void setColNm61(String colNm61) {
		this.colNm61 = colNm61;
	}
	public String getColNm62() {
		return colNm62;
	}
	public void setColNm62(String colNm62) {
		this.colNm62 = colNm62;
	}
	public String getColNm63() {
		return colNm63;
	}
	public void setColNm63(String colNm63) {
		this.colNm63 = colNm63;
	}
	public String getColNm64() {
		return colNm64;
	}
	public void setColNm64(String colNm64) {
		this.colNm64 = colNm64;
	}
	public String getColNm65() {
		return colNm65;
	}
	public void setColNm65(String colNm65) {
		this.colNm65 = colNm65;
	}
	public String getColNm66() {
		return colNm66;
	}
	public void setColNm66(String colNm66) {
		this.colNm66 = colNm66;
	}
	public String getColNm67() {
		return colNm67;
	}
	public void setColNm67(String colNm67) {
		this.colNm67 = colNm67;
	}
	public String getColNm68() {
		return colNm68;
	}
	public void setColNm68(String colNm68) {
		this.colNm68 = colNm68;
	}
	public String getColNm69() {
		return colNm69;
	}
	public void setColNm69(String colNm69) {
		this.colNm69 = colNm69;
	}
	public String getColNm70() {
		return colNm70;
	}
	public void setColNm70(String colNm70) {
		this.colNm70 = colNm70;
	}
	public String getColNm71() {
		return colNm71;
	}
	public void setColNm71(String colNm71) {
		this.colNm71 = colNm71;
	}
	public String getColNm72() {
		return colNm72;
	}
	public void setColNm72(String colNm72) {
		this.colNm72 = colNm72;
	}
	public String getColNm73() {
		return colNm73;
	}
	public void setColNm73(String colNm73) {
		this.colNm73 = colNm73;
	}
	public String getColNm74() {
		return colNm74;
	}
	public void setColNm74(String colNm74) {
		this.colNm74 = colNm74;
	}
	public String getColNm75() {
		return colNm75;
	}
	public void setColNm75(String colNm75) {
		this.colNm75 = colNm75;
	}
	public String getColNm76() {
		return colNm76;
	}
	public void setColNm76(String colNm76) {
		this.colNm76 = colNm76;
	}
	public String getColNm77() {
		return colNm77;
	}
	public void setColNm77(String colNm77) {
		this.colNm77 = colNm77;
	}
	public String getColNm78() {
		return colNm78;
	}
	public void setColNm78(String colNm78) {
		this.colNm78 = colNm78;
	}
	public String getColNm79() {
		return colNm79;
	}
	public void setColNm79(String colNm79) {
		this.colNm79 = colNm79;
	}
	public String getColNm80() {
		return colNm80;
	}
	public void setColNm80(String colNm80) {
		this.colNm80 = colNm80;
	}
	public String getColNm81() {
		return colNm81;
	}
	public void setColNm81(String colNm81) {
		this.colNm81 = colNm81;
	}
	public String getColNm82() {
		return colNm82;
	}
	public void setColNm82(String colNm82) {
		this.colNm82 = colNm82;
	}
	public String getColNm83() {
		return colNm83;
	}
	public void setColNm83(String colNm83) {
		this.colNm83 = colNm83;
	}
	public String getColNm84() {
		return colNm84;
	}
	public void setColNm84(String colNm84) {
		this.colNm84 = colNm84;
	}
	public String getColNm85() {
		return colNm85;
	}
	public void setColNm85(String colNm85) {
		this.colNm85 = colNm85;
	}
	public String getColNm86() {
		return colNm86;
	}
	public void setColNm86(String colNm86) {
		this.colNm86 = colNm86;
	}
	public String getColNm87() {
		return colNm87;
	}
	public void setColNm87(String colNm87) {
		this.colNm87 = colNm87;
	}
	public String getColNm88() {
		return colNm88;
	}
	public void setColNm88(String colNm88) {
		this.colNm88 = colNm88;
	}
	public String getColNm89() {
		return colNm89;
	}
	public void setColNm89(String colNm89) {
		this.colNm89 = colNm89;
	}
	public String getColNm90() {
		return colNm90;
	}
	public void setColNm90(String colNm90) {
		this.colNm90 = colNm90;
	}
	public String getColNm91() {
		return colNm91;
	}
	public void setColNm91(String colNm91) {
		this.colNm91 = colNm91;
	}
	public String getColNm92() {
		return colNm92;
	}
	public void setColNm92(String colNm92) {
		this.colNm92 = colNm92;
	}
	public String getColNm93() {
		return colNm93;
	}
	public void setColNm93(String colNm93) {
		this.colNm93 = colNm93;
	}
	public String getColNm94() {
		return colNm94;
	}
	public void setColNm94(String colNm94) {
		this.colNm94 = colNm94;
	}
	public String getColNm95() {
		return colNm95;
	}
	public void setColNm95(String colNm95) {
		this.colNm95 = colNm95;
	}
	public String getColNm96() {
		return colNm96;
	}
	public void setColNm96(String colNm96) {
		this.colNm96 = colNm96;
	}
	public String getColNm97() {
		return colNm97;
	}
	public void setColNm97(String colNm97) {
		this.colNm97 = colNm97;
	}
	public String getColNm98() {
		return colNm98;
	}
	public void setColNm98(String colNm98) {
		this.colNm98 = colNm98;
	}
	public String getColNm99() {
		return colNm99;
	}
	public void setColNm99(String colNm99) {
		this.colNm99 = colNm99;
	}
	public String getColNm100() {
		return colNm100;
	}
	public void setColNm100(String colNm100) {
		this.colNm100 = colNm100;
	}
	public String getDqiLnm() {
		return dqiLnm;
	}
	public void setDqiLnm(String dqiLnm) {
		this.dqiLnm = dqiLnm;
	}
	public String getAnaCnt() {
		return anaCnt;
	}
	public void setAnaCnt(String anaCnt) {
		this.anaCnt = anaCnt;
	}
	public String getErrCnt() {
		return errCnt;
	}
	public void setErrCnt(String errCnt) {
		this.errCnt = errCnt;
	}
	public Float getErrRate() {
		return errRate;
	}
	public void setErrRate(Float errRate) {
		this.errRate = errRate;
	}
	public String getDbcTblNm() {
		return dbcTblNm;
	}
	public void setDbcTblNm(String dbcTblNm) {
		this.dbcTblNm = dbcTblNm;
	}
	public String getDbcTblKorNm() {
		return dbcTblKorNm;
	}
	public void setDbcTblKorNm(String dbcTblKorNm) {
		this.dbcTblKorNm = dbcTblKorNm;
	}
	public String getDbcColNm() {
		return dbcColNm;
	}
	public void setDbcColNm(String dbcColNm) {
		this.dbcColNm = dbcColNm;
	}
	public String getDbcColKorNm() {
		return dbcColKorNm;
	}
	public void setDbcColKorNm(String dbcColKorNm) {
		this.dbcColKorNm = dbcColKorNm;
	}
	public String getPrfKndCd() {
		return prfKndCd;
	}
	public void setPrfKndCd(String prfKndCd) {
		this.prfKndCd = prfKndCd;
	}
	public String getPrfNm() {
		return prfNm;
	}
	public void setPrfNm(String prfNm) {
		this.prfNm = prfNm;
	}
	public String getPrfId() {
		return prfId;
	}
	public void setPrfId(String prfId) {
		this.prfId = prfId;
	}
	public String getErrSql() {
		return errSql;
	}
	public void setErrSql(String errSql) {
		this.errSql = errSql;
	}
	public int getColCnt() {
		return colCnt;
	}
	public void setColCnt(int colCnt) {
		this.colCnt = colCnt;
	}
}