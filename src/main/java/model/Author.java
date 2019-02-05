package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jakub Filipiak on 03.02.2019.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private String name;
    private String email;
}
