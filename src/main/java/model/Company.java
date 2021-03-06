package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Created by Jakub Filipiak on 01.02.2019.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private String name;
    private String address;
    private String email;
    private String website;
    private String mobile;
    private String taxNumber;
    File logoFile;
}
