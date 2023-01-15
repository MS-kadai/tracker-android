package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseServerDateTime {
    @JsonProperty
    public String date_time;

    public ResponseServerDateTime(String date_time) {
        this.date_time = date_time;
    }

    public ResponseServerDateTime() {
    }
}
