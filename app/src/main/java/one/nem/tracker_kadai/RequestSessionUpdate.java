package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;


public class RequestSessionUpdate {
    @JsonProperty
    public String session_id;
    @JsonProperty
    public String timestamp;

    public RequestSessionUpdate(String session_id, String timestamp) {
        this.session_id = session_id;
        this.timestamp = timestamp;
    }

    public RequestSessionUpdate() {
    }
}
