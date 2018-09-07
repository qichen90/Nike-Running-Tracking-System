package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Document
public class Location {

    private final String KEY = "";

    @Id
    private String id;
    private String fromAddress;
    private String toAddress;
    private String polyline;

    public Location() {}

    @JsonCreator
    public Location(@JsonProperty("fromAddress") String fromAddress, @JsonProperty("toAddress") String toAddress) throws Exception{
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;

        setPolyline(fromAddress, toAddress);
    }

    private void setPolyline(String fromAddress, String toAddress) throws Exception{
        GeoApiContext context = new GeoApiContext();
        context.setApiKey(KEY);
        DirectionsResult result = DirectionsApi.getDirections(context, fromAddress, toAddress).await();
        this.polyline = result.routes[0].legs[0].steps[0].polyline.getEncodedPath();
    }
}
