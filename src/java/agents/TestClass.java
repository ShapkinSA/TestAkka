package agents;
import lombok.extern.slf4j.Slf4j;
import messageTypes.TcpAkkaMessage;
import messageTypes.TcpTestMessage;
import tcp.TcpClient;
import tcp.model.MessageBox;
import tcp.router.OnRouterMessage;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestClass {


    public TestClass(String ownName, String receiverName, TcpClient tcpClient) {
        String tcpTestMessage = MessageBox.createMsg(new TcpTestMessage(receiverName, "hello From "+ ownName + " base tcp"), "TcpTestMessage");
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            tcpClient.sendMessage(tcpTestMessage);
        }, 0, 1, TimeUnit.SECONDS);

    }


    public OnRouterMessage<TcpAkkaMessage> incomingTcpAkkaMessage(SocketAddress socketAddress, Object payload) {
        log.info("I(router_agent) receive tcp message from {}. Msg: {}. Routing message to local actor", socketAddress, payload);
        TcpAkkaMessage tcpAkkaMessage = (TcpAkkaMessage) payload;
        return new OnRouterMessage<>(tcpAkkaMessage.getAgentName(),tcpAkkaMessage);
    }


    public void incomingTcpTestMessage(SocketAddress socketAddress, Object payload) {
        log.info("I(agent) receive base tcp message from {}. Msg: {}. Routing message to local actor", socketAddress, (TcpTestMessage) payload);
    }


    public void closingTcpRouterConnection(SocketAddress socketAddress) {
        log.info("I(router_agent) close tcp connection from {}", socketAddress);
    }

    public void closingTcpBaseConnection(SocketAddress socketAddress) {
        log.info("I(agent) close tcp connection from {}", socketAddress);
    }

}
