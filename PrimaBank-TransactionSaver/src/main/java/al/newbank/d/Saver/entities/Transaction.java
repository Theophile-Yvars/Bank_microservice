package al.newbank.d.Saver.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;


@Entity
public class Transaction {

    @Id
    @GeneratedValue()
    private Long id;
    private String name;
    private Long client;
    private double price;
    private String country; // france, suisse, wakanda
    private String type; // entreprise ou particulier
    private String origine; // internet ou direct
    private Long tagetClient;

    public Transaction(String name, Long client, double price, String country, String type, String origine, Long tagetClient) {
        this.name = name;
        this.client = client;
        this.price = price;
        this.country = country;
        this.type = type;
        this.origine = origine;
        this.tagetClient = tagetClient;
    }

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }


    public Long getClient() {
        return client;
    }

    public void setClient(Long account) {
        this.client = account;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry( String country) {
        this.country = country;
    }


    public String getType() {
        return type;
    }

    public void setType( String type) {
        this.type = type;
    }


    public String getOrigine() {
        return origine;
    }

    public void setOrigine( String origine) {
        this.origine = origine;
    }


    public Long getTagetClient() {
        return tagetClient;
    }

    public void setTagetClient(Long tagetAccount) {
        this.tagetClient = tagetAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(id, that.id) && name.equals(that.name) && client.equals(that.client) && country.equals(that.country) && type.equals(that.type) && origine.equals(that.origine) && tagetClient.equals(that.tagetClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, client, price, country, type, origine, tagetClient);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account=" + client +
                ", price=" + price +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", origine='" + origine + '\'' +
                ", tagetAccount=" + tagetClient +
                '}';
    }
}