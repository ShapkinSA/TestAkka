package agents;
import lombok.extern.slf4j.Slf4j;
import messageTypes.TcpTestMessage;
import tcp.TcpClient;
import tcp.model.MessageBox;
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


    public void incomingTcpTestMessage(Object payload) {
        log.info("I(agent) receive base tcp message {}", (TcpTestMessage) payload);
    }


    public void closingTcpBaseConnection() {
        log.info("I(agent) close tcp connection");
    }

}
