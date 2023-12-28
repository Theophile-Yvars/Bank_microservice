package primabankclient.primabankclient.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


public class Analytics {


    Long id ;


    Long clientId;

    public Analytics(Long clientId) {
        this.clientId = clientId;
    }

    public Analytics() {

    }
}