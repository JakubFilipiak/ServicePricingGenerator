package model;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.image.Image;
import be.quodlibet.boxable.line.LineStyle;
import be.quodlibet.boxable.utils.PDStreamUtils;
import javafx.collections.ObservableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jakub Filipiak on 04.02.2019.
 */
@RequiredArgsConstructor
public class PdfDrawer {

    @NonNull
    Company company;
    @NonNull
    Client client;
    @NonNull
    Author author;
    @NonNull
    Pricing pricing;
    @NonNull
    ObservableList<Service> services;

    private PDDocument document = new PDDocument();
    private PDPage blankPage = new PDPage();
    private PDPage page = new PDPage();
    private PDPageContentStream contentStream;
    private PDPageContentStream footerContentStream;
    private float pageWidth;
    private float pageHeight;
    private float xMargin = 50;
    private float yMargin = 40;
    private float xWorkspace;
    private float yWorkspace;
    private float xZeroFromTop;
    private float yZeroFromTop;
    private float yActualPositionLeftSide = 0;
    private float yActualPositionRightSide = 0;
    private float yPositionOfLastLeftComponent = 0;
    private float yPositionOfLastRightComponent = 0;

    private Color colorRed = Color.decode("#720d23");
    private Color colorBlue = Color.decode("#2c3a43");
    private Color colorBrightGrey = Color.decode("#f8f8f8");
    private Color colorDarkGrey = Color.decode("#d8d8d8");

    PDType0Font latoLightFont;
    PDType0Font latoLighItalicFont;
    PDType0Font latoMediumFont;

    public void importFonts() throws IOException {
        InputStream latoLightFontInputStream =
                PdfDrawer.class.getResourceAsStream("/Lato-Light.ttf");
        latoLightFont = PDType0Font.load(document, latoLightFontInputStream, true);
        latoLightFontInputStream.close();

        InputStream latoLightItalicFontInputStream =
                PdfDrawer.class.getResourceAsStream("/Lato-LightItalic.ttf");
        latoLighItalicFont = PDType0Font.load(document, latoLightItalicFontInputStream, true);
        latoLightItalicFontInputStream.close();

        InputStream latoMediumFontInputStream =
                PdfDrawer.class.getResourceAsStream("/Lato-Medium.ttf");
        latoMediumFont = PDType0Font.load(document, latoMediumFontInputStream, true);
        latoMediumFontInputStream.close();
    }

    public void initPage() throws IOException {
        document.addPage(blankPage);
        page = document.getPage(0);
        contentStream = new PDPageContentStream(document, page);
        pageWidth = page.getMediaBox().getWidth();
        pageHeight = page.getMediaBox().getHeight();
        xWorkspace = pageWidth - (2 * xMargin);
        yWorkspace = pageHeight - (2 * yMargin);
        xZeroFromTop = xMargin;
        yZeroFromTop = pageHeight - yMargin;
        yActualPositionLeftSide = pageHeight - yMargin;
        yPositionOfLastLeftComponent = yActualPositionLeftSide;
        yActualPositionRightSide = yActualPositionLeftSide;
    }

    public void closeStreamAndSaveFile(File pricingFile) throws IOException {
        footerContentStream.close();

        document.save(pricingFile);
        document.close();
    }

    public float getStringLenght(PDType0Font font, float fontSize, String string) throws IOException {
        float stringLenght;
        stringLenght = (font.getStringWidth(string) / 1000.0f) * fontSize;
        return stringLenght;
    }

