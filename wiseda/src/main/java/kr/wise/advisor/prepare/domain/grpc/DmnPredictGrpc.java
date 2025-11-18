package kr.wise.advisor.prepare.domain.grpc;

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
    comments = "Source: dmnpredict.proto")
public final class DmnPredictGrpc {

  private DmnPredictGrpc() {}

  public static final String SERVICE_NAME = "domain.DmnPredict";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.domain.grpc.DmnRequest,
      kr.wise.advisor.prepare.domain.grpc.DmnResponse> METHOD_GET_DMN_PREDICT =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.domain.grpc.DmnRequest, kr.wise.advisor.prepare.domain.grpc.DmnResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "domain.DmnPredict", "GetDmnPredict"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.domain.grpc.DmnRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.domain.grpc.DmnResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DmnPredictStub newStub(io.grpc.Channel channel) {
    return new DmnPredictStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DmnPredictBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DmnPredictBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DmnPredictFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DmnPredictFutureStub(channel);
  }

  /**
   */
  public static abstract class DmnPredictImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public void getDmnPredict(kr.wise.advisor.prepare.domain.grpc.DmnRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.domain.grpc.DmnResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_DMN_PREDICT, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_DMN_PREDICT,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.domain.grpc.DmnRequest,
                kr.wise.advisor.prepare.domain.grpc.DmnResponse>(
                  this, METHODID_GET_DMN_PREDICT)))
          .build();
    }
  }

  /**
   */
  public static final class DmnPredictStub extends io.grpc.stub.AbstractStub<DmnPredictStub> {
    private DmnPredictStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DmnPredictStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DmnPredictStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DmnPredictStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public void getDmnPredict(kr.wise.advisor.prepare.domain.grpc.DmnRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.domain.grpc.DmnResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_DMN_PREDICT, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DmnPredictBlockingStub extends io.grpc.stub.AbstractStub<DmnPredictBlockingStub> {
    private DmnPredictBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DmnPredictBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DmnPredictBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DmnPredictBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public kr.wise.advisor.prepare.domain.grpc.DmnResponse getDmnPredict(kr.wise.advisor.prepare.domain.grpc.DmnRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_DMN_PREDICT, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DmnPredictFutureStub extends io.grpc.stub.AbstractStub<DmnPredictFutureStub> {
    private DmnPredictFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DmnPredictFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DmnPredictFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DmnPredictFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Get summary data for specified table
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.domain.grpc.DmnResponse> getDmnPredict(
        kr.wise.advisor.prepare.domain.grpc.DmnRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_DMN_PREDICT, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_DMN_PREDICT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DmnPredictImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DmnPredictImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_DMN_PREDICT:
          serviceImpl.getDmnPredict((kr.wise.advisor.prepare.domain.grpc.DmnRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.domain.grpc.DmnResponse>) responseObserver);
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

  private static final class DmnPredictDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return kr.wise.advisor.prepare.domain.grpc.DmnPredictProcess.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DmnPredictGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DmnPredictDescriptorSupplier())
              .addMethod(METHOD_GET_DMN_PREDICT)
              .build();
        }
      }
    }
    return result;
  }
}
