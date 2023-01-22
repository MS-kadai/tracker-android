package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestDeleteSession {
    @JsonProperty
    public String session_id;
    @JsonProperty
    public String route_id;

    public RequestDeleteSession(String session_id, String route_id) {
        this.session_id = session_id;
        this.route_id = route_id;
    }

    public RequestDeleteSession() {
    }

}