    public void drawHeader() throws IOException {
        initPage();

        if(company.getLogoFile() != null) {

            Image logoImage = new Image(ImageIO.read(company.getLogoFile()));
            float logoWidth = 150;
            float logoHeight = 75;
            //logoImage = logoImage.scaleByWidth(logoWidth);
            logoImage = logoImage.scale(logoWidth, logoHeight);
            logoImage.draw(document, contentStream, xZeroFromTop, yZeroFromTop);
            yActualPositionLeftSide -= logoImage.getHeight();
            yActualPositionRightSide = yActualPositionLeftSide;
            yPositionOfLastLeftComponent = yActualPositionLeftSide;
        }

        PDStreamUtils.write(contentStream, "WYCENA", latoMediumFont, 20,
                xMargin + xWorkspace - getStringLenght(latoMediumFont, 20,
                        "WYCENA"), yActualPositionRightSide, colorRed);
        yActualPositionRightSide -= 40;

        if(!client.getName().isEmpty() || !client.getAddress().isEmpty() || !client.getEmail().isEmpty() || !client.getMobile().isEmpty() || !client.getTaxNumber().isEmpty()) {
            yActualPositionLeftSide -= 30;
            PDStreamUtils.write(contentStream, "PRZYGOTOWANA DLA:",
                    latoMediumFont, 12, xMargin,
                    yActualPositionLeftSide, colorRed);
            yActualPositionLeftSide -= 25;
        }

        if(!pricing.getPreparationDate().toString().isEmpty()) {

            PDStreamUtils.write(contentStream, pricing.getPreparationDate().toString(),
                    latoLightFont, 10,
                    xMargin + xWorkspace - getStringLenght(latoLightFont, 10,
                            pricing.getPreparationDate().toString()),
                    yActualPositionRightSide, Color.BLACK);

            PDStreamUtils.write(contentStream, "DATA:", latoMediumFont, 10,
                    xMargin + xWorkspace - 70 - getStringLenght(latoMediumFont, 10,
                            "DATA:"), yActualPositionRightSide, colorBlue);

            yActualPositionRightSide -= 15;
        }

        if(!pricing.getValidityDate().toString().isEmpty()) {

            PDStreamUtils.write(contentStream, pricing.getValidityDate().toString(),
                    latoLightFont, 10,
                    xMargin + xWorkspace - getStringLenght(latoLightFont, 10,
                            pricing.getValidityDate().toString()),
                    yActualPositionRightSide, Color.BLACK);

            PDStreamUtils.write(contentStream, "WAŻNA DO:", latoMediumFont, 10,
                    xMargin + xWorkspace - 70 - getStringLenght(latoMediumFont, 10,
                            "WAŻNA DO:"), yActualPositionRightSide, colorBlue);

            yPositionOfLastRightComponent = yActualPositionRightSide;
            yActualPositionRightSide -= 15;
        }

        if(!pricing.getNumber().isEmpty()) {

            PDStreamUtils.write(contentStream, pricing.getNumber(),
                    latoLightFont, 10,
                    xMargin + xWorkspace - getStringLenght(latoLightFont, 10,
                            pricing.getNumber()),
                    yActualPositionRightSide, Color.BLACK);

            PDStreamUtils.write(contentStream, "NUMER:", latoMediumFont, 10,
                    xMargin + xWorkspace - 70 - getStringLenght(latoMediumFont, 10,
                            "NUMER:"), yActualPositionRightSide, colorBlue);

            yPositionOfLastRightComponent = yActualPositionRightSide;
            yActualPositionRightSide -= 15;
        }

        if(!client.getName().isEmpty()) {

            PDStreamUtils.write(contentStream, client.getName(),
                    latoMediumFont, 15, xMargin,
                    yActualPositionLeftSide,
                    colorBlue);

            yPositionOfLastLeftComponent = yActualPositionLeftSide;
            yActualPositionLeftSide -= 25;
        }

        if(!client.getAddress().isEmpty()) {

            PDStreamUtils.write(contentStream, client.getAddress(),
                    latoLightFont, 10, xMargin, yActualPositionLeftSide, Color.BLACK);

            yPositionOfLastLeftComponent = yActualPositionLeftSide;
            yActualPositionLeftSide -= 15;
        }

        if(!client.getEmail().isEmpty()) {

            PDStreamUtils.write(contentStream, client.getEmail(),
                    latoLightFont, 10, xMargin, yActualPositionLeftSide, Color.BLACK);

            yPositionOfLastLeftComponent = yActualPositionLeftSide;
            yActualPositionLeftSide -= 15;
        }

        if(!client.getMobile().isEmpty()) {

            PDStreamUtils.write(contentStream, "Tel.: " + client.getMobile(),
                    latoLightFont, 10, xMargin, yActualPositionLeftSide,
                    Color.BLACK);

            yPositionOfLastLeftComponent = yActualPositionLeftSide;
            yActualPositionLeftSide -= 15;
        }

        if(!client.getTaxNumber().isEmpty()) {

            PDStreamUtils.write(contentStream, "NIP: " + client.getTaxNumber(),
                    latoLightFont, 10, xMargin, yActualPositionLeftSide,
                    Color.BLACK);

            yPositionOfLastLeftComponent = yActualPositionLeftSide;
            yActualPositionLeftSide -= 15;
        }

        if(!author.getName().isEmpty() && !author.getEmail().isEmpty()) {

            if(yActualPositionLeftSide > yActualPositionRightSide) {
                yActualPositionLeftSide = yActualPositionRightSide;
            }
            yActualPositionLeftSide -= 5;

            PDStreamUtils.write(contentStream,
                    "Wycena została przygotowana przez: " + author.getName() + " <" + author.getEmail() + ">",
                    latoLighItalicFont, 10, xMargin, yActualPositionLeftSide,
                    Color.BLACK);
            yPositionOfLastLeftComponent = yActualPositionLeftSide;
        } else if(!author.getName().isEmpty() && author.getEmail().isEmpty()) {

            if(yActualPositionLeftSide > yActualPositionRightSide) {
                yActualPositionLeftSide = yActualPositionRightSide;
            }
            yActualPositionLeftSide -= 5;

            PDStreamUtils.write(contentStream,
                    "Wycena została przygotowana przez: " + author.getName(),
                    latoLighItalicFont, 10, xMargin, yActualPositionLeftSide,
                    Color.BLACK);
            yPositionOfLastLeftComponent = yActualPositionLeftSide;
        } else if(author.getName().isEmpty() && !author.getEmail().isEmpty()) {

            if(yActualPositionLeftSide > yActualPositionRightSide) {
                yActualPositionLeftSide = yActualPositionRightSide;
            }
            yActualPositionLeftSide -= 5;

            PDStreamUtils.write(contentStream,
                    "Wycena została przygotowana przez: " + "<" + author.getEmail() + ">",
                    latoLighItalicFont, 10, xMargin, yActualPositionLeftSide,
                    Color.BLACK);
            yPositionOfLastLeftComponent = yActualPositionLeftSide;
        }
    }


