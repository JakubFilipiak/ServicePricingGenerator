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
public class Client {

    private String name;
    private String address;
    private String email;
    private String mobile;
    private String taxNumber;
}
