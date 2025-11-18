package kr.wise.meta.stnd.service;

import kr.wise.commons.cmm.CommonVo;

public class WamSymnTrm extends CommonVo{

	 private String symnId;

	    private String symnLnm;

	    private String symnPnm;

	    private String symnDcd;

	    private String stwdId;

	    private String sbswdLnm;

	    private String sbswdPnm;


	    public String getSymnId() {
	        return symnId;
	    }

	    public void setSymnId(String symnId) {
	        this.symnId = symnId;
	    }

	    public String getSymnLnm() {
	        return symnLnm;
	    }

	    public void setSymnLnm(String symnLnm) {
	        this.symnLnm = symnLnm;
	    }

	    public String getSymnPnm() {
	        return symnPnm;
	    }

	    public void setSymnPnm(String symnPnm) {
	        this.symnPnm = symnPnm;
	    }

	    public String getSymnDcd() {
	        return symnDcd;
	    }

	    public void setSymnDcd(String symnDcd) {
	        this.symnDcd = symnDcd;
	    }

	    public String getStwdId() {
	        return stwdId;
	    }

	    public void setStwdId(String stwdId) {
	        this.stwdId = stwdId;
	    }

	    public String getSbswdLnm() {
	        return sbswdLnm;
	    }

	    public void setSbswdLnm(String sbswdLnm) {
	        this.sbswdLnm = sbswdLnm;
	    }

	    public String getSbswdPnm() {
	        return sbswdPnm;
	    }

	    public void setSbswdPnm(String sbswdPnm) {
	        this.sbswdPnm = sbswdPnm;
	    }

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("WamSymn [symnId=").append(symnId).append(", symnLnm=")
					.append(symnLnm).append(", symnPnm=").append(symnPnm)
					.append(", symnDcd=").append(symnDcd).append(", stwdId=")
					.append(stwdId).append(", sbswdLnm=").append(sbswdLnm)
					.append(", sbswdPnm=").append(sbswdPnm).append("]");
			return super.toString() + builder.toString();
		}
		
}
