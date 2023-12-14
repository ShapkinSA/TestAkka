package messageTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcpAkkaMessage {
    private String agentName;
    private String msg;
}
