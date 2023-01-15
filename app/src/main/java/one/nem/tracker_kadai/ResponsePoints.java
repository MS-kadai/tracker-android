package one.nem.tracker_kadai;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponsePoints {
    @JsonProperty
    public Integer point_id;
    @JsonProperty
    public String point_name;
    @JsonProperty
    public String coordinate;

    public ResponsePoints(int point_id, String point_name, String coordinate) {
        this.point_id = point_id;
        this.point_name = point_name;
        this.coordinate = coordinate;
    }

    public ResponsePoints() {
    }
}
