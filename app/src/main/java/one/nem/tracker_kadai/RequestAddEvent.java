package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAddEvent {
    @JsonProperty
    public String point_id;
    @JsonProperty
    public String timestamp;

    public RequestAddEvent(String point_id, String timestamp) {
        this.point_id = point_id;
        this.timestamp = timestamp;
    }

    public RequestAddEvent() {
    }
}
