package org.pb.model.client;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.pb.model.order.Order;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name_ua")
    private String firstNameUa;
    @Column(name = "last_name_ua")
    private String lastNameUa;
    @Column(name = "first_name_en")
    private String firstNameEn;
    @Column(name = "last_name_en")
    private String lastNameEn;
    @Column(name = "birth")
    private LocalDate birth;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "operator_full_name")
    private String operatorFullName;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
