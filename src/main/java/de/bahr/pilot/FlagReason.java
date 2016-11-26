package de.bahr.pilot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;

/**
 * Created by michaelbahr on 14/11/16.
 */
public class FlagReason {

    @Id
    private String id;
    private String pilot;
    private String orderId;
    private String reason;

    @JsonCreator
    public FlagReason(@JsonProperty String pilot, @JsonProperty String orderId, @JsonProperty String reason) {
        this.pilot = pilot;
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
