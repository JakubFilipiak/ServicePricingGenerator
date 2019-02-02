package model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by Jakub Filipiak on 01.02.2019.
 */
@Builder
@Data
public class Pricing {
    private Date prepaationDate;
    private Date validityDate;
    private int id;

    private double netCost;
    private int taxRate;
    private double totalCost;
    private int totalAmountOfHours;
}
