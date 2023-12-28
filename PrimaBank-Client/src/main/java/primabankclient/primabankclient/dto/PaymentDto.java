package primabankclient.primabankclient.dto;

import java.util.UUID;

public class PaymentDto {
    String name;
    UUID account;
    float price;
    String country; // france, suisse, wakanda
    String type; // entreprise ou particulier
    String origine; // internet ou direct
    UUID tagetAccount;

    public PaymentDto(String name, UUID account, float price, String country, String type, String origine, UUID tagetAccount) {
        this.name = name;
        this.account = account;
        this.price = price;
        this.country = country;
        this.type = type;
        this.origine = origine;
        this.tagetAccount = tagetAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getAccount() {
        return account;
    }

    public void setAccount(UUID account) {
        this.account = account;
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

    public UUID getTagetAccount() {
        return tagetAccount;
    }

    public void setTagetAccount(UUID tagetAccount) {
        this.tagetAccount = tagetAccount;
    }
}
