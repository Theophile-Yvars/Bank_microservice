package primabankclient.primabankclient.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity(name = "accounts")
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private Long creditCardNumber;
    private double amount;
    private String clientName;

    public Account() {
    }

    public Account(Long clientId, String clientName) {
        this.clientId = clientId;
        this.creditCardNumber = (long) (Math.random() * 10000000000000000L);
        this.amount = 0.0;
        this.clientName = clientName;
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCreditCardNumber() {
        return creditCardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setCreditCardNumber(Long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    


    
}
