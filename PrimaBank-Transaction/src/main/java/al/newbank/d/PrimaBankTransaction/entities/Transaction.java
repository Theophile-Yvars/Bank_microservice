package al.newbank.d.PrimaBankTransaction.entities;


import jakarta.persistence.*;
import java.util.Objects;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Table
public class Transaction {

    @PrimaryKey
    private Long id;
    @Column
    private String name;
    @Column
    private Long client;
    @Column
    private float price;
    @Column
    private String country; // france, suisse, wakanda
    @Column
    private String type; // entreprise ou particulier
    @Column
    private String origine; // internet ou direct
    @Column
    private Long tagetClient;

    public Transaction(String name, Long client, float price, String country, String type, String origine, Long tagetClient) {
        this.id = System.currentTimeMillis();
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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
        return Float.compare(that.price, price) == 0 && Objects.equals(id, that.id) && name.equals(that.name) && client.equals(that.client) && country.equals(that.country) && type.equals(that.type) && origine.equals(that.origine) && tagetClient.equals(that.tagetClient);
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