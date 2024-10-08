package ru.t1.java.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Account {
    @Column(name = "client_id")
    private Long clientId;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private double balance;
}

