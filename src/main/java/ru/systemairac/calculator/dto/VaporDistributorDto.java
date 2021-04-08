package ru.systemairac.calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.systemairac.calculator.domain.Image;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VaporDistributorDto {
    private Long id;
    private Image image;
    private String articleNumber;
    private String title;
    private int length;
    private int diameter;
    private BigDecimal price;

    public void setTitle(String title) {
        this.title = "Парораспределитель из нержавеющей стали, d=" + this.diameter + "мм, L=" + this.length + "мм";
    }
}