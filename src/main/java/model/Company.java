package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.File;

/**
 * Created by Jakub Filipiak on 01.02.2019.
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String address;
    private String email;
    private String website;
    private String mobile;
    private String taxNumber;
    File logoFile;
}
