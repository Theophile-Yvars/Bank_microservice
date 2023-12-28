package al.newbank.d.Marketing.dto;

import java.util.Objects;

public class TransactionDTO {
    private String name;
    private Long account;
    private double price;
    private String country;
    private String type;
    private String origine;
    private Long tagetAccount;

    public TransactionDTO() {
    }

    public TransactionDTO(String name, Long account, double price, String country, String type, String origine, Long tagetAccount) {
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

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
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

    public Long getTagetAccount() {
        return tagetAccount;
    }

    public void setTagetAccount(Long tagetAccount) {
        this.tagetAccount = tagetAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return account == that.account && Double.compare(price, that.price) == 0 && tagetAccount == that.tagetAccount && Objects.equals(name, that.name) && Objects.equals(country, that.country) && Objects.equals(type, that.type) && Objects.equals(origine, that.origine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, account, price, country, type, origine, tagetAccount);
    }

    @Override
    public String toString() {
        return "Marketing TransactionDTO{" +
                "name='" + name + '\'' +
                ", account=" + account +
                ", price=" + price +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", origine='" + origine + '\'' +
                ", tagetAccount=" + tagetAccount +
                '}';
    }
}
