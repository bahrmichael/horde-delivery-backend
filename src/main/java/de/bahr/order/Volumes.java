package de.bahr.order;

/**
 * Created by michaelbahr on 27/07/16.
 */
public class Volumes {

    private Long typeid;
    private Double volume;

    public Volumes(Long typeid, Double volume) {
        this.typeid = typeid;
        this.volume = volume;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
