import actors.LocalActor;
import agents.TestClass;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import messageTypes.TcpAkkaMessage;
import messageTypes.TcpTestMessage;
import model.AgentCfg;
import tcp.TcpManager;
import tcp.handlers.BaseHandler;
import tcp.handlers.RouterHandler;
import utils.WorkWithConfigGFiles;

public class Main {


    public static void main(String[] args) {

        AgentCfg agentCfg = WorkWithConfigGFiles.unMarshalAny(
                AgentCfg.class,
                args[0]
        );

        ActorSystem system = ActorSystem.create(agentCfg.getSystemName());

        ActorRef local_actor = system.actorOf(
                LocalActor.props(agentCfg.getAgentName(),
                        (agentCfg.getAgentName().equals("agent_1") ? "agent_2" : "agent_1")), agentCfg.getAgentName());

        TcpManager.configureTcpManager(system, agentCfg.getTcpSettings());

        TestClass testClass = new TestClass(agentCfg.getAgentName(), (agentCfg.getAgentName().equals("agent_1") ? "agent_2" : "agent_1"), TcpManager.getTcpClients().get("SIMPLE_TCP").get(0));

        TcpManager.addRouterHandler(new RouterHandler<>(TcpAkkaMessage.class, testClass::incomingTcpAkkaMessage, testClass::closingTcpRouterConnection));

        TcpManager.addBaseHandler("SIMPLE_TCP", new BaseHandler<>(TcpTestMessage.class, testClass::incomingTcpTestMessage, testClass::closingTcpBaseConnection));

        TcpManager.startAllServers();
    }
}