package de.bahr.pilot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;

/**
 * Created by michaelbahr on 02/08/16.
 */
public class Pilot {

    @Id
    private String id;
    private String name;

    @JsonCreator
    public Pilot(@JsonProperty String id, @JsonProperty String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