    public void drawTable() throws IOException {

        System.out.println(yPositionOfLastLeftComponent);
        System.out.println(yPositionOfLastRightComponent);
        if(yPositionOfLastLeftComponent < yPositionOfLastRightComponent) {
            yActualPositionLeftSide = yPositionOfLastLeftComponent - 30;
        } else {
            yActualPositionLeftSide = yPositionOfLastRightComponent - 30;
        }




        float yStartNewPage = page.getMediaBox().getHeight() - (2 * yMargin);
        float tableWidth = page.getMediaBox().getWidth() - (2 * xMargin);
        BaseTable table = new BaseTable(yActualPositionLeftSide,
                yStartNewPage, yMargin, tableWidth, xMargin, document, page, true,
                true);

        Color rowFillColor;
        rowFillColor = colorBlue;

        Cell<PDPage> headerCell;
        Row<PDPage> headerRow = table.createRow(10);
        headerCell = headerRow.createCell(62, "Nazwa usługi");
        headerCell.setFont(latoMediumFont);
        headerCell.setTextColor(Color.WHITE);
        headerCell.setFillColor(rowFillColor);
        headerCell.setAlign(HorizontalAlignment.CENTER);
        headerCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        headerCell = headerRow.createCell(10, "Ilość");
        headerCell.setFont(latoMediumFont);
        headerCell.setTextColor(Color.WHITE);
        headerCell.setFillColor(rowFillColor);
        headerCell.setAlign(HorizontalAlignment.CENTER);
        headerCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        headerCell = headerRow.createCell(13, "Koszt jednost.");
        headerCell.setFont(latoMediumFont);
        headerCell.setTextColor(Color.WHITE);
        headerCell.setFillColor(rowFillColor);
        headerCell.setAlign(HorizontalAlignment.CENTER);
        headerCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        headerCell = headerRow.createCell(15, "Suma");
        headerCell.setFont(latoMediumFont);
        headerCell.setTextColor(Color.WHITE);
        headerCell.setFillColor(rowFillColor);
        headerCell.setAlign(HorizontalAlignment.CENTER);
        headerCell.setBorderStyle(new LineStyle(Color.WHITE, 0));



        Cell<PDPage> cell;
        Row<PDPage> row;



        boolean iterationParity = true;



        for (Service tmpService : services){

            if (iterationParity) {
                iterationParity = false;
                rowFillColor = colorBrightGrey;
            } else {
                iterationParity = true;
                rowFillColor = colorDarkGrey;
            }

            row = table.createRow(10);
            cell = row.createCell(62, tmpService.getName());
            cell.setFont(latoLightFont);
            cell.setAlign(HorizontalAlignment.LEFT);
            cell.setFillColor(rowFillColor);
            cell.setBorderStyle(new LineStyle(Color.WHITE, 0));

            cell = row.createCell(10, tmpService.getAmountOfHours() + " godz.");
            cell.setFont(latoLightFont);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell.setFillColor(rowFillColor);
            cell.setBorderStyle(new LineStyle(Color.WHITE, 0));

            cell = row.createCell(13, tmpService.getUnitCost() + " PLN");
            cell.setFont(latoLightFont);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell.setFillColor(rowFillColor);
            cell.setBorderStyle(new LineStyle(Color.WHITE, 0));

            cell = row.createCell(15, tmpService.getCost() + " PLN");
            cell.setFont(latoLightFont);
            cell.setAlign(HorizontalAlignment.CENTER);
            cell.setFillColor(rowFillColor);
            cell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        }

        Cell<PDPage> summaryCell;
        Row<PDPage> summaryRow;

        rowFillColor = colorRed;

        summaryRow = table.createRow(10);

        if(pricing.getSummary().isEmpty()) {
            summaryCell = summaryRow.createCell(62, "Podsumowanie");
            summaryCell.setFont(latoMediumFont);
            summaryCell.setTextColor(Color.WHITE);
            summaryCell.setFillColor(rowFillColor);
            summaryCell.setAlign(HorizontalAlignment.LEFT);
            summaryCell.setBorderStyle(new LineStyle(Color.WHITE, 0));
        } else {
            summaryCell = summaryRow.createCell(62, pricing.getSummary() + " - " +
                    "podsumowanie");
            summaryCell.setFont(latoMediumFont);
            summaryCell.setTextColor(Color.WHITE);
            summaryCell.setFillColor(rowFillColor);
            summaryCell.setAlign(HorizontalAlignment.LEFT);
            summaryCell.setBorderStyle(new LineStyle(Color.WHITE, 0));
        }

        summaryCell = summaryRow.createCell(10, pricing.getTotalAmountOfHours() +
                " godz.");
        summaryCell.setFont(latoMediumFont);
        summaryCell.setTextColor(Color.WHITE);
        summaryCell.setFillColor(rowFillColor);
        summaryCell.setAlign(HorizontalAlignment.CENTER);
        summaryCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        summaryCell = summaryRow.createCell(13, "");
        summaryCell.setTextColor(Color.WHITE);
        summaryCell.setFillColor(rowFillColor);
        summaryCell.setAlign(HorizontalAlignment.CENTER);
        summaryCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        summaryCell = summaryRow.createCell(15, String.format("%.2f PLN",
                pricing.getNetCost()));
        summaryCell.setFont(latoMediumFont);
        summaryCell.setTextColor(Color.WHITE);
        summaryCell.setFillColor(rowFillColor);
        summaryCell.setAlign(HorizontalAlignment.CENTER);
        summaryCell.setBorderStyle(new LineStyle(Color.WHITE, 0));


        //
        Cell<PDPage> netCostCell;
        Row<PDPage> netCostRow;

        netCostRow = table.createRow(10);

        netCostCell = netCostRow.createCell(62, "");
        netCostCell.setFillColor(Color.WHITE);
        netCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));
        netCostCell = netCostRow.createCell(10, "");
        netCostCell.setFillColor(Color.WHITE);
        netCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        netCostCell = netCostRow.createCell(13, "SUMA NETTO:");
        netCostCell.setFont(latoMediumFont);
        netCostCell.setFillColor(colorBrightGrey);
        netCostCell.setAlign(HorizontalAlignment.RIGHT);
        netCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        netCostCell = netCostRow.createCell(15, String.format("%.2f " +
                        "PLN", pricing.getNetCost()));
        netCostCell.setFont(latoMediumFont);
        netCostCell.setFillColor(colorBrightGrey);
        netCostCell.setAlign(HorizontalAlignment.CENTER);
        netCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));


        Cell<PDPage> taxCostCell;
        Row<PDPage> taxCostRow;

        taxCostRow = table.createRow(10);

        taxCostCell = taxCostRow.createCell(62, "");
        taxCostCell.setFillColor(Color.WHITE);
        taxCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));
        taxCostCell = taxCostRow.createCell(10, "");
        taxCostCell.setFillColor(Color.WHITE);
        taxCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        taxCostCell = taxCostRow.createCell(13, "VAT 23%:");
        taxCostCell.setFont(latoMediumFont);
        taxCostCell.setFillColor(colorDarkGrey);
        taxCostCell.setAlign(HorizontalAlignment.RIGHT);
        taxCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        taxCostCell = taxCostRow.createCell(15, String.format("%.2f " +
                        "PLN", pricing.getTaxCost()));
        taxCostCell.setFont(latoMediumFont);
        taxCostCell.setFillColor(colorDarkGrey);
        taxCostCell.setAlign(HorizontalAlignment.CENTER);
        taxCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));


        Cell<PDPage> totalCostCell;
        Row<PDPage> totalCostRow;

        totalCostRow = table.createRow(10);

        totalCostCell = totalCostRow.createCell(62, "");
        totalCostCell.setFillColor(Color.WHITE);
        totalCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));
        totalCostCell = totalCostRow.createCell(10, "");
        totalCostCell.setFillColor(Color.WHITE);
        totalCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        totalCostCell = totalCostRow.createCell(13, "CAŁKOWITY KOSZT:");
        totalCostCell.setFont(latoMediumFont);
        totalCostCell.setTextColor(Color.WHITE);
        totalCostCell.setFillColor(colorBlue);
        totalCostCell.setAlign(HorizontalAlignment.RIGHT);
        totalCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));

        totalCostCell = totalCostRow.createCell(15, String.format("%.2f " +
                "PLN", pricing.getTotalCost()));
        totalCostCell.setFont(latoMediumFont);
        totalCostCell.setTextColor(Color.WHITE);
        totalCostCell.setFillColor(colorBlue);
        totalCostCell.setAlign(HorizontalAlignment.CENTER);
        totalCostCell.setValign(VerticalAlignment.MIDDLE);
        totalCostCell.setBorderStyle(new LineStyle(Color.WHITE, 0));


        float tableHeight = table.getHeaderAndDataHeight();
        System.out.println("table height: " + tableHeight);
        yActualPositionLeftSide -= tableHeight;
        table.draw();

    }

    public void drawFooter() throws IOException {

        int numberOfPages = document.getNumberOfPages();
        System.out.println("number of pages: " + numberOfPages);


        float yActualPositionLeftSideTmp = yActualPositionLeftSide;


        if(numberOfPages == 1) {
            footerContentStream = contentStream;
            if(yActualPositionLeftSide < (yMargin + 90)) {
                contentStream.close();
                document.addPage(new PDPage());
                System.out.println("Number of pages after adding a blank page" + document.getNumberOfPages());
                footerContentStream = new PDPageContentStream(document,
                        document.getPage(1), true,
                        false, false);
                yActualPositionLeftSide = yZeroFromTop + 15;
            }
        } else if (numberOfPages > 1){
            yActualPositionLeftSide =
                    yZeroFromTop - (2 * yMargin) - (yActualPositionLeftSideTmp * (-1));
            yActualPositionLeftSide -= 15;
            contentStream.close();
            footerContentStream = new PDPageContentStream(document,
                    document.getPage(numberOfPages - 1), true, false, false);
            System.out.println("new page yActualPositionLeftSide: " + yActualPositionLeftSide);

        }


        yActualPositionLeftSide -= 15;
        PDStreamUtils.write(footerContentStream, "W celu zaakceptowania wyceny, prosimy " +
                        "o wydrukowanie, podpisanie i zwrot niniejszego arkusza.",
                latoLightFont, 10, xMargin, yActualPositionLeftSide,
                Color.BLACK);
        yActualPositionLeftSide -= 30;

        PDStreamUtils.write(footerContentStream, "DZIĘKUJEMY ZA ZAINTERESOWANIE NASZYMI " +
                        "USŁUGAMI!",
                latoLightFont, 10, xMargin, yActualPositionLeftSide,
                colorBlue);
        yActualPositionLeftSide -= 15;

        //footerContentStream.drawLine(xMargin, yActualPositionLeftSide,
        //        xMargin + xWorkspace,
         //      yActualPositionLeftSide);
        footerContentStream.setStrokingColor(colorBlue);
        footerContentStream.setLineWidth(1);
        footerContentStream.moveTo(xMargin, yActualPositionLeftSide);
        footerContentStream.lineTo(xMargin + xWorkspace, yActualPositionLeftSide);
        footerContentStream.stroke();
        yActualPositionLeftSide -= 5;

        float iconsHeight = 10;

        InputStream addressIconInputStream =
                PdfDrawer.class.getResourceAsStream("/address_icon.png");
        Image addressIcon = new Image(ImageIO.read(addressIconInputStream));
        addressIconInputStream.close();
        addressIcon = addressIcon.scaleByHeight(iconsHeight);
        addressIcon.draw(document, footerContentStream, xMargin, yActualPositionLeftSide);

        PDStreamUtils.write(footerContentStream, company.getAddress(),
                latoLightFont, 10, xMargin + 30, yActualPositionLeftSide,
                Color.BLACK);

        InputStream emailIconInputStream =
                PdfDrawer.class.getResourceAsStream("/email_icon.png");
        Image emailIcon = new Image(ImageIO.read(emailIconInputStream));
        emailIconInputStream.close();
        emailIcon = emailIcon.scaleByHeight(iconsHeight);
        emailIcon.draw(document, footerContentStream, 370,
                yActualPositionLeftSide);

        PDStreamUtils.write(footerContentStream, company.getEmail(),
                latoLightFont, 10, 400, yActualPositionLeftSide,
                Color.BLACK);

        yActualPositionLeftSide -= addressIcon.getHeight() + 5;

        InputStream contactIconInputStream =
                PdfDrawer.class.getResourceAsStream("/contact_icon.png");
        Image contactIcon = new Image(ImageIO.read(contactIconInputStream));
        contactIconInputStream.close();
        contactIcon = contactIcon.scaleByHeight(iconsHeight);
        contactIcon.draw(document, footerContentStream, xMargin, yActualPositionLeftSide);

        PDStreamUtils.write(footerContentStream, company.getMobile(),
                latoLightFont, 10, xMargin + 30, yActualPositionLeftSide,
                Color.BLACK);

        InputStream websiteIconInputStream =
                PdfDrawer.class.getResourceAsStream("/website_icon.png");
        Image websiteIcon = new Image(ImageIO.read(websiteIconInputStream));
        websiteIcon = websiteIcon.scaleByHeight(iconsHeight);
        websiteIcon.draw(document, footerContentStream, 370,
                yActualPositionLeftSide);


        PDStreamUtils.write(footerContentStream, company.getWebsite(),
                latoLightFont, 10, 400, yActualPositionLeftSide,
                Color.BLACK);

        yActualPositionLeftSide -= contactIcon.getHeight();

        }
}
