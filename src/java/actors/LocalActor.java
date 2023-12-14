package actors;
import akka.actor.AbstractActor;
import akka.actor.Props;
import messageTypes.TcpAkkaMessage;
import tcp.model.MessageBox;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LocalActor extends AbstractActor {

    public LocalActor(String ownName, String receiverName) {
        String tcpTestMessage = MessageBox.createMsg(new TcpAkkaMessage(receiverName, "hello From "+ ownName), "TcpAkkaMessage");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            getContext().actorSelection("/user/router_actor").tell(tcpTestMessage,null);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TcpAkkaMessage.class, msg -> {
                    System.out.println(msg.getMsg());
                })
                .build();
    }

    public static Props props(String ownName, String receiverName) {
        return Props.create(LocalActor.class, ownName, receiverName);
    }
}
