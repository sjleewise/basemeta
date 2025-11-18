package kr.wise.advisor.prepare.summary.grpc;

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
    comments = "Source: summary.proto")
public final class SummaryServiceGrpc {

  private SummaryServiceGrpc() {}

  public static final String SERVICE_NAME = "kr.wise.advisor.prepare.summary.SummaryService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.summary.grpc.TableRequest,
      kr.wise.advisor.prepare.summary.grpc.SummaryReply> METHOD_GET_SUMMARY =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.summary.grpc.TableRequest, kr.wise.advisor.prepare.summary.grpc.SummaryReply>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.summary.SummaryService", "GetSummary"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.summary.grpc.TableRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.summary.grpc.SummaryReply.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.summary.grpc.ColRequest,
      kr.wise.advisor.prepare.summary.grpc.HistResponse> METHOD_GET_HISTOGRAM =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.summary.grpc.ColRequest, kr.wise.advisor.prepare.summary.grpc.HistResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.summary.SummaryService", "GetHistogram"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.summary.grpc.ColRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.summary.grpc.HistResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.summary.grpc.ColRequest,
      kr.wise.advisor.prepare.summary.grpc.BoxResponse> METHOD_GET_BOX_OUTLIER =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.summary.grpc.ColRequest, kr.wise.advisor.prepare.summary.grpc.BoxResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.summary.SummaryService", "GetBoxOutlier"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.summary.grpc.ColRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.summary.grpc.BoxResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SummaryServiceStub newStub(io.grpc.Channel channel) {
    return new SummaryServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SummaryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SummaryServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SummaryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SummaryServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SummaryServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public void getSummary(kr.wise.advisor.prepare.summary.grpc.TableRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.SummaryReply> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SUMMARY, responseObserver);
    }

    /**
     * <pre>
     * 히스토그램 조회 (컬럼기준)
     * </pre>
     */
    public void getHistogram(kr.wise.advisor.prepare.summary.grpc.ColRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.HistResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_HISTOGRAM, responseObserver);
    }

    /**
     * <pre>
     * boxplot + outlier 호출
     * </pre>
     */
    public void getBoxOutlier(kr.wise.advisor.prepare.summary.grpc.ColRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.BoxResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_BOX_OUTLIER, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_SUMMARY,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.summary.grpc.TableRequest,
                kr.wise.advisor.prepare.summary.grpc.SummaryReply>(
                  this, METHODID_GET_SUMMARY)))
          .addMethod(
            METHOD_GET_HISTOGRAM,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.summary.grpc.ColRequest,
                kr.wise.advisor.prepare.summary.grpc.HistResponse>(
                  this, METHODID_GET_HISTOGRAM)))
          .addMethod(
            METHOD_GET_BOX_OUTLIER,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.summary.grpc.ColRequest,
                kr.wise.advisor.prepare.summary.grpc.BoxResponse>(
                  this, METHODID_GET_BOX_OUTLIER)))
          .build();
    }
  }

  /**
   */
  public static final class SummaryServiceStub extends io.grpc.stub.AbstractStub<SummaryServiceStub> {
    private SummaryServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SummaryServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SummaryServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SummaryServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public void getSummary(kr.wise.advisor.prepare.summary.grpc.TableRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.SummaryReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_SUMMARY, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * 히스토그램 조회 (컬럼기준)
     * </pre>
     */
    public void getHistogram(kr.wise.advisor.prepare.summary.grpc.ColRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.HistResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_HISTOGRAM, getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * boxplot + outlier 호출
     * </pre>
     */
    public void getBoxOutlier(kr.wise.advisor.prepare.summary.grpc.ColRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.BoxResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_BOX_OUTLIER, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SummaryServiceBlockingStub extends io.grpc.stub.AbstractStub<SummaryServiceBlockingStub> {
    private SummaryServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SummaryServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SummaryServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SummaryServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public kr.wise.advisor.prepare.summary.grpc.SummaryReply getSummary(kr.wise.advisor.prepare.summary.grpc.TableRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_SUMMARY, getCallOptions(), request);
    }

    /**
     * <pre>
     * 히스토그램 조회 (컬럼기준)
     * </pre>
     */
    public kr.wise.advisor.prepare.summary.grpc.HistResponse getHistogram(kr.wise.advisor.prepare.summary.grpc.ColRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_HISTOGRAM, getCallOptions(), request);
    }

    /**
     * <pre>
     * boxplot + outlier 호출
     * </pre>
     */
    public kr.wise.advisor.prepare.summary.grpc.BoxResponse getBoxOutlier(kr.wise.advisor.prepare.summary.grpc.ColRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_BOX_OUTLIER, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SummaryServiceFutureStub extends io.grpc.stub.AbstractStub<SummaryServiceFutureStub> {
    private SummaryServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SummaryServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SummaryServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SummaryServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.summary.grpc.SummaryReply> getSummary(
        kr.wise.advisor.prepare.summary.grpc.TableRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_SUMMARY, getCallOptions()), request);
    }

    /**
     * <pre>
     * 히스토그램 조회 (컬럼기준)
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.summary.grpc.HistResponse> getHistogram(
        kr.wise.advisor.prepare.summary.grpc.ColRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_HISTOGRAM, getCallOptions()), request);
    }

    /**
     * <pre>
     * boxplot + outlier 호출
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.summary.grpc.BoxResponse> getBoxOutlier(
        kr.wise.advisor.prepare.summary.grpc.ColRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_BOX_OUTLIER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_SUMMARY = 0;
  private static final int METHODID_GET_HISTOGRAM = 1;
  private static final int METHODID_GET_BOX_OUTLIER = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SummaryServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SummaryServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_SUMMARY:
          serviceImpl.getSummary((kr.wise.advisor.prepare.summary.grpc.TableRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.SummaryReply>) responseObserver);
          break;
        case METHODID_GET_HISTOGRAM:
          serviceImpl.getHistogram((kr.wise.advisor.prepare.summary.grpc.ColRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.HistResponse>) responseObserver);
          break;
        case METHODID_GET_BOX_OUTLIER:
          serviceImpl.getBoxOutlier((kr.wise.advisor.prepare.summary.grpc.ColRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.summary.grpc.BoxResponse>) responseObserver);
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

  private static final class SummaryServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return kr.wise.advisor.prepare.summary.grpc.SummaryProcess.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SummaryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SummaryServiceDescriptorSupplier())
              .addMethod(METHOD_GET_SUMMARY)
              .addMethod(METHOD_GET_HISTOGRAM)
              .addMethod(METHOD_GET_BOX_OUTLIER)
              .build();
        }
      }
    }
    return result;
  }
}
