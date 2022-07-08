package eu.suro.launch.grpc;

import io.grpc.stub.StreamObserver;
import org.bukkit.Bukkit;
import server.ServerGrpc;
import server.ServerOuterClass;

public class Server {

    private ServerGrpc.ServerStub stub;

    public Server(ServerGrpc.ServerStub stub){
        this.stub = stub;
    }

    public void UpdateServer(Status status){
        ServerOuterClass.UpdateServerRequest updateServerRequest = ServerOuterClass
                        .UpdateServerRequest
                        .newBuilder()
                        .setMessage(status.status)
                        .setPort(Bukkit.getPort())
                        .buildPartial();
        stub.updateServer(updateServerRequest, new UpdateCallback());
    }

    class UpdateCallback implements StreamObserver<ServerOuterClass.Response>{

        @Override
        public void onNext(ServerOuterClass.Response value) {

        }

        @Override
        public void onError(Throwable t) {

        }

        @Override
        public void onCompleted() {

        }
    }


    public enum Status {
        STARTING("starting"),
        RUNNING("running"),
        STOPPING("stopping");

        private final String status;

        Status(String status) {
            this.status = status;
        }

        public String getUrl() {
            return status;
        }
    }

}

