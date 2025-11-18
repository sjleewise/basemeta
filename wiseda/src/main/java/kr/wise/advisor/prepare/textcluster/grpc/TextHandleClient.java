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

package kr.wise.advisor.prepare.textcluster.grpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.protobuf.ProtocolStringList;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import kr.wise.admin.ai.algorithm.service.WaaAlgArg;
import kr.wise.advisor.prepare.dataset.service.WadAnaVar;
import kr.wise.advisor.prepare.outlier.grpc.OdRequest;
import kr.wise.advisor.prepare.outlier.grpc.OdResponse;
import kr.wise.advisor.prepare.outlier.service.WadOtlDtcVo;
import kr.wise.advisor.prepare.summary.grpc.TargetDbms;
import kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder;
import kr.wise.advisor.prepare.textcluster.grpc.texthandlingGrpc.texthandlingBlockingStub;
import kr.wise.advisor.prepare.textcluster.service.WadDataMtcCol;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

public class TextHandleClient {
//  private static final Logger logger = Logger.getLogger(DmnPredictClient.class.getName());
  private final  Logger logger = LoggerFactory.getLogger(getClass());

  private final ManagedChannel channel;
  private final texthandlingBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public TextHandleClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

	  /** Construct client for accessing RouteGuide server using the existing channel. */
	  TextHandleClient(ManagedChannelBuilder<?> channelBuilder) {
	    channel = channelBuilder.build();
	    blockingStub = texthandlingGrpc.newBlockingStub(channel);
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
	  TextHandleClient client = new TextHandleClient("localhost", 50051);
    try {
      /* Access a service running on the local machine on port 50051 */
      String tblname = "wad_ana_var";
      if (args.length > 0) {
    	  tblname = args[0]; /* Use the arg as the name to greet if provided */
      }
	  	client.testmatching();
//	  	client.testcluster();
    } finally {
      client.shutdown();
    }
  }

	/**  insomnia */
	public void testmatching() {
		logger.debug("textmatching client test");
		
		txtMchRequest request = gettxtMchTest();
		
		txtMchResponse response = blockingStub.dataMatching(request);
		
		resPrint(response);
		
	}

	public txtClstrResponse testcluster() {
		logger.debug("textcluster client test");
		
		
		txtClstrRequest request = gettxtClusterTest();
		
		txtClstrResponse response = blockingStub.textClustering(request);
		
		resPrintCluster(response);
		
		return response;
		
	}

	/** @param response insomnia */
	private void resPrintCluster(txtClstrResponse response) {
		List<TxtClstr> txtClstrList = response.getTxtClstrList();
		for (TxtClstr resvo : txtClstrList) {
			logger.debug("추천용어:{}", resvo.getRecommand());
			logger.debug("컬러스터용어:{}", resvo.getResultList());
		}
	}

	/** @return insomnia */
	private txtClstrRequest gettxtClusterTest() {
		txtClstrRequest.Builder builder = txtClstrRequest.newBuilder();

		builder.setTableName("fintech.license").setColName("app_nm");
		builder.setTgtDbms(getTestTgtDbms().build());
		
		return builder.build();
	}

	/** @param response insomnia */
	private void resPrint(txtMchResponse response) {
		
		List<TxtMch> colValList = response.getColValList();
		List<txtMchResult> resList = response.getResultList();
		int cntlist = colValList.size();
		
		for (int i=0; i<cntlist;i++) {
			TxtMch colvo = colValList.get(i);
			logger.debug("srccol:{}", colvo.getColValList());
			List<TxtMch> txtMchList = resList.get(i).getTxtMchList();
			for (TxtMch txtMch : txtMchList) {
				logger.debug("tgtcol:{}, res:{}", txtMch.getColValList(), txtMch.getSimilarity());
			}
		}
	}
	
