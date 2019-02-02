package model;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Jakub Filipiak on 01.02.2019.
 */
@Builder
@Data
public class Service {
    private String name;
    private int amountOfHours;
    private int unitCost;
    private int cost;
}
