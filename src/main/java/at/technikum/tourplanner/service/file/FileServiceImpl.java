package at.technikum.tourplanner.service.file;

import at.technikum.tourplanner.config.ConfigService;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileServiceImpl implements FileService {

    public static final String DEFAULT_EXPORT_DIR = "default-export-dir";
    private final FileChooser fileChooser;

    public FileServiceImpl(FileChooser fileChooser, ConfigService configService) {
        this.fileChooser = fileChooser;

        String dirName = configService.getKey(DEFAULT_EXPORT_DIR);
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
                e.printStackTrace();
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
                // TODO: custom exception
                e.printStackTrace();
            }
        }
    }
}
