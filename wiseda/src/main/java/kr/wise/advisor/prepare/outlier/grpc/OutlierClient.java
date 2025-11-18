/*
 * Copyright 2015, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package kr.wise.advisor.prepare.outlier.grpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kr.wise.admin.ai.algorithm.service.WaaAlg;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.summary.grpc.TargetDbms;
import kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

public class OutlierClient {
//  private static final Logger logger = Logger.getLogger(DmnPredictClient.class.getName());
  private final  Logger logger = LoggerFactory.getLogger(getClass());

  private final ManagedChannel channel;
  private final OdServiceGrpc.OdServiceBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public OutlierClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  /** Construct client for accessing RouteGuide server using the existing channel. */
  OutlierClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = OdServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }
  
	/** @param dbvo
	/** @return insomnia */
	private TargetDbms.Builder getDbmsBuild(WaaDbConnTrgVO dbvo) {
		TargetDbms.Builder tgtdbms = TargetDbms.newBuilder();
		tgtdbms.setDbmsTypCd(dbvo.getDbmsTypCd());
//	    tgtdbms.setConnTrgLnkUrl(dbvo.getConnTrgLnkUrl());
		tgtdbms.setConnTrgLnkUrl("FDS");
		tgtdbms.setDbConnAcId(dbvo.getDbConnAcId());
		tgtdbms.setDbConnAcPwd(dbvo.getDbConnAcPwd());
		tgtdbms.setDbSchPnm(dbvo.getDbSchPnm());
		return tgtdbms;
	}


/** grpc client test용 db접속정보 생성 */
private TargetDbms.Builder getTestTgtDbms() {
	TargetDbms.Builder tgtdbms = TargetDbms.newBuilder();
    tgtdbms.setDbmsTypCd("ORA");
    tgtdbms.setConnTrgLnkUrl("FDS");
    tgtdbms.setDbConnAcId("wiseda");
    tgtdbms.setDbConnAcPwd("wise1012");
    tgtdbms.setDbSchPnm("WISEDA");
	return tgtdbms;
}

  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
	  OutlierClient client = new OutlierClient("localhost", 50051);
    try {
      /* Access a service running on the local machine on port 50051 */
      String tblname = "wad_ana_var";
      if (args.length > 0) {
    	  tblname = args[0]; /* Use the arg as the name to greet if provided */
      }
	  	client.testlof   ();
	  	client.testocsvm ();
	  	client.testee    ();
	  	client.testisofor(); 
    } finally {
      client.shutdown();
    }
  }

	/**  insomnia */
	private void testlof() {
		logger.debug("outlier client test:LOF");
		
		OdRequest request = getOdrequestTest();
		
		OdResponse response = blockingStub.lof(request);
		
		resPrint(response);
		
	}
	/**  insomnia */
	private void testocsvm() {
		logger.debug("outlier client test:LOF");
		
		OdRequest request = getOdrequestTest();
		
		OdResponse response = blockingStub.ocsvm(request);
		
		resPrint(response);
		
	}
	/**  insomnia */
	private void testee() {
		logger.debug("outlier client test:LOF");
		
		OdRequest request = getOdrequestTest();
		
		OdResponse response = blockingStub.ee(request);
		
		resPrint(response);
		
	}
	/**  insomnia */
	private void testisofor() {
		logger.debug("outlier client test:LOF");
		
		OdRequest request = getOdrequestTest();
		
		OdResponse response = blockingStub.isofor(request);
		
		resPrint(response);
		
	}

	/** @param response insomnia */
	private void resPrint(OdResponse response) {
		logger.debug("error:{}", response.getError());
		logger.debug("resultlist:{}", response.getResultsList());
		
		List<ColResponse> colresList = response.getColresList();
		for (ColResponse colres : colresList) {
			logger.debug("attrnm:{}", colres.getArrtnm());
			logger.debug("varId:{}", colres.getVarId());
			logger.debug("colVallist:{}", colres.getColvalList());
			
		}
	}
	
		/** @return insomnia */
	private OdRequest getOdrequestTest() {
		OdRequest.Builder builder = OdRequest.newBuilder();
		builder.setTableName("wad_dmn_pdt");
		List<String> attrs = new ArrayList<String>();
		attrs.add("col1");
		attrs.add("col2");
		List<String> varids = new ArrayList<String>();
		attrs.add("varId1");
		attrs.add("varId2");
		builder.addAllVarId(varids);
		builder.setNum(10);
		builder.setContamination(0.01F);
		builder.setTgtDbms(getTestTgtDbms().build());
		
		return builder.build();
	}

		/** @param tgtdbms 
		 * @param searchvo insomnia */
		public OdResponse callOutlierGrpc(WaaDbConnTrgVO tgtdbms, WadOtlDtcVo searchvo) {
			//request 생성...
			OdRequest request = getOdRequest(tgtdbms, searchvo);
			OdResponse response = null;
			//알고리즘정보
			String algnm = searchvo.getAlg().getAlgPnm();
			
			//알고리즘 종류별로 호출한다...
			if ("EE".equals(algnm)) {
				response = blockingStub.ee(request);
			} else if ("ISOFOR".equals(algnm)) {
				response = blockingStub.isofor(request);
				
			} else if ("OCSVM".equals(algnm)) {
				response = blockingStub.ocsvm(request);
				
			} else if ("LOF".equals(algnm)) {
				response = blockingStub.lof(request);
				
			} 
			
			//정보 조회...
//			resPrint(response);
			
			return response;
			
			
		}

		/** @param tgtdbms 
		 * @param searchvo insomnia 
		 * @return */
		private OdRequest getOdRequest(WaaDbConnTrgVO tgtdbms, WadOtlDtcVo searchvo) {
			List<WadAnaVar> varlist = searchvo.getVarlist();
			//테이블정보
			
			String tblnm = varlist.get(0).getDbTblNm();
			//컬럼목록 파라미터 생성
			List<String> collist = new ArrayList<String>();
			List<String> colids = new ArrayList<String>();
			for (WadAnaVar varvo : varlist) {
				collist.add(varvo.getDbColNm().toLowerCase());
				colids.add(varvo.getAnlVarId());
			}
			//파라미터 목록
			List<WaaAlgArg> arglist = searchvo.getArglist();
			int cntnh = 10;   //이웃수
			float conti = 0.01F; //이상값 비율
			for (WaaAlgArg param : arglist) {
				if ("OP".equals(param.getArgPnm())) {
					conti = Float.parseFloat(param.getArgVal()); 
				} else if ("NON".equals(param.getArgPnm())) {
					cntnh = Integer.parseInt(param.getArgVal());
				}
			}
			
			Builder dbmsBuild = getDbmsBuild(tgtdbms);
			
			OdRequest.Builder builder = OdRequest.newBuilder();
			if(StringUtils.hasText(tgtdbms.getDbSchPnm())) {
				builder.setTableName(tgtdbms.getDbSchPnm().toLowerCase()+"."+tblnm);
			} else {
				builder.setTableName(tblnm);
			}
			builder.addAllAttributes(collist);
			builder.addAllVarId(colids);
			builder.setNum(cntnh);
			builder.setContamination(conti);
			builder.setTgtDbms(getDbmsBuild(tgtdbms).build());
			
			return builder.build();
		}



}
