package messageTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcpTestMessage {
    private String uniqueKey;
    private String message;
}
