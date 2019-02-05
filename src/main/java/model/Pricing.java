package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by Jakub Filipiak on 01.02.2019.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pricing {

    private String name;
    private String number;
    private LocalDate preparationDate;
    private LocalDate validityDate;

    private double netCost;
    private double taxCost;
    private double totalCost;
    private int totalAmountOfHours;

    private String summary;
    private String comment;
}
