package ru.t1.java.demo.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TimeLimitExceedLog {
    private String methodSignature;
    private long executionTime;
}
