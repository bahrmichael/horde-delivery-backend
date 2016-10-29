package de.bahr.order;

/**
 * Created by michaelbahr on 17/07/16.
 */
public class Item {

    private String name;
    private Long quantity;
    private Double volume;
    private Long price;

    public Item(String name, Long quantity, Double volume, Long price) {
        this.name = name;
        this.quantity = quantity;
        this.volume = volume;
        this.price = price;
    }

    public Item(String name, Double volume) {
        this.name = name;
        this.volume = volume;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
