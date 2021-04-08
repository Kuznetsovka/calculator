package ru.systemairac.calculator.domain.humidifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Brand;
import ru.systemairac.calculator.domain.Image;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "vapor_distributor")
public class VaporDistributor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Brand brand;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Image image;


    @Column(unique = true)
    private String articleNumber;

    private int length;

    private int diameter;

    private BigDecimal price;
}
