package de.bahr.client;

/**
 * Created by michaelbahr on 26/11/16.
 */
public class Reorder {

    private String owner;
    private String destination;
    private String link;
    private String title;

    public Reorder(String owner, String destination, String link, String title) {
        this.owner = owner;
        this.destination = destination;
        this.link = link;
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
