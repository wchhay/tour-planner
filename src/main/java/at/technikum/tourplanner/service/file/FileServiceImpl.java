package at.technikum.tourplanner.service.file;

import at.technikum.tourplanner.config.AppConfiguration;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

@Log4j2
public class FileServiceImpl implements FileService {

    private final FileChooser fileChooser;

    public FileServiceImpl(FileChooser fileChooser, AppConfiguration appConfiguration) {
        this.fileChooser = fileChooser;

        String dirName = appConfiguration.getDefaultExportDir();
        logger.debug("Setting initialDirectory={}", dirName);
        fileChooser.setInitialDirectory(new File(dirName));
    }

    @Override
    public String importFromFile() {
        File file = fileChooser.showOpenDialog(new Stage());
        StringBuilder stringBuilder = new StringBuilder();
        if (null != file) {
            try (Scanner scanner = new Scanner(file)) {
                while(scanner.hasNextLine()){
                    stringBuilder.append(scanner.nextLine()).append("\n");
                }
                return stringBuilder.toString();
            } catch (FileNotFoundException e) {
                logger.warn("File not found during import", e);
            }
        }
        return "";
    }

    @Override
    public void exportToFile(String content) {
        File file = fileChooser.showSaveDialog(new Stage());
        if (null != file) {
            try (PrintWriter printWriter = new PrintWriter(file)) {
                printWriter.write(content);
            } catch (FileNotFoundException e) {
                logger.warn("File not found during export", e);
            }
        }
    }
}
