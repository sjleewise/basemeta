package kr.wise.advisor.prepare.outlier.grpc;

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
    comments = "Source: odservice.proto")
public final class OdServiceGrpc {

  private OdServiceGrpc() {}

  public static final String SERVICE_NAME = "kr.wise.advisor.prepare.outlier.OdService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.outlier.grpc.OdRequest,
      kr.wise.advisor.prepare.outlier.grpc.OdResponse> METHOD_LOF =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.outlier.grpc.OdRequest, kr.wise.advisor.prepare.outlier.grpc.OdResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.outlier.OdService", "lof"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.outlier.grpc.OdRequest,
      kr.wise.advisor.prepare.outlier.grpc.OdResponse> METHOD_OCSVM =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.outlier.grpc.OdRequest, kr.wise.advisor.prepare.outlier.grpc.OdResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.outlier.OdService", "ocsvm"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.outlier.grpc.OdRequest,
      kr.wise.advisor.prepare.outlier.grpc.OdResponse> METHOD_EE =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.outlier.grpc.OdRequest, kr.wise.advisor.prepare.outlier.grpc.OdResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.outlier.OdService", "ee"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdResponse.getDefaultInstance()))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<kr.wise.advisor.prepare.outlier.grpc.OdRequest,
      kr.wise.advisor.prepare.outlier.grpc.OdResponse> METHOD_ISOFOR =
      io.grpc.MethodDescriptor.<kr.wise.advisor.prepare.outlier.grpc.OdRequest, kr.wise.advisor.prepare.outlier.grpc.OdResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "kr.wise.advisor.prepare.outlier.OdService", "isofor"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              kr.wise.advisor.prepare.outlier.grpc.OdResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static OdServiceStub newStub(io.grpc.Channel channel) {
    return new OdServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static OdServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new OdServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static OdServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new OdServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class OdServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void lof(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_LOF, responseObserver);
    }

    /**
     */
    public void ocsvm(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_OCSVM, responseObserver);
    }

    /**
     */
    public void ee(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EE, responseObserver);
    }

    /**
     */
    public void isofor(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ISOFOR, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_LOF,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.outlier.grpc.OdRequest,
                kr.wise.advisor.prepare.outlier.grpc.OdResponse>(
                  this, METHODID_LOF)))
          .addMethod(
            METHOD_OCSVM,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.outlier.grpc.OdRequest,
                kr.wise.advisor.prepare.outlier.grpc.OdResponse>(
                  this, METHODID_OCSVM)))
          .addMethod(
            METHOD_EE,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.outlier.grpc.OdRequest,
                kr.wise.advisor.prepare.outlier.grpc.OdResponse>(
                  this, METHODID_EE)))
          .addMethod(
            METHOD_ISOFOR,
            asyncUnaryCall(
              new MethodHandlers<
                kr.wise.advisor.prepare.outlier.grpc.OdRequest,
                kr.wise.advisor.prepare.outlier.grpc.OdResponse>(
                  this, METHODID_ISOFOR)))
          .build();
    }
  }

  /**
   */
  public static final class OdServiceStub extends io.grpc.stub.AbstractStub<OdServiceStub> {
    private OdServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OdServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OdServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OdServiceStub(channel, callOptions);
    }

    /**
     */
    public void lof(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_LOF, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ocsvm(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_OCSVM, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ee(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_EE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void isofor(kr.wise.advisor.prepare.outlier.grpc.OdRequest request,
        io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ISOFOR, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class OdServiceBlockingStub extends io.grpc.stub.AbstractStub<OdServiceBlockingStub> {
    private OdServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OdServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OdServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OdServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public kr.wise.advisor.prepare.outlier.grpc.OdResponse lof(kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_LOF, getCallOptions(), request);
    }

    /**
     */
    public kr.wise.advisor.prepare.outlier.grpc.OdResponse ocsvm(kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_OCSVM, getCallOptions(), request);
    }

    /**
     */
    public kr.wise.advisor.prepare.outlier.grpc.OdResponse ee(kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_EE, getCallOptions(), request);
    }

    /**
     */
    public kr.wise.advisor.prepare.outlier.grpc.OdResponse isofor(kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ISOFOR, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class OdServiceFutureStub extends io.grpc.stub.AbstractStub<OdServiceFutureStub> {
    private OdServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private OdServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected OdServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new OdServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.outlier.grpc.OdResponse> lof(
        kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_LOF, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.outlier.grpc.OdResponse> ocsvm(
        kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_OCSVM, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.outlier.grpc.OdResponse> ee(
        kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_EE, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.wise.advisor.prepare.outlier.grpc.OdResponse> isofor(
        kr.wise.advisor.prepare.outlier.grpc.OdRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ISOFOR, getCallOptions()), request);
    }
  }

  private static final int METHODID_LOF = 0;
  private static final int METHODID_OCSVM = 1;
  private static final int METHODID_EE = 2;
  private static final int METHODID_ISOFOR = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final OdServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(OdServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LOF:
          serviceImpl.lof((kr.wise.advisor.prepare.outlier.grpc.OdRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse>) responseObserver);
          break;
        case METHODID_OCSVM:
          serviceImpl.ocsvm((kr.wise.advisor.prepare.outlier.grpc.OdRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse>) responseObserver);
          break;
        case METHODID_EE:
          serviceImpl.ee((kr.wise.advisor.prepare.outlier.grpc.OdRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse>) responseObserver);
          break;
        case METHODID_ISOFOR:
          serviceImpl.isofor((kr.wise.advisor.prepare.outlier.grpc.OdRequest) request,
              (io.grpc.stub.StreamObserver<kr.wise.advisor.prepare.outlier.grpc.OdResponse>) responseObserver);
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

  private static final class OdServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return kr.wise.advisor.prepare.outlier.grpc.OutlierProcess.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (OdServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new OdServiceDescriptorSupplier())
              .addMethod(METHOD_LOF)
              .addMethod(METHOD_OCSVM)
              .addMethod(METHOD_EE)
              .addMethod(METHOD_ISOFOR)
              .build();
        }
      }
    }
    return result;
  }
}
