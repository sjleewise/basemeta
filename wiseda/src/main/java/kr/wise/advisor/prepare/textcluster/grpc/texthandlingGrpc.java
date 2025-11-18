package kr.wise.advisor.prepare.textcluster.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: texthandling.proto")
public final class texthandlingGrpc {

  private texthandlingGrpc() {}

  public static final String SERVICE_NAME = "kr.wise.advisor.prepare.textcluster.texthandling";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest,
      kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse> METHOD_DATA_MATCHING =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest, kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.textcluster.texthandling", "data_matching"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest,
      kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse> METHOD_TEXT_CLUSTERING =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest, kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.textcluster.texthandling", "textClustering"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static texthandlingStub newStub(io.grpc.Channel channel) {
    return new texthandlingStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static texthandlingBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new texthandlingBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static texthandlingFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new texthandlingFutureStub(channel);
  }

  /**
   */
  public static abstract class texthandlingImplBase implements io.grpc.BindableService {

    /**
     */
    public void dataMatching(kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_DATA_MATCHING, responseObserver);
    }

    /**
     */
    public void textClustering(kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_TEXT_CLUSTERING, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_DATA_MATCHING,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest,
                kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse>(
                  this, METHODID_DATA_MATCHING)))
          .addMethod(
            METHOD_TEXT_CLUSTERING,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest,
                kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse>(
                  this, METHODID_TEXT_CLUSTERING)))
          .build();
    }
  }

  /**
   */
  public static final class texthandlingStub extends io.grpc.stub.AbstractStub<texthandlingStub> {
    private texthandlingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private texthandlingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected texthandlingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new texthandlingStub(channel, callOptions);
    }

    /**
     */
    public void dataMatching(kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_DATA_MATCHING, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void textClustering(kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_TEXT_CLUSTERING, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class texthandlingBlockingStub extends io.grpc.stub.AbstractStub<texthandlingBlockingStub> {
    private texthandlingBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private texthandlingBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected texthandlingBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new texthandlingBlockingStub(channel, callOptions);
    }

    /**
     */
    public kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse dataMatching(kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_DATA_MATCHING, getCallOptions(), request);
    }

    /**
     */
    public kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse textClustering(kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_TEXT_CLUSTERING, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class texthandlingFutureStub extends io.grpc.stub.AbstractStub<texthandlingFutureStub> {
    private texthandlingFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private texthandlingFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected texthandlingFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new texthandlingFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse> dataMatching(
        kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_DATA_MATCHING, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse> textClustering(
        kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_TEXT_CLUSTERING, getCallOptions()), request);
    }
  }

  private static final int METHODID_DATA_MATCHING = 0;
  private static final int METHODID_TEXT_CLUSTERING = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final texthandlingImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(texthandlingImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_DATA_MATCHING:
          serviceImpl.dataMatching((kr.wise.advisor.prepare.textcluster.grpc.txtMchRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.textcluster.grpc.txtMchResponse>) responseObserver);
          break;
        case METHODID_TEXT_CLUSTERING:
          serviceImpl.textClustering((kr.wise.advisor.prepare.textcluster.grpc.txtClstrRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.textcluster.grpc.txtClstrResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class texthandlingDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return kr.wise.advisor.prepare.textcluster.grpc.TextProcess.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (texthandlingGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new texthandlingDescriptorSupplier())
              .addMethod(METHOD_DATA_MATCHING)
              .addMethod(METHOD_TEXT_CLUSTERING)
              .build();
        }
      }
    }
    return result;
  }
}
