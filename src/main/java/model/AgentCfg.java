package model;
import lombok.Data;
import tcp.model.TcpSettings;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "agent")
public class AgentCfg {

    @XmlElement
    private String agentName;

    @XmlElement
    private String systemName;

    @XmlElement
    private TcpSettings tcpSettings;

}
