package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
// @AllArgsConstructor
@Entity
@Table(name = "tbl_drainage_cooling_kits")
public class DrainageCoolingKit extends ArticledEntity {

}