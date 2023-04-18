import server.models.Course;

import java.util.ArrayList;

public class ListeCours {
    private static ArrayList<Course> coursSession;

    public ListeCours() {;
    }

    // Getters and setters


    public ArrayList<Course> getCoursSession() {
        return coursSession;
    }

    public void setCoursSession(ArrayList<Course> coursSession) {
        this.coursSession = coursSession;
    }
}
