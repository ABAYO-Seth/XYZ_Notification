package rw.gov.irembo.notification.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Subsciber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Subscriber plan is required")
    private String subscriberPlan;
    @NotNull(message = "Subscriber Name is required")
    private String name;
    private String subscriberKey;
    private LocalDate createDate= LocalDate.now();
    private int requestPerSecond;
    private int requestPerMonth;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private int request;
    
    
}
