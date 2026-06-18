package Event_driven;

import java.util.ArrayList;



public class HtuStudent extends HtuUser {
	private ArrayList<HtuWorkshop> enrolledWorkshops = new ArrayList<>();

    public HtuStudent(String fullName, int userCardId) { 
        super(fullName, userCardId); 
    }

    public ArrayList<HtuWorkshop> getEnrolledWorkshops() { 
        return enrolledWorkshops; 
    }
}
