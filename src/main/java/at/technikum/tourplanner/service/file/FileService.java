package at.technikum.tourplanner.service.file;

public interface FileService {

    String importFromFile();

    void exportToFile(String content);
}
