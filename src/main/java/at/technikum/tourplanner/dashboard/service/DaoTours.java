package at.technikum.tourplanner.dashboard.service;

import java.util.List;

public interface DaoTours {

    public int getTourID (Tours tour);

    public List<Tours> getAllTours(Tours tour);

    public void logTour(Tours tour);

    public void createTour();

    public void delete(Tours tour);
}
