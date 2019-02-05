package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jakub Filipiak on 01.02.2019.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {

    private String name;
    private int amountOfHours;
    private int unitCost;
    private int cost;
}
