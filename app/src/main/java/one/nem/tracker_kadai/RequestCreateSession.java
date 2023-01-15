package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestCreateSession {
    @JsonProperty
    public String session_id;
    @JsonProperty
    public String route_id;

    public RequestCreateSession(String session_id, String route_id) {
        this.session_id = session_id;
        this.route_id = route_id;
    }

    public RequestCreateSession() {
    }

}
