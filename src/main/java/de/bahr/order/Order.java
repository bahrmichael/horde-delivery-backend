package de.bahr.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michaelbahr on 13/04/16.
 */
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String client;
    private String link;
    private String status;
    private List<Item> items;
    private Double expectedPrice;
    private String destination;
    private Double setPrice;
    private LocalDateTime created;
    private LocalDateTime completed;
    private Double shippingPrice;
    private String assignee;
    private Long age;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getCompleted() {
        return completed;
    }

    public void setCompleted(LocalDateTime completed) {
        this.completed = completed;
    }

    @JsonCreator
    public Order(@JsonProperty("client") String client, @JsonProperty("link") String link,
                 @JsonProperty("expectedPrice") Double expectedPrice,
                 @JsonProperty("destination") String destination) {
        this.client = client;
        this.link = link;
        this.expectedPrice = expectedPrice;
        this.destination = destination;
        this.status = "requested";
        this.items = new ArrayList<>();
        this.created = LocalDateTime.now(Clock.systemUTC());
    }

    public Order() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setExpectedPrice(Double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public Double getExpectedPrice() {
        return expectedPrice;
    }

    public Double getSetPrice() {
        return setPrice;
    }

    public void setSetPrice(Double setPrice) {
        this.setPrice = setPrice;
    }

    public void setShippingPrice(Double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public Double getShippingPrice() {
        return shippingPrice;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
