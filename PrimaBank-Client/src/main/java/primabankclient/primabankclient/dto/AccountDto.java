package primabankclient.primabankclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class AccountDto {
    Long clientId;
    String clientName;
    double amount;
    Long creditCardNumber;

    public AccountDto() {
    }

    
    
}
