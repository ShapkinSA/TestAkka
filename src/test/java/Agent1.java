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

public class Agent1 {

    public static void main(String[] args) {

        AgentCfg agentCfg = WorkWithConfigGFiles.unMarshalAny(
                AgentCfg.class,
                args[0]
        );

        ActorSystem system = ActorSystem.create(agentCfg.getSystemName());

        ActorRef local_actor = system.actorOf(
                LocalActor.props(agentCfg.getAgentName(),
                        (agentCfg.getAgentName().equals("agent_1") ? "agent_2" : "agent_1")), agentCfg.getAgentName());

        TcpManager manager = new TcpManager(system, agentCfg.getTcpSettings());

        TestClass testClass = new TestClass(agentCfg.getAgentName(), (agentCfg.getAgentName().equals("agent_1") ? "agent_2" : "agent_1"), manager.getTcpClients().get("SIMPLE_TCP").get(0));

        manager.addRouterHandler(new RouterHandler<>(TcpAkkaMessage.class, testClass::incomingTcpAkkaMessage, testClass::closingTcpRouterConnection));

        manager.addBaseHandler("SIMPLE_TCP", new BaseHandler<>(TcpTestMessage.class, testClass::incomingTcpTestMessage, testClass::closingTcpBaseConnection));

        manager.startAllServers();
    }
}