package ru.systemairac.calculator.domain.humidifier;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "tbl_master_slave_panels")
public class MasterSlavePanel extends ArticledEntity {

}
