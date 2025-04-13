package com.company.librarymanagement.fee.entity;

import com.company.librarymanagement.shared.auditable.UserAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "fee")
public class Fee extends UserAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_seq_gen")
    @SequenceGenerator(sequenceName = "fee_seq", allocationSize = 1, name = "fee_seq_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "fee_value")
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
