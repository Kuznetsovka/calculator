package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "tbl_vapor_distributors")
public class VaporDistributor extends ArticledEntity {

    // TODO: int, float, double, BigDecimal?

    private int diameter;

    private int length;

}