		/** @return insomnia */
	private txtMchRequest gettxtMchTest() {
		txtMchRequest.Builder builder = txtMchRequest.newBuilder();

		InfoDb.Builder tgtdb = InfoDb.newBuilder();
		InfoDb.Builder srcdb = InfoDb.newBuilder();
		
		List<String> colnms = new ArrayList<String>();
		colnms.add("do");
		colnms.add("sigun");
		srcdb.setTableName("fintech.koreaxy").addColName("do").addColName("sigun");
		tgtdb.setTableName("fintech.population").addColName("do").addColName("sigun");
		
		builder.setSourceTb(srcdb.build()).setTargetTb(tgtdb.build());
		builder.setTgtDbms(getTestTgtDbms().build());
		
		return builder.build();
	}
	
	


		/** @param collist
		/** @param tgtdbms insomnia */
		public txtMchResponse callTextMatch(List<WadDataMtcCol> collist, WaaDbConnTrgVO tgtdbms) {
			
			txtMchRequest request = getTextMatchRequest(tgtdbms, collist);
			txtMchResponse response = blockingStub.dataMatching(request);
			
			resPrint(response);
			
			return response;
		}

		/** @param tgtdbms
		/** @param collist
		/** @return insomnia */
		private txtMchRequest getTextMatchRequest(WaaDbConnTrgVO tgtdbms, List<WadDataMtcCol> collist) {
			
			txtMchRequest.Builder builder = txtMchRequest.newBuilder();  
			
			InfoDb.Builder tgtdb = InfoDb.newBuilder();
			InfoDb.Builder srcdb = InfoDb.newBuilder();
			
			for (WadDataMtcCol colvo : collist) {
				if(StringUtils.hasText(tgtdbms.getDbSchPnm())) {
					srcdb.setTableName(tgtdbms.getDbSchPnm().toLowerCase()+"."+colvo.getSrcDbcTblNm().toLowerCase());
					tgtdb.setTableName(tgtdbms.getDbSchPnm().toLowerCase()+"."+colvo.getTgtDbcTblNm().toLowerCase());
				} else {
					srcdb.setTableName(colvo.getSrcDbcTblNm().toLowerCase());
					tgtdb.setTableName(colvo.getTgtDbcTblNm().toLowerCase());
				}
				
				srcdb.addColName(colvo.getSrcDbcColNm().toLowerCase());
				tgtdb.addColName(colvo.getTgtDbcColNm().toLowerCase());
			}
		    	
//			srcdb.setTableName("fintech.koreaxy").addColName("do").addColName("sigun");
//			tgtdb.setTableName("fintech.population").addColName("do").addColName("sigun");
			
			builder.setSourceTb(srcdb.build()).setTargetTb(tgtdb.build());
			builder.setTgtDbms(getDbmsBuild(tgtdbms).build());
			
			return builder.build();
		}

		/** @param anavo
		/** @param tgtdbms
		/** @return insomnia */
		public txtClstrResponse callTextCluster(WadAnaVar anavo, WaaDbConnTrgVO tgtdbms) {
			logger.debug("textcluster client 호출:{}", anavo.getAnlVarId());
			
			txtClstrRequest request = getClusterRequest(anavo, tgtdbms);
			
			txtClstrResponse response = blockingStub.textClustering(request);
			
//			resPrintCluster(response);
			
			return response;
		}

		/** @param anavo
		/** @param tgtdbms
		/** @return insomnia */
		private txtClstrRequest getClusterRequest(WadAnaVar anavo, WaaDbConnTrgVO tgtdbms) {
			txtClstrRequest.Builder builder = txtClstrRequest.newBuilder();

			String tblnm = "";
			
			if(StringUtils.hasText(tgtdbms.getDbSchPnm())) {
				tblnm = tgtdbms.getDbSchPnm() + "." + anavo.getDbcTblNm();
			} else {
				tblnm = anavo.getDbcTblNm();
			}
			
			builder.setTableName(tblnm.toLowerCase()).setColName(anavo.getDbcColNm().toLowerCase());
			builder.setTgtDbms(getDbmsBuild(tgtdbms).build());
			
			return builder.build();
		}



}
