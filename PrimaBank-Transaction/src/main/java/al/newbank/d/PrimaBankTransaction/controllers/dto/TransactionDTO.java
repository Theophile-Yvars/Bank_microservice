package al.newbank.d.PrimaBankTransaction.controllers.dto;

import org.springframework.lang.NonNull;

import java.util.UUID;

public class TransactionDTO {
    private String name;
    private Long client;
    private float price;
    private String country; // france, suisse, wakanda
    private String type; // entreprise ou particulier
    private String origine; // internet ou direct
    private Long targetClient;

    public TransactionDTO(String name, Long client, float price, String country, String type, String origine, Long targetClient) {
        this.name = name;
        this.client = client;
        this.price = price;
        this.country = country;
        this.type = type;
        this.origine = origine;
        this.targetClient = targetClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }

    public Long getTargetClient() {
        return targetClient;
    }

    public void setTargetClient(Long targetClient) {
        this.targetClient = targetClient;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "name='" + name + '\'' +
                ", account=" + client +
                ", price=" + price +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", origine='" + origine + '\'' +
                ", tagetAccount=" + targetClient +
                '}';
    }
}