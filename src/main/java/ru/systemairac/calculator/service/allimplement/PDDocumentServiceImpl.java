package ru.systemairac.calculator.service.allimplement;

import be.quodlibet.boxable.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import ru.systemairac.calculator.domain.humidifier.HumidifierComponent;
import ru.systemairac.calculator.domain.humidifier.VaporDistributor;
import ru.systemairac.calculator.dto.EstimateDto;
import ru.systemairac.calculator.dto.ProjectDto;
import ru.systemairac.calculator.dto.TechDataDto;
import ru.systemairac.calculator.dto.UserDto;
import ru.systemairac.calculator.service.allinterface.PDDocumentService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class PDDocumentServiceImpl implements PDDocumentService {

    private static final float fontSizeSmall = 10;
    private static final float fontSizeNormal = 12;
    private static final float fontSizeBig = 14;

    private static final float marginBetweenTables = 20;

    private static final String PATH_TO_FONT_FILE = "src/main/resources/arial.ttf"; // TODO: путь к файлу шрифта


    // TODO: refactor
    @Override
    public PDDocument toPDDocument(
            UserDto userDto,
            ProjectDto projectDto,
            TechDataDto techDataDto,
            EstimateDto estimateDto
    ) throws IOException {

        PDPage myPage = new PDPage(PDRectangle.A4);
        PDDocument mainDocument = new PDDocument();
        PDFont font = PDType0Font.load(mainDocument, new File(PATH_TO_FONT_FILE));
        mainDocument.addPage(myPage);
        PDPageContentStream contentStream = new PDPageContentStream(mainDocument, myPage);
        contentStream.setFont(font, 22);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("Проект");
        contentStream.endText();

        float margin = 50;
        float yStartNewPage = myPage.getMediaBox().getHeight() - (2 * margin);
        float tableWidth = myPage.getMediaBox().getWidth() - (2 * margin);

        boolean drawContent = true;
        float bottomMargin = 70;
        float yPosition = 720;

        Row<PDPage> row;
        Cell<PDPage> cell;

        String name = null;
        String[][] data = {
                {"Наименование проекта: " + projectDto.getTitle()},
                {"Адрес проекта: " + projectDto.getAddress()}
        };

        BaseTable table = new BaseTable( yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPosition -= table.getHeaderAndDataHeight() + marginBetweenTables;



        name = "Заказчик/клиент";
        data = new String[][] {
                {"Компания: " + userDto.getNameCompany()},
                {"Имя: " + userDto.getFullName()},
                {"Телефон: " + userDto.getPhone()},
                {"email: " + userDto.getEmail()}
        };

        table = new BaseTable( yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPosition -= table.getHeaderAndDataHeight() + marginBetweenTables;


        name = null;
        data = new String[][] {
                {"", "Вход", "Выход"},
                {"Температура (°C)", String.format("%.1f", techDataDto.getTempIn()), String.format("%.1f", techDataDto.getTempIn())},
                {"RH (%)", String.format("%.0f", techDataDto.getHumIn()), String.format("%.0f", techDataDto.getHumOut())}
        };

        table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, drawContent);
        drawTable(table, font, name, data, new float[]{30, 20, 20}, HorizontalAlignment.CENTER);
        yPosition -= table.getHeaderAndDataHeight() + marginBetweenTables;


        name = "Парораспределение";
        data = new String[][]{
                {"Парораспределитель в:", techDataDto.getTypeMontage().getTxt()},
                {"Ширина (мм)", String.valueOf(techDataDto.getWidth())},
                {"Высота (мм)", String.valueOf(techDataDto.getHeight())}
        };

        float yPositionLeft = yPosition;
        float yPositionRight = yPosition;
        table = new BaseTable( yPositionLeft, yStartNewPage, bottomMargin, tableWidth / 2f, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPositionLeft -= table.getHeaderAndDataHeight() + marginBetweenTables;
        Double velocity = Double.valueOf(techDataDto.getAirFlow())/ (Double.valueOf(techDataDto.getWidth())/1000 * Double.valueOf(techDataDto.getHeight())/1000)/3600;
        name = "Информация по увлажнению";
        data = new String[][]{
                {"Температура увлажнения (°С)", String.format("%.1f", techDataDto.getTempIn())},
                {"Суммарный расход воздуха (м³/ч)", String.format("%d", techDataDto.getAirFlow())},
                {"Скорость воздуха (м/с)",  String.format("%.2f",velocity)},
                {"Нагрузка по пару (кг/ч)", String.format("%.1f", techDataDto.getCalcCapacity())},
        };

        table = new BaseTable( yPositionRight, yStartNewPage, bottomMargin, tableWidth / 2f, margin + table.getWidth(), mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPositionRight -= table.getHeaderAndDataHeight() + marginBetweenTables;

        name = "Подбор увлажнителя";
        data = new String[][]{
                {"Тип увлажнения", techDataDto.getEnumHumidifierType().getTxt()},
                {"Электропитание", techDataDto.getVoltage().getTxt() + "V"}

        };

        table = new BaseTable( yPositionLeft, yStartNewPage, bottomMargin, tableWidth / 2f, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data);
        yPositionLeft -= table.getHeaderAndDataHeight() + marginBetweenTables;

        yPosition = Math.min(yPositionLeft, yPositionRight);

        name = "Результаты расчета увлажнения";
        String vaporTitle = String.format("%d %s ø:%dmm L:%dmm",
                estimateDto.getHumidifier().getNumberOfCylinders(), // число дисперсионных трубок
                "Дисперсионная трубка", // разные данные для разных чисел
                estimateDto.getHumidifier().getVaporPipeDiameter(), // диаметр трубок
                techDataDto.getWidth());
        data = new String[][]{
                { "Тип увлажнения",
                        String.format("1 x %s, %.1f kg/h, %sV %.2f kW",
                                estimateDto.getHumidifier().getTitle(),
                                estimateDto.getHumidifier().getCapacity(),
                                estimateDto.getHumidifier().getVoltage().getTxt(),
                                estimateDto.getHumidifier().getElectricPower()
                        )
                },
                { "Тип распыления",vaporTitle }
        };

        table = new BaseTable( yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, false, true);

        drawTable(table, font, name, data, new float[]{40f, 60f});
        yPosition -= table.getHeaderAndDataHeight() + marginBetweenTables;
        Path path = Path.of(estimateDto.getHumidifier().getImage().getLink());
        path.getFileName();
        contentStream.drawImage(
                PDImageXObject.createFromFile(
                        "src/main/resources/static/img/humidifiers/"+ path.getFileName(),
                        mainDocument
                ),
                450,
                720,
                100,
                100
        );

        contentStream.drawImage(
                PDImageXObject.createFromFile(
                        "src/main/resources/static/img/logo.png",
                        mainDocument
                ),
                200,
                780
        );

        contentStream.close();

        myPage = new PDPage(PDRectangle.A4);
        mainDocument.addPage(myPage);
        contentStream = new PDPageContentStream(mainDocument, myPage);


        yPosition = 650;

        name = "Смета";

        List<String[]> prices = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        Integer numberOfCylinders = estimateDto.getHumidifier().getNumberOfCylinders();
        total = total.add(estimateDto.getHumidifier().getPrice());
        prices.add(new String[] {"Артикул","Наименование товара","Количество", "Цена"});
        prices.add(new String[]
                {
                        estimateDto.getHumidifier().getArticleNumber(),
                        estimateDto.getHumidifier().getTitle(),
                        "1",
                        estimateDto.getHumidifier().getPrice().toString()  + " €"
                });
        VaporDistributor vaporDistributor = estimateDto.getVaporDistributor();
        if (vaporDistributor != null) {
            prices.add(new String[]
                    {
                            vaporDistributor.getArticleNumber(),
                            vaporTitle,
                            numberOfCylinders.toString(),
                            vaporDistributor.getPrice().toString()  + " €"
                    });
            total = total.add(vaporDistributor.getPrice().multiply(new BigDecimal(numberOfCylinders)));
        }
        for (HumidifierComponent component : estimateDto.getHumidifierComponents()) {
            total = total.add(component.getPrice());
            prices.add(new String[]
                    {
                            component.getArticleNumber(),
                            component.getType().getTxt(),
                            "1",
                            component.getPrice().toString()  + " €"
                    });
        }

        data = prices.toArray(new String[0][0]);

        table = new BaseTable( yPosition, yStartNewPage, bottomMargin, tableWidth, margin, mainDocument, myPage, true, true);

        row = table.createRow(12);
        cell = row.createCell(100, name);
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.CENTER);
        for (int i = 0; i < data.length; i++) {
            row = table.createRow(0);
            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(100f / data[i].length, data[i][j]);
                cell.setFont(font);
                cell.setFontSize(fontSizeNormal);
                if (j < data[i].length - 1)
                    cell.setAlign(HorizontalAlignment.LEFT);
                else
                    cell.setAlign(HorizontalAlignment.RIGHT);
                cell.setValign(VerticalAlignment.MIDDLE);
            }
        }
        row = table.createRow(12);
        cell = row.createCell(75f, "Итого");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.RIGHT);
        cell.setValign(VerticalAlignment.MIDDLE);
        cell = row.createCell(25f, total + " €");
        cell.setFont(font);
        cell.setFontSize(fontSizeNormal);
        cell.setAlign(HorizontalAlignment.RIGHT);
        cell.setValign(VerticalAlignment.MIDDLE);

        table.draw();
        yPosition -= table.getHeaderAndDataHeight() + marginBetweenTables;

        contentStream.close();

        return mainDocument;
    }

    private static void drawTable(BaseTable table, PDFont font, String name, String[][] data) throws IOException {
        float[] columnWidth = new float[data.length];
        for (int i = 0; i < data.length; i++) {
            columnWidth[i] = 100f / data[i].length;
        }
        drawTable(table, font, name, data, columnWidth);
    }

    private static void drawTable(BaseTable table, PDFont font, String name, String[][] data, float[] columnWidth) throws IOException {
        drawTable(table, font, name, data, columnWidth, HorizontalAlignment.LEFT);
    }

    private static void drawTable(BaseTable table, PDFont font, String name, String[][] data, float[] columnWidth, HorizontalAlignment horizontalAlignment) throws IOException {
        Row<PDPage> row;
        Cell<PDPage> cell;
        if (name != null) {
            row = table.createRow(12);
            cell = row.createCell(100, name);
            cell.setFont(font);
            cell.setFontSize(fontSizeNormal);
            cell.setAlign(HorizontalAlignment.CENTER);
        }
        for (int i = 0; i < data.length; i++) {
            row = table.createRow(0);
            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(columnWidth[j], data[i][j]);
                cell.setFont(font);
                cell.setFontSize(fontSizeNormal);
                cell.setAlign(horizontalAlignment);
                cell.setValign(VerticalAlignment.MIDDLE);
            }
        }

        table.draw();
    }



}


