package Event_driven;

import java.util.ArrayList;


public class HtuWorkshop {
	private String subject, sessionTime, labLocation;
    private int maxCapacity;
    private ArrayList<HtuStudent> studentList = new ArrayList<>();

    public HtuWorkshop(String subject, String sessionTime, String labLocation, int maxCapacity) {
        this.subject = subject;
        this.sessionTime = sessionTime;
        this.labLocation = labLocation;
        this.maxCapacity = maxCapacity;
    }

    public String getSubject() { return subject; }
    public String getSessionTime() { return sessionTime; }
    public String getLabLocation() { return labLocation; }
    public int getMaxCapacity() { return maxCapacity; }
    public ArrayList<HtuStudent> getStudentList() { return studentList; }
    public int getFreeSeats() { return maxCapacity - studentList.size(); }

    public boolean registerStudent(HtuStudent s) {
        if (studentList.size() < maxCapacity) {
            studentList.add(s);
            return true;
        }
        return false;
    }

    public void unregisterStudent(int id) {
        studentList.removeIf(student -> student.getUserCardId() == id);
    }
}
