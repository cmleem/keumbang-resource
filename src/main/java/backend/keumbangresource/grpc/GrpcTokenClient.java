package backend.keumbangresource.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import backend.keumbangresource.grpc.token.TokenProto.TokenRequestProto;
import backend.keumbangresource.grpc.token.TokenProto.TokenResponseProto;
import backend.keumbangresource.grpc.token.TokenServiceGrpc;
import backend.keumbangresource.grpc.token.TokenServiceGrpc.TokenServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;

@Component
public class GrpcTokenClient {
	private final TokenServiceBlockingStub blockingStub;
	
	public GrpcTokenClient(@Value("${custom.grpc.client.port}") int port) {
		ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", port).usePlaintext().build();
		blockingStub = TokenServiceGrpc.newBlockingStub(channel);
	}
	
	public TokenResponseProto getUser(TokenRequestProto request) {
		return blockingStub.getUser(request);
	}
}
