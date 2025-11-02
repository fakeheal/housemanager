package edu.nbu.entities;

import edu.nbu.converters.YearMonthConverter;
import edu.nbu.enums.FeeStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.YearMonth;

@Entity
public class Fee implements WorksWithMoney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Apartment apartment;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FeeStatus status;

    @Column(columnDefinition = "VARCHAR(7)")
    @Convert(converter = YearMonthConverter.class)
    private YearMonth period;

    @NotNull
    private Integer amount;

    private Instant paidOn;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public FeeStatus getStatus() {
        return status;
    }

    public void setStatus(FeeStatus status) {
        this.status = status;
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public Float getAmount() {
        return convertToLeva(amount);
    }

    public Integer getAmountRaw() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Instant getPaidOn() {
        return paidOn;
    }

    public void setPaidOn(Instant paidOn) {
        this.paidOn = paidOn;
    }
}
