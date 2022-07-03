package at.technikum.tourplanner.service.report;

import at.technikum.tourplanner.config.AppConfiguration;
import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.statistics.StatisticsService;
import at.technikum.tourplanner.service.statistics.TourStats;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

import static at.technikum.tourplanner.util.TimeConverterUtil.convertToTimeString;


public class ReportServiceImpl implements ReportService {

    public static final float MARGIN = 10F;

    private final FileChooser fileChooser;
    private final AppConfiguration configReader;
    private final StatisticsService statisticsService;

    public ReportServiceImpl(FileChooser fileChooser, AppConfiguration appConfiguration, StatisticsService statisticsService) {
        this.fileChooser = fileChooser;
        this.configReader = appConfiguration;
        this.statisticsService = statisticsService;

        setUpInitialDirectory();
    }

    @Override
    public void generateTourReport(Tour tour, String imageUrl) {
        writeToPdfFile(document -> {
            try {
                document.add(generateDocumentHeader("Tour report"))
                        .add(generateTourTable(tour))
                        .add(getImage(imageUrl))
                        .add(generateLogsTable(tour.getLogs()));
            } catch (IOException e) {
                throw new FailedPdfGenerationException(e);
            }
        });
    }

    @Override
    public void generateSummarizeReport(List<Tour> tours) {
        writeToPdfFile(document -> {
            try {
                document.add(generateDocumentHeader("Summary report"))
                        .add(generateSummaryTable(tours));
            } catch (IOException e) {
                throw new FailedPdfGenerationException(e);
            }
        });
    }

    private void writeToPdfFile(Consumer<Document> consumer) {
        File file = fileChooser.showSaveDialog(new Stage());
        if (null == file) {
            return;
        }

        try (
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf)
        ) {
            consumer.accept(document);
        } catch (IOException e) {
            throw new FailedPdfGenerationException(e);
        }
    }

    private Image getImage(String imageUrl) throws MalformedURLException {
        return new Image(ImageDataFactory.create(imageUrl)).setMarginLeft(MARGIN);
    }

    private Table generateTourTable(Tour tour) {
        float[] pointColumnWidths = { 100F, 300F };
        Table table = new Table(pointColumnWidths)
                .setFontSize(12)
                .setBackgroundColor(ColorConstants.WHITE)
                .setMargin(MARGIN);

        table.addCell(generateHeaderCell("Tour name")).addCell(tour.getName());
        table.addCell(generateHeaderCell("From")).addCell(tour.getFrom());
        table.addCell(generateHeaderCell("To")).addCell(tour.getTo());
        table.addCell(generateHeaderCell("Transport type")).addCell(String.valueOf(tour.getTransportType()));
        table.addCell(generateHeaderCell("Distance")).addCell(String.valueOf(tour.getDistance()));
        table.addCell(generateHeaderCell("Estimated time")).addCell(convertToTimeString(tour.getEstimatedTime()));
        table.addCell(generateHeaderCell("Child-friendliness")).addCell(String.valueOf(tour.getChildFriendliness()));
        table.addCell(generateHeaderCell("Popularity")).addCell(String.valueOf(tour.getPopularity()));
        table.addCell(generateHeaderCell("Description")).addCell(tour.getDescription());

        return table;
    }

    private Table generateLogsTable(List<Log> logs) {
        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth()
                .setFontSize(10)
                .setBackgroundColor(ColorConstants.WHITE)
                .setMargin(MARGIN);

        table.addHeaderCell(generateHeaderCell("Date"))
                .addHeaderCell(generateHeaderCell("Total time"))
                .addHeaderCell(generateHeaderCell("Difficulty"))
                .addHeaderCell(generateHeaderCell("Rating"))
                .addHeaderCell(generateHeaderCell("Comment"));

        logs.forEach(log ->
            table.addCell(String.valueOf(log.getDate()))
                    .addCell(convertToTimeString(log.getTotalTime()))
                    .addCell(String.valueOf(log.getDifficulty()))
                    .addCell(String.valueOf(log.getRating()))
                    .addCell(log.getComment())
        );

        return table;
    }

    private Table generateSummaryTable(List<Tour> tours) {
        Table table = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth()
                .setFontSize(10)
                .setBackgroundColor(ColorConstants.WHITE)
                .setMargin(MARGIN);

        table.addHeaderCell(generateHeaderCell("Tour name"))
                .addHeaderCell(generateHeaderCell("Estimated time"))
                .addHeaderCell(generateHeaderCell("Average time"))
                .addHeaderCell(generateHeaderCell("Distance"))
                .addHeaderCell(generateHeaderCell("Average difficulty"))
                .addHeaderCell(generateHeaderCell("Average rating"));

        tours.forEach(tour -> {
            TourStats stats = statisticsService.calculateTourStats(tour);
            table.addCell(tour.getName())
                    .addCell(convertToTimeString(tour.getEstimatedTime()))
                    .addCell(convertToTimeString(stats.getAverageTime().longValue()))
                    .addCell(String.valueOf(tour.getDistance()))
                    .addCell(String.format("%.2f", stats.getAverageDifficulty()))
                    .addCell(String.format("%.2f", stats.getAverageRating()));

        });

        return table;
    }

    private Cell generateHeaderCell(String label) {
        return new Cell()
                .add(new Paragraph(label))
                .setBold()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }

    private Paragraph generateDocumentHeader(String header) throws IOException {
        return new Paragraph(header + " from " + LocalDate.now())
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(20)
                .setFontColor(ColorConstants.BLACK)
                .setMargin(MARGIN);
    }

    private void setUpInitialDirectory() {
        String dirName = configReader.getDefaultExportDir();
        fileChooser.setInitialDirectory(new File(dirName));
    }
}
