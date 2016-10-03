import java.util.*;

public class City{
	private final String[] Locations =  { "Hotel", "Diner", "Library", "Coffee", "Outside City" };
	private final String[] Hotel =	    { "Fourth Ave.", "Bill St." };
	private final String[] Diner =	    { "Fourth Ave.", "Phil St." };
	private final String[] Library =	{ "Fifth Ave.", "Bill St." };
	private final String[] Coffee =		{ "Fifth Ave.", "Phil St." };

	public String[] getNewLocation(String currentLocation, String direction, Random rand){
		String newLocation = "";

		if(currentLocation == null){
			return new String[] { null, null, null };
		} else if(rand == null){
			return new String[] { currentLocation, currentLocation, null };
		} else if(direction == null){
			newLocation = Locations[Math.abs(rand.nextInt()) % 4];
			return new String[] { currentLocation, newLocation, null };
		} else if(!isValidLocation(currentLocation)){
			return new String[] { currentLocation, currentLocation, null };
		}

		switch(direction){
			case "Fourth Ave.":
				if(currentLocation.equals("Hotel")) newLocation = "Diner";
				else if(currentLocation.equals("Diner")) newLocation = "Outside City";
				break;
			case "Fifth Ave.":
				if(currentLocation.equals("Coffee")) newLocation = "Library";
				else if(currentLocation.equals("Library")) newLocation = "Outside City";
				break;
			case "Bill St.":
				if(currentLocation.equals("Library")) newLocation = "Hotel";
				else if(currentLocation.equals("Hotel")) newLocation = "Library";
				break;
			case "Phil St.":
				if(currentLocation.equals("Diner")) newLocation = "Coffee";
				else if(currentLocation.equals("Coffee")) newLocation = "Diner";
				break;
			default:
				newLocation = currentLocation;
				break;
		}

		return new String[] { currentLocation, newLocation, direction };
	}

	public String getNewDirection(String currentLocation, Random rand){
		if(rand == null || currentLocation == null) return null;

		int newDirection = Math.abs(rand.nextInt()) % 2;

		if(currentLocation.equals("Hotel"))
			return Hotel[newDirection];
		else if(currentLocation.equals("Diner"))
			return Diner[newDirection];
		else if(currentLocation.equals("Library"))
			return Library[newDirection];
		else if(currentLocation.equals("Coffee"))
			return Coffee[newDirection];

		return null;
	}

	public boolean isValidLocation(String location){
		return location.equals("Hotel") || location.equals("Diner") || location.equals("Library") || location.equals("Coffee");
	}
}
