package model;

import java.io.File;

/**
 * Created by Jakub Filipiak on 04.02.2019.
 */
public class PdfDrawer {

    PdfDrawer(Company company) {

        String name = company.getName();
        String address = company.getAddress();
        String email = company.getEmail();
        String website = company.getWebsite();
        String mobile = company.getMobile();
        String taxNumber = company.getTaxNumber();
        File logoFile = company.getLogoFile();
    }
}
