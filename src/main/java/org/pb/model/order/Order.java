package org.pb.model.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.pb.model.client.Client;
import org.pb.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created")
    private LocalDateTime created;
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "card_number")
    private String cardNumber;
    @ManyToOne
    @JoinColumn(name = "fk_operator_id")
    private User operator;
    @ManyToOne
    @JoinColumn(name = "fk_client_id")
    private Client client;
}
