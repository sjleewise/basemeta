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

package kr.wise.advisor.prepare.domain.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import kr.wise.advisor.prepare.domain.grpc.TargetDbms.Builder;
import kr.wise.commons.damgmt.db.service.WaaDbConnTrgVO;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple client that requests a greeting from the {@link HelloWorldServer}.
 */
public class DmnPredictClient {
//  private static final Logger logger = Logger.getLogger(DmnPredictClient.class.getName());
  private final  Logger logger = LoggerFactory.getLogger(getClass());

  private final ManagedChannel channel;
  private final DmnPredictGrpc.DmnPredictBlockingStub blockingStub;

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public DmnPredictClient(String host, int port) {
    this(ManagedChannelBuilder.forAddress(host, port).usePlaintext(true));
  }

  /** Construct client for accessing RouteGuide server using the existing channel. */
  DmnPredictClient(ManagedChannelBuilder<?> channelBuilder) {
    channel = channelBuilder.build();
    blockingStub = DmnPredictGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** 도메인판별요청 to 서버 */
  public void CallDmnPredict(String rqstNo) {
//    logger.info("Will try to greet " + name + " ...");
    Builder tgtdbms = TargetDbms.newBuilder();
    tgtdbms.setDbmsTypCd("ORA");
    tgtdbms.setConnTrgLnkUrl("FDS");
    tgtdbms.setDbConnAcId("wiseda");
    tgtdbms.setDbConnAcPwd("wise1012");
    tgtdbms.setDbSchPnm("WISEDA");
    
    kr.wise.advisor.prepare.domain.grpc.DmnRequest.Builder dmnrqst = DmnRequest.newBuilder();
    dmnrqst.setRqstNo(rqstNo);
    dmnrqst.setTgtDbms(tgtdbms.build());
    
    DmnRequest request = dmnrqst.build();
	
    DmnResponse response;
    try {
      response = blockingStub.getDmnPredict(request);
      
      List<DmnPredictResult> dmnpdtresult = response.getDmnpdtlistList();
      logger.debug(dmnpdtresult.toString());
      
    } catch (StatusRuntimeException e) {
    	e.printStackTrace();
//    	logger.debug("RPC failed: {}", e.printStackTrace());
      
      return;
    }
//    logger.info("Greeting: " + response.getMessage());
  }

  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
	  DmnPredictClient client = new DmnPredictClient("localhost", 50051);
    try {
      /* Access a service running on the local machine on port 50051 */
      String rqstNo = "REQ_00000009251";
      if (args.length > 0) {
        rqstNo = args[0]; /* Use the arg as the name to greet if provided */
      }
      client.CallDmnPredict(rqstNo);
    } finally {
      client.shutdown();
    }
  }

	/** @param dbvo insomnia 
	 * @return */
	public List<DmnPredictResult> CallDmnPredictbydb(WaaDbConnTrgVO dbvo) {
		
		Builder tgtdbms = TargetDbms.newBuilder();
	    tgtdbms.setDbmsTypCd(dbvo.getDbmsTypCd());
//	    tgtdbms.setConnTrgLnkUrl(dbvo.getConnTrgLnkUrl());
	    tgtdbms.setConnTrgLnkUrl("FDS");
	    tgtdbms.setDbConnAcId(dbvo.getDbConnAcId());
	    tgtdbms.setDbConnAcPwd(dbvo.getDbConnAcPwd());
	    tgtdbms.setDbSchPnm(dbvo.getDbSchPnm());
	    
	    kr.wise.advisor.prepare.domain.grpc.DmnRequest.Builder dmnrqst = DmnRequest.newBuilder();
	    dmnrqst.setRqstNo(dbvo.getRqstNo());
	    dmnrqst.setTgtDbms(tgtdbms.build());
	    
	    DmnRequest request = dmnrqst.build();
		
	    DmnResponse response;
	    response = blockingStub.getDmnPredict(request);
	      
	    List<DmnPredictResult> dmnpdtresult = response.getDmnpdtlistList();
	    logger.debug("도메인판별호출결과:{}", dmnpdtresult.toString());
	    return dmnpdtresult;
		
	}
}
