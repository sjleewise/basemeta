package kr.wise.meta.intf.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import kr.wise.commons.rqstmst.service.RequestMstService;
import kr.wise.commons.rqstmst.service.WaaBizInfo;
import kr.wise.commons.rqstmst.service.WaqMstr;
import kr.wise.commons.rqstmst.service.WaqMstrMapper;
import kr.wise.commons.util.UtilString;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service("GroupWareSendService")
public class GroupWareSendServiceImpl implements GroupWareSendService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@ Inject
	private WaqMstrMapper mstmapper;
	
	@Inject
	private MessageSource message;
	
	@Inject
	private RequestMstService requestMstService;
	
	@Override
	public Packet makePacket(GroupWareSendVO vo){
		Packet packet = new Packet();
		
		String mode = message.getMessage("mode", null, Locale.getDefault());

		mode = mode.substring(0,1).toUpperCase();
//		System.out.println("모드 : " + mode);
			
		String sysCode = 		message.getMessage("wiseda.sys.code", null, Locale.getDefault());
		String interfaceId = 	message.getMessage("eai.alert.interface.id", null, Locale.getDefault());
		String domain = 		message.getMessage("wiseda.domain.url", null, Locale.getDefault());
		String gb		=		message.getMessage("wiseda.gb", null, Locale.getDefault());
		
		String curDate =		getCurDate();
		
		String guid =			sysCode + curDate + RandomStringUtils.randomNumeric(21);
		String msgKey =			"alert"+sysCode+"ZZ"+curDate+RandomStringUtils.randomNumeric(8);
		
		String sendName = "";
		String subject  = "";
		
		String Contents = vo.getContents();
		
		if (gb.equals("META"))
		{
				gb 		= "메타시스템";
				subject	= gb + " 결재요청";
		}
		else
		{
				gb 		= "데이터품질시스템";
				Contents = Contents.replace("메타시스템", "데이터품질시스템");
		}
		
		sendName= gb;
		subject	= gb + " 결재요청";
					
		// URL 세팅.
		WaqMstr reqmst = new WaqMstr();
		reqmst.setBizDcd(vo.getBizDcd());
		reqmst.setBizDtlCd(" ");
		
		WaaBizInfo bizInfo = requestMstService.getBizInfo(reqmst);
		
		String url = domain + bizInfo.getUrl() + "?rqstNo=" + vo.getRqstNo() + "&bizDcd=" + bizInfo.getBizDcd();
	//	System.out.println("URL : " + url);
		
		Item item1 = new Item();	item1.setName("WHL_TGR_LEN");			item1.setLength(8);		item1.setValue("11111111");		packet.addItem(item1);
		Item item2 = new Item();	item2.setName("HER_LEN");				item2.setLength(8);		item2.setValue("00001102");		packet.addItem(item2);
		Item item3 = new Item();	item3.setName("PRO_MDA_LEN");			item3.setLength(8);		item3.setValue("00000000");		packet.addItem(item3);
		Item item4 = new Item();	item4.setName("TGR_VRS_INF");			item4.setLength(3);		item4.setValue("1.0");			packet.addItem(item4);
		Item item5 = new Item();	item5.setName("MLAN_TC");				item5.setLength(2);		item5.setValue("KO");			packet.addItem(item5);
		Item item6 = new Item();	item6.setName("SYS_ENV_TC");			item6.setLength(1);		item6.setValue(mode);			packet.addItem(item6);
		Item item7 = new Item();	item7.setName("IP_ADDR");				item7.setLength(40);	item7.setValue("10.6.15.91");	packet.addItem(item7);
		Item item8 = new Item();	item8.setName("MAC_ADDR");				item8.setLength(12);	item8.setValue("98BE85370E8H");	packet.addItem(item8);
		Item item9 = new Item();	item9.setName("GUID");					item9.setLength(38);	item9.setValue(guid);			packet.addItem(item9);
		Item item10 = new Item();	item10.setName("GUID_PRG_SNO");			item10.setLength(4);	item10.setValue("0001");		packet.addItem(item10);
		Item item11 = new Item();	item11.setName("FST_GUID");				item11.setLength(38);	item11.setValue(guid);			packet.addItem(item11);
		Item item12 = new Item();	item12.setName("FWDI_SYS_C");			item12.setLength(3);	item12.setValue(sysCode);		packet.addItem(item12);
		Item item13 = new Item();	item13.setName("FST_FWDI_SYS_C");		item13.setLength(3);	item13.setValue(sysCode);		packet.addItem(item13);
		Item item14 = new Item();	item14.setName("SYS_CO_RSRV");			item14.setLength(12);	item14.setValue("");			packet.addItem(item14);
		Item item15 = new Item();	item15.setName("TR_ID");				item15.setLength(10);	item15.setValue("");			packet.addItem(item15);
		Item item16 = new Item();	item16.setName("RMS_SYS_C");			item16.setLength(3);	item16.setValue("GWE");			packet.addItem(item16);
		Item item17 = new Item();	item17.setName("SRE_ID");				item17.setLength(10);	item17.setValue("");			packet.addItem(item17);
		Item item18 = new Item();	item18.setName("LKG_SRE_ID");			item18.setLength(10);	item18.setValue("");			packet.addItem(item18);
		Item item19 = new Item();	item19.setName("SRE_CNTR_TC");			item19.setLength(1);	item19.setValue("0");			packet.addItem(item19);
		Item item20 = new Item();	item20.setName("REQ_RPD_TC");			item20.setLength(1);	item20.setValue("Q");			packet.addItem(item20);
		Item item21 = new Item();	item21.setName("DTLS_TP_TC");			item21.setLength(1);	item21.setValue("1");			packet.addItem(item21);
		Item item22 = new Item();	item22.setName("CHN_TP_C");				item22.setLength(2);	item22.setValue("00");			packet.addItem(item22);
		Item item23 = new Item();	item23.setName("MSG_CHN_C");			item23.setLength(2);	item23.setValue("");			packet.addItem(item23);
		Item item24 = new Item();	item24.setName("SYNC_PRC_TC");			item24.setLength(1);	item24.setValue("A");			packet.addItem(item24);
		Item item25 = new Item();	item25.setName("RLT_TC");				item25.setLength(1);	item25.setValue("");			packet.addItem(item25);
		Item item26 = new Item();	item26.setName("PAGE_ROW_COUNT");		item26.setLength(5);	item26.setValue("00000");		packet.addItem(item26);
		Item item27 = new Item();	item27.setName("NEXT_PAGE_YN");			item27.setLength(1);	item27.setValue("");			packet.addItem(item27);
		Item item28 = new Item();	item28.setName("REQ_PAGE_NO");			item28.setLength(5);	item28.setValue("00000");		packet.addItem(item28);
		Item item29 = new Item();	item29.setName("REQ_DTM");				item29.setLength(17);	item29.setValue(curDate);		packet.addItem(item29);
		Item item30 = new Item();	item30.setName("RPD_DTM");				item30.setLength(17);	item30.setValue("");			packet.addItem(item30);
		Item item31 = new Item();	item31.setName("TR_SLS_DT");			item31.setLength(8);	item31.setValue("");			packet.addItem(item31);
		Item item32 = new Item();	item32.setName("TR_SLS_DT_TC");			item32.setLength(1);	item32.setValue("0");			packet.addItem(item32);
		Item item33 = new Item();	item33.setName("SML_TC");				item33.setLength(1);	item33.setValue("0");			packet.addItem(item33);
		Item item34 = new Item();	item34.setName("SML_SLS_YMD");			item34.setLength(8);	item34.setValue("");			packet.addItem(item34);
		Item item35 = new Item();	item35.setName("MSK_CCC_YN");			item35.setLength(1);	item35.setValue("N");			packet.addItem(item35);
		Item item36 = new Item();	item36.setName("ATH_TES_ALY_YN");		item36.setLength(1);	item36.setValue("N");			packet.addItem(item36);
		Item item37 = new Item();	item37.setName("LQN_TR_YN");			item37.setLength(1);	item37.setValue("N");			packet.addItem(item37);
		Item item38 = new Item();	item38.setName("RE_TR_FLAG");			item38.setLength(1);	item38.setValue("");			packet.addItem(item38);
		Item item39 = new Item();	item39.setName("CSG_TC");				item39.setLength(2);	item39.setValue("01");			packet.addItem(item39);
		Item item40 = new Item();	item40.setName("FSC_DT_BSE_TC");		item40.setLength(2);	item40.setValue("10");			packet.addItem(item40);
		Item item41 = new Item();	item41.setName("BLGT_BBR_C");			item41.setLength(3);	item41.setValue("310");			packet.addItem(item41);	//확인
		Item item42 = new Item();	item42.setName("USID");					item42.setLength(14);	item42.setValue("");			packet.addItem(item42);
		Item item43 = new Item();	item43.setName("PSC_C");				item43.setLength(4);	item43.setValue("");			packet.addItem(item43);
		Item item44 = new Item();	item44.setName("DTS_C");				item44.setLength(4);	item44.setValue("");			packet.addItem(item44);
		Item item45 = new Item();	item45.setName("TMN_ITL_BBR_C");		item45.setLength(3);	item45.setValue("");			packet.addItem(item45);
		Item item46 = new Item();	item46.setName("TMN_NO");				item46.setLength(10);	item46.setValue("");			packet.addItem(item46);
		Item item47 = new Item();	item47.setName("AAP_BBR_C");			item47.setLength(3);	item47.setValue("310");			packet.addItem(item47);
		Item item48 = new Item();	item48.setName("NML_PRC_RMS_TR_ID");	item48.setLength(10);	item48.setValue("");			packet.addItem(item48);
		Item item49 = new Item();	item49.setName("FRM_ERR_RMS_TR_ID");	item49.setLength(10);	item49.setValue("");			packet.addItem(item49);
		Item item50 = new Item();	item50.setName("NRPD_RMS_TR_ID");		item50.setLength(10);	item50.setValue("");			packet.addItem(item50);
		Item item51 = new Item();	item51.setName("FOOE_IST_C");			item51.setLength(4);	item51.setValue("");			packet.addItem(item51);
		Item item52 = new Item();	item52.setName("FOOE_NEK_TC");			item52.setLength(4);	item52.setValue("");			packet.addItem(item52);
		Item item53 = new Item();	item53.setName("FOOE_HED_TP_TC");		item53.setLength(3);	item53.setValue("");			packet.addItem(item53);
		Item item54 = new Item();	item54.setName("FOOE_REQ_BYCL_C");		item54.setLength(8);	item54.setValue("");			packet.addItem(item54);
		Item item55 = new Item();	item55.setName("FOOE_REQ_TR_TC");		item55.setLength(20);	item55.setValue("");			packet.addItem(item55);
		Item item56 = new Item();	item56.setName("ONLD_CNTR_TP_TC");		item56.setLength(1);	item56.setValue("");			packet.addItem(item56);
		Item item57 = new Item();	item57.setName("ADN_FOOE_IST_CD");		item57.setLength(4);	item57.setValue("");			packet.addItem(item57);
		Item item58 = new Item();	item58.setName("IF_ID");				item58.setLength(12);	item58.setValue(interfaceId);	packet.addItem(item58);
		Item item59 = new Item();	item59.setName("EAI_FWDI_SVR_NO");		item59.setLength(2);	item59.setValue("00");			packet.addItem(item59);
		Item item60 = new Item();	item60.setName("MCI_FWDI_SVR_NO");		item60.setLength(2);	item60.setValue("00");			packet.addItem(item60);
		Item item61 = new Item();	item61.setName("MCI_SES_ID");			item61.setLength(10);	item61.setValue("");			packet.addItem(item61);
		Item item62 = new Item();	item62.setName("CC_ID");				item62.setLength(10);	item62.setValue("");			packet.addItem(item62);
		Item item63 = new Item();	item63.setName("CC_PRC_DT");			item63.setLength(8);	item63.setValue("");			packet.addItem(item63);
		Item item64 = new Item();	item64.setName("CC_PRC_TO");			item64.setLength(5);	item64.setValue("");			packet.addItem(item64);
		Item item65 = new Item();	item65.setName("CC_TD_SNO");			item65.setLength(9);	item65.setValue("");			packet.addItem(item65);
		Item item66 = new Item();	item66.setName("CC_PRC_TC");			item66.setLength(2);	item66.setValue("");			packet.addItem(item66);
		Item item67 = new Item();	item67.setName("CC_CALL_TR_ID");		item67.setLength(10);	item67.setValue("");			packet.addItem(item67);
		Item item68 = new Item();	item68.setName("RSD_CC_YN");			item68.setLength(1);	item68.setValue("");			packet.addItem(item68);
		Item item69 = new Item();	item69.setName("DTL_CC_ID");			item69.setLength(10);	item69.setValue("");			packet.addItem(item69);
		Item item70 = new Item();	item70.setName("CC_PRC_RLT_C");			item70.setLength(2);	item70.setValue("");			packet.addItem(item70);
		Item item71 = new Item();	item71.setName("CC_ACL_TFR_AMT");		item71.setLength(20);	item71.setValue("00000000000000000000");	packet.addItem(item71);
		Item item72 = new Item();	item72.setName("CC_TFR_TGT_AMT");		item72.setLength(20);	item72.setValue("00000000000000000000");	packet.addItem(item72);
		Item item73 = new Item();	item73.setName("CC_XTR_DETS_RE_ROM_YN");item73.setLength(1);	item73.setValue("");			packet.addItem(item73);
		Item item74 = new Item();	item74.setName("CST_NO");				item74.setLength(8);	item74.setValue("");			packet.addItem(item74);
		Item item75 = new Item();	item75.setName("TR_CO_RSRV");			item75.setLength(40);	item75.setValue("");			packet.addItem(item75);
		Item item76 = new Item();	item76.setName("BKB_TC");				item76.setLength(1);	item76.setValue("");			packet.addItem(item76);
		Item item77 = new Item();	item77.setName("BKB_CWT_NO");			item77.setLength(20);	item77.setValue("");			packet.addItem(item77);
		Item item78 = new Item();	item78.setName("CSF_CHN_RSRV");			item78.setLength(129);	item78.setValue("");			packet.addItem(item78);
		Item item79 = new Item();	item79.setName("NFF_CNC_MDA_KD_C");		item79.setLength(2);	item79.setValue("");			packet.addItem(item79);
		Item item80 = new Item();	item80.setName("CHN_TP_ADN_C");			item80.setLength(2);	item80.setValue("");			packet.addItem(item80);
		Item item81 = new Item();	item81.setName("NFF_CNC_MCN_ID");		item81.setLength(64);	item81.setValue("");			packet.addItem(item81);
		Item item82 = new Item();	item82.setName("NFF_ISO_NTN_SYM_C");	item82.setLength(2);	item82.setValue("");			packet.addItem(item82);
		Item item83 = new Item();	item83.setName("NFF_CIR_NO");			item83.setLength(5);	item83.setValue("");			packet.addItem(item83);
		Item item84 = new Item();	item84.setName("NFF_CHN_BZ_TC");		item84.setLength(4);	item84.setValue("");			packet.addItem(item84);
		Item item85 = new Item();	item85.setName("IB_SRE_MNU_ID");		item85.setLength(10);	item85.setValue("");			packet.addItem(item85);
		Item item86 = new Item();	item86.setName("TELB_SVC_C");			item86.setLength(3);	item86.setValue("");			packet.addItem(item86);
		Item item87 = new Item();	item87.setName("XP_LGN_TR_YN");			item87.setLength(1);	item87.setValue("");			packet.addItem(item87);
		Item item88 = new Item();	item88.setName("NFF_CST_TC");			item88.setLength(1);	item88.setValue("");			packet.addItem(item88);
		Item item89 = new Item();	item89.setName("USR_NO");				item89.setLength(8);	item89.setValue("");			packet.addItem(item89);
		Item item90 = new Item();	item90.setName("ADMR_ATH_YN");			item90.setLength(1);	item90.setValue("");			packet.addItem(item90);
		Item item91 = new Item();	item91.setName("SECT_MDA_TC");			item91.setLength(2);	item91.setValue("");			packet.addItem(item91);
		Item item92 = new Item();	item92.setName("CFA_KPN_MDA_TC");		item92.setLength(1);	item92.setValue("");			packet.addItem(item92);
		Item item93 = new Item();	item93.setName("MCT_TC");				item93.setLength(2);	item93.setValue("");			packet.addItem(item93);
		Item item94 = new Item();	item94.setName("USR_CER_MANR_C");		item94.setLength(1);	item94.setValue("");			packet.addItem(item94);
		Item item95 = new Item();	item95.setName("CSR_TPR_CNFM_FLAG");	item95.setLength(1);	item95.setValue("");			packet.addItem(item95);
		Item item96 = new Item();	item96.setName("CSR_TPR_CNFM_NO");		item96.setLength(38);	item96.setValue("");			packet.addItem(item96);
		Item item97 = new Item();	item97.setName("NFF_CHN_RSRV");			item97.setLength(102);	item97.setValue("");			packet.addItem(item97);
		Item item98 = new Item();	item98.setName("RSPR_APV_STS_TC");		item98.setLength(2);	item98.setValue("00");			packet.addItem(item98);
		Item item99 = new Item();	item99.setName("RSPR_APV_LEV_NBR");		item99.setLength(2);	item99.setValue("00");			packet.addItem(item99);
		Item item100 = new Item();	item100.setName("RSPR_TR_TC");			item100.setLength(2);	item100.setValue("00");			packet.addItem(item100);
		Item item101 = new Item();	item101.setName("RSPR_APV_MSG_CNT");	item101.setLength(2);	item101.setValue("00");			packet.addItem(item101);
		Item item102 = new Item();	item102.setName("RSPR_CNT");			item102.setLength(2);	item102.setValue("00");			packet.addItem(item102);
		Item item103 = new Item();	item103.setName("RSPR_APV_DKEY_CNT");	item103.setLength(2);	item103.setValue("00");			packet.addItem(item103);
		Item item104 = new Item();	item104.setName("APV_RSPR_CNT");		item104.setLength(2);	item104.setValue("00");			packet.addItem(item104);
		Item item105 = new Item();	item105.setName("APV_TR_SRE_DKEY");		item105.setLength(18);	item105.setValue("");			packet.addItem(item105);
		Item item106 = new Item();	item106.setName("RSPR_APV_RSRV");		item106.setLength(29);	item106.setValue("");			packet.addItem(item106);
		Item item107 = new Item();	item107.setName("IDFC_MNG_CNT");		item107.setLength(2);	item107.setValue("00");			packet.addItem(item107);
		Item item108 = new Item();	item108.setName("MSG_IDCT_TC");			item108.setLength(1);	item108.setValue("1");			packet.addItem(item108);
		Item item109 = new Item();	item109.setName("ERR_OCC_TGR_ITM");		item109.setLength(50);	item109.setValue("");			packet.addItem(item109);
		Item item110 = new Item();	item110.setName("MSG_CNT");				item110.setLength(3);	item110.setValue("000");		packet.addItem(item110);
		Item item111 = new Item();	item111.setName("ETC_PRO_DAT_CNT");		item111.setLength(2);	item111.setValue("00");			packet.addItem(item111);
		Item item112 = new Item();	item112.setName("PRO_MDA_CNT");			item112.setLength(3);	item112.setValue("000");		packet.addItem(item112);
		Item item113 = new Item();	item113.setName("MSG_KEY");				item113.setLength(32);	item113.setValue(msgKey);		packet.addItem(item113);
		Item item114 = new Item();	item114.setName("MSG_GUBUN");			item114.setLength(1);	item114.setValue(vo.getMsgGubun());	packet.addItem(item114);
		Item item115 = new Item();	item115.setName("SEND_ID");				item115.setLength(50);	item115.setValue(vo.getSendId());packet.addItem(item115);
		Item item116 = new Item();	item116.setName("SEND_NAME");			item116.setLength(100);	item116.setValue(vo.getSendName());		packet.addItem(item116);
		Item item117 = new Item();	item117.setName("DEST_GUBUN");			item117.setLength(1);	item117.setValue(vo.getDestGubun());packet.addItem(item117);
		Item item118 = new Item();	item118.setName("RECV_IDS");			item118.setLength(4000);item118.setValue(vo.getRecvIds());	packet.addItem(item118);
		Item item119 = new Item();	item119.setName("CC_RECV_IDS");			item119.setLength(500);	item119.setValue(UtilString.null2Blank(vo.getCcRecvIds()));		packet.addItem(item119);
		Item item120 = new Item();	item120.setName("BCC_RECV_IDS");		item120.setLength(500);	item120.setValue(UtilString.null2Blank(vo.getBccRecvIds()));	packet.addItem(item120);
		Item item121 = new Item();	item121.setName("SUBJECT");				item121.setLength(200);	item121.setValue(subject);	packet.addItem(item121);
		Item item122 = new Item();	item122.setName("CONTENTS");			item122.setLength(4000);item122.setValue(Contents);	packet.addItem(item122);
		Item item123 = new Item();	item123.setName("URL");					item123.setLength(500);	item123.setValue(url);				packet.addItem(item123);
		Item item124 = new Item();	item124.setName("ATT_FLAG");			item124.setLength(1);	item124.setValue(vo.getAttFlag());	packet.addItem(item124);
		Item item125 = new Item();	item125.setName("ATT");					item125.setLength(4000);item125.setValue(UtilString.null2Blank(vo.getAtt()));	packet.addItem(item125);
		Item item126 = new Item();	item126.setName("SYSTEM_CODE");			item126.setLength(3);	item126.setValue(sysCode);			packet.addItem(item126);
		Item item127 = new Item();	item127.setName("END_CODE");			item127.setLength(2);	item127.setValue("@@");				packet.addItem(item127);
		
		return packet;
	}

	public static String getCurDate()
    {
		String curDate = null;
		Calendar cal = Calendar.getInstance();
		cal.add(cal.DATE, 0);

        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf((cal.get(Calendar.MONTH) + 1));
        String day = String.valueOf(cal.get(Calendar.DATE));
        String hour 		= String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String min 			= String.valueOf(cal.get(Calendar.MINUTE));
        String second 		= String.valueOf(cal.get(Calendar.SECOND));
        
        if (Integer.parseInt(month) < 10) month = "0"
                + month;
        if (Integer.parseInt(day) < 10) day = "0" + day;
        
        if (Integer.parseInt(hour) < 10) 	hour = 		"0"		+ hour;
        if (Integer.parseInt(min) < 10) 	min = 		"0"		+ min;
        if (Integer.parseInt(second) < 10) 	second = 	"0" 	+ second;
        
        curDate = year+ month+ day+ hour+min+ second;

        return curDate;
    }
	
	
	@Override
	public void send(Packet packet) {
		
		byte[] reqData = null;;
		
		String param = packet.raw();
		
		String mode = message.getMessage("mode", null, Locale.getDefault());
		String urlStr = message.getMessage(mode+".eai.url", null, Locale.getDefault()); 
		
		// 전체전문길이 세팅.
		String totLen = "000"+param.getBytes().length;
		param = param.replaceFirst("11111111", totLen);
		
	//	System.out.println("표준전문 : " + param);
	
		reqData = param.getBytes();
		
		

		byte[] rtnData = null;
		HttpURLConnection conn = null;
		OutputStream 	os = null;
		InputStream 	is = null;
		ByteArrayOutputStream baos = null;
		
		try{
			// 요청 처리
		    URL url = new URL(urlStr);
		    conn = (HttpURLConnection)url.openConnection();
		    conn.setConnectTimeout(3 * 1000); // 연결 타임아웃 3 sec
		    conn.setReadTimeout(3 * 1000); // 거래 타임아웃
		    conn.addRequestProperty("Content-Type", "application/octet-stream");
		    conn.addRequestProperty("Content-Length", "" + reqData.length);
		    conn.setDoOutput(true);
		    conn.setDoInput(true);
		   // os = conn.getOutputStream();

		    os = new DataOutputStream(conn.getOutputStream());

		  //  System.out.println("HTTP REQ[" + urlStr + "][" + new String(rtnData) + "]");
		    
		    os.write(reqData);
		    
		    os.flush();
		  //  System.out.println("url : " + urlStr);
		   // System.out.println("HTTP Response [" + conn.getResponseCode() + "] " + conn.getResponseMessage());
		    logger.debug("HTTP Response [" + conn.getResponseCode() + "] " + conn.getResponseMessage());
    
		    // 응답 처리
		    is = conn.getInputStream();
		    int readLen = -1;
		    byte[] buffer = new byte[1024];
		    baos = new ByteArrayOutputStream();
		    do {
		        readLen = is.read(buffer);
		        if (readLen < 0) {
		            break;
		        }
		        baos.write(buffer, 0, readLen);
		    } while (readLen != -1);
		    rtnData = baos.toByteArray();
		  //  System.out.println("HTTP RES[" + urlStr + "][" + new String(rtnData) + "]");
		    
		    logger.debug("HTTP RES[" + urlStr + "][" + new String(rtnData) + "]");

		}catch(Exception e)
		{
			System.out.println(e.toString());
		}finally{
			if(baos != null) {
		        try { baos.close(); } catch(Exception ee) {}
		    }
		    if(os != null) {
		        try { os.close(); } catch(Exception ee) {}
		    }
		    if(is != null) {
		        try { is.close(); } catch(Exception ee) {}
		    }
		    if(conn != null) {
		        try { conn.disconnect(); } catch(Exception ee) {}
		    }

		}
	}

	@Override
	public void sendGroupWare(String rqstNo) {
	// TODO Auto-generated method stub
	//	logger.debug("sendGroupWare start");
		List<GroupWareSendVO> noticeList = mstmapper.selectEaiGwSend(rqstNo);
		
		Packet packet = null;
		
		if(noticeList != null)
		{
			for(GroupWareSendVO sendVo : noticeList)
			{
				//개발에서 테스트용
				System.out.println("/// 메신저 전송 " +rqstNo+ " ///");
				System.out.println("SEND_ID : " + sendVo.getSendId() + " / SEND_NM : " + sendVo.getSendName() + " / RECV_IDS : " + sendVo.getRecvIds());
//				System.out.println("CONTENTS : " + sendVo.getContents());
				
				String mode = message.getMessage("mode", null, Locale.getDefault());
				if(mode.equals("prod")){
					packet = makePacket(sendVo);
					send(packet);
					System.out.println("/// " + mode + " 메신저 전송 완료 ///");
				}else{
					System.out.println("/// " + mode + " 는(은) 메신저 전송 없음 ///");
				}
				
			}
		}
	//	logger.debug("sendGroupWare end");
		
	}
	
}
