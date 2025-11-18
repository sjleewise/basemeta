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

package kr.wise.advisor.prepare.summary.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import kr.wise.advisor.prepare.domain.grpc.TargetDbms.Builder;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class SummaryClient {
//  private static final Logger logger = Logger.getLogger(DmnPredictClient.class.getName());
  private final  Logger logger = LoggerFactory.getLogger(getClass());

  private final ManagedChannel channel;
  private final SummaryServiceGrpc.SummaryServiceBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public SummaryClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  /** Construct client for accessing RouteGuide server using the existing channel. */
  SummaryClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = SummaryServiceGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** 도메인판별요청 to 서버 */
  public void CallSummaryTest(String tblname) {
//    logger.info("Will try to greet " + name + " ...");
    kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = getTestTgtDbms();
    
    TableRequest.Builder tblrqst = TableRequest.newBuilder();
    tblrqst.setTableName(tblname);
    tblrqst.setTgtDbms(tgtdbms.build());
    
    TableRequest request = tblrqst.build();
	
    SummaryReply response;
    try {
      response = blockingStub.getSummary(request);
      
      List<Summary> reslist = response.getSummariesList();
      logger.debug(reslist.toString());
      
    } catch (StatusRuntimeException e) {
//    	e.printStackTrace();
    	logger.error("RPC failed: {}", e.toString());
      
      return;
    }
//    logger.info("Greeting: " + response.getMessage());
  }

/** grpc client test용 db접속정보 생성 */
private kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder getTestTgtDbms() {
	kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = TargetDbms.newBuilder();
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
	  SummaryClient client = new SummaryClient("localhost", 50051);
    try {
      /* Access a service running on the local machine on port 50051 */
      String tblname = "wad_ana_var";
      if (args.length > 0) {
    	  tblname = args[0]; /* Use the arg as the name to greet if provided */
      }
      client.CallSummaryTest("fintech.claim_data");
//      client.callHisttoTest();
//      client.callBoxTest();
    } finally {
      client.shutdown();
    }
  }

	/**  insomnia */
	private void callHisttoTest() {
		logger.debug("히스토그램 client test");
		kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = getTestTgtDbms();
		
		ColRequest request = ColRequest.newBuilder()
				.setVarId("OBJ_TEST")
				.setColName("obj_vers")
				.setTableName("wad_dmn_pdt")
				.setTgtDbms(tgtdbms)
				.build();
		HistResponse response = blockingStub.getHistogram(request);
		
		logger.debug(response.getVarId());
		logger.debug(response.getStrValList().toString());
		logger.debug(response.getSctValList().toString());
		
	}
	
		/**  insomnia */
	private void callBoxTest() {
		logger.debug("boxplot & outlier client test");
		kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = getTestTgtDbms();
		ColRequest request = ColRequest.newBuilder()
				.setVarId("OBJ_TEST")
				.setColName("obj_vers")
				.setTableName("wad_dmn_pdt")
				.setTgtDbms(tgtdbms)
				.build();
		
		BoxResponse response = blockingStub.getBoxOutlier(request);
		
		logger.debug(response.getVarId());
		logger.debug(response.getSummary().toString());
		logger.debug(response.getOutValList().toString());
	}

	/** @param dbvo insomnia 
	 * @return */
	public List<Summary> CallSummarygrpc(WaaDbConnTrgVO dbvo, String tablenm) {
		
		kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = getDbmsBuild(dbvo);
	    
	    kr.wise.advisor.prepare.summary.grpc.TableRequest.Builder tblrqst = TableRequest.newBuilder();
	    if(StringUtils.hasText(dbvo.getDbSchPnm()))  
	    	tblrqst.setTableName(dbvo.getDbSchPnm().toLowerCase()+"."+tablenm);
	    else 
	    	tblrqst.setTableName(tablenm);
	    	
	    tblrqst.setTgtDbms(tgtdbms.build());
	    
	    TableRequest request = tblrqst.build();
		
	    SummaryReply response;
	    response = blockingStub.getSummary(request);
	      
	    List<Summary> reslist = response.getSummariesList();
	    logger.debug("summary result:{}", reslist.toString());
	    return reslist;
		
	}
	
	public Map<String, Object> CallHistoGrpc(WaaDbConnTrgVO dbvo, String tblnm, String colnm, String varid) {
		
		ColRequest request = getColRequest(dbvo, tblnm, colnm, varid);
	    
	    HistResponse response = blockingStub.getHistogram(request);
	    
	    String resvarId = response.getVarId();
	    List<Float> sctValList = response.getSctValList();
	    List<Float> strValList = response.getStrValList();
	    
	    Map<String, Object> resmap = new HashMap<String, Object>();
	    resmap.put("varId", resvarId);
	    resmap.put("sctVal", sctValList);
	    resmap.put("strVal", strValList);
	    
	    return resmap;
	    
	}

	/** @param dbvo
	/** @param tblnm
	/** @param colnm
	/** @param varid
	/** @return insomnia */
	private ColRequest getColRequest(WaaDbConnTrgVO dbvo, String tblnm, String colnm, String varid) {
		kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = getDbmsBuild(dbvo);
	    String tblname = "";
		if(StringUtils.hasText(dbvo.getDbSchPnm())) tblname = dbvo.getDbSchPnm().toLowerCase()+"."+tblnm; 
		else tblname = tblnm;
	    ColRequest request = ColRequest.newBuilder().setTableName(tblname).setColName(colnm).setVarId(varid).setTgtDbms(tgtdbms.build()).build();
		return request;
	}

	public Map<String, Object> CallBoxPlotGrpc(WaaDbConnTrgVO dbvo, String tblnm, String colnm, String varid) {
		
		ColRequest request = getColRequest(dbvo, tblnm, colnm, varid);
		
		BoxResponse response = blockingStub.getBoxOutlier(request);
		
		String resvarId = response.getVarId();
		Summary summary = response.getSummary();
		List<Float> outlist = response.getOutValList();
		
		Map<String, Object> resmap = new HashMap<String, Object>();
		resmap.put("varId", resvarId);
		resmap.put("summary", summary);
		resmap.put("outlist", outlist);
		
		return resmap;
		
	}

	/** @param dbvo
	/** @return insomnia */
	private kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder getDbmsBuild(WaaDbConnTrgVO dbvo) {
		kr.wise.advisor.prepare.summary.grpc.TargetDbms.Builder tgtdbms = TargetDbms.newBuilder();
		tgtdbms.setDbmsTypCd(dbvo.getDbmsTypCd());
//	    tgtdbms.setConnTrgLnkUrl(dbvo.getConnTrgLnkUrl());
		tgtdbms.setConnTrgLnkUrl("FDS");
		tgtdbms.setDbConnAcId(dbvo.getDbConnAcId());
		tgtdbms.setDbConnAcPwd(dbvo.getDbConnAcPwd());
		tgtdbms.setDbSchPnm(dbvo.getDbSchPnm());
		return tgtdbms;
	}
}
