package OOP;

import java.util.ArrayList;


public class Workshop {
	private String title;
	private String time;
	private String location;
	private String outcome;
	private int capacity;
	private ArrayList<Student> participants = new ArrayList<Student>();

	public Workshop(String title, String time, String location, String outcome, int capacity) {

		this.title = title;
		this.time = time;
		this.location = location;
		this.outcome = outcome;
		this.capacity = capacity;

	}

	public String getTitle() {return title;}

	public String getTime() {return time;}

	public String getLocation() {return location;}

	public String getOutcome() {return outcome;}

	public int getCapacity() {	return capacity;}
	
	public ArrayList<Student> getParticipants() {return participants;}

	
	 public boolean addStudent(Student s) {
	        if (participants.size() < capacity) {
	            participants.add(s);
	            return true;
	        }
	        return false;
	    }
	
	public void removeStudent(int studentId) {
		participants.removeIf(student -> student.getId() == studentId);
	}
	
	public int getSeatsLeft() {
		return (capacity - participants.size());
	}

	@Override
	public String toString() {
		return  title + "| time=" + time + "| location=" + location + "| outcome=" + outcome
				+ "| capacity=" + capacity + "| participants=" + participants + "| Seats remaining="+getSeatsLeft();
	}
	
	
	
	public void hepify(int n,int i) {
		int max = i;
		int l = 2 * i + 1;
		int r = 2 * i +2;
		
		if(l<n && participants.get(l).getId()>participants.get(max).getId())max = l;
		if(r<n && participants.get(r).getId()>participants.get(max).getId())max = r;
		
		if(max != i) {
			
			Student swap = participants.get(i);
			participants.set(i, participants.get(max));
			participants.set(max,swap);
			hepify(n, max);
			
		}
		
	}
	
	public void sortPartecipants() {
		
		int n = participants.size();
		
		for(int i = n/2-1;i>=0;i--) {
			hepify(n, i);
		}
		
		for(int i=n-1;i>0;i--) {
			
			Student temp = participants.get(0); 
			participants.set(0, participants.get(i));
			participants.set(i, temp);
			hepify(i, 0);
			
		}
		
		
	}
}
