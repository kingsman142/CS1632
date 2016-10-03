import java.util.*;

public class Driver{
	private static int DriverIDCounter = 1;
	private int DriverID = -1;
	private String Location = "";
	private static Random Rand = null;
	private int CupsOfCoffee = 0;

	public Driver(){
		DriverID = DriverIDCounter++;
	}

	public static void setRandom(Random random){
		if(random != null) Rand = random;
		else Rand = new Random();
	}

	public static Random getRandom(){
		return Rand;
	}

	public String getLocation(){
		return Location;
	}

	public int getCupsOfCoffee(){
		return CupsOfCoffee;
	}

	public int getDriverID(){
		return DriverID;
	}

	public boolean setInitialLocation(){
		City city = new City();

		if(Rand != null){
			String[] locationData = city.getNewLocation(Location, null, Rand);
			Location = locationData[1];
			if(Location.equals("Coffee")) CupsOfCoffee++;
			return true;
		} else{
			return false;
		}
	}

	public void setLocation(String newLocation){
		if(newLocation != null && (newLocation.equals("Hotel") || newLocation.equals("Diner") || newLocation.equals("Coffee") || newLocation.equals("Library") || newLocation.equals("Outside City"))) Location = newLocation;
	}

	public boolean moveDriver(){
		City city = new City();
		String direction = city.getNewDirection(Location, Rand);
		String[] locationData = city.getNewLocation(Location, direction, Rand);

		System.out.printf("Driver %d heading from %s to %s via %s\n", DriverID, locationData[0], locationData[1], locationData[2]);
		setLocation(locationData[1]);
		if(getLocation().equals("Coffee")) CupsOfCoffee++;

		if(checkIfDriverWentToPhiladelphia(locationData[0], locationData[1]) || checkIfDriverWentToCleveland(locationData[0], locationData[1])) return false;
		else return true;
	}

	public boolean checkIfDriverWentToPhiladelphia(String oldLocation, String newLocation){
		if(oldLocation.equals("Diner") && newLocation.equals("Outside City")){
			System.out.printf("Driver %d has gone to Philadelphia!\n", getDriverID());
			System.out.printf("Driver %d got %d cup(s) of coffee.\n-----\n", getDriverID(), getCupsOfCoffee());
			return true;
		} else{
			return false;
		}
	}

	public boolean checkIfDriverWentToCleveland(String oldLocation, String newLocation){
		if(oldLocation.equals("Library") && newLocation.equals("Outside City")){
			System.out.printf("Driver %d has gone to Cleveland!\n", DriverID);
			System.out.printf("Driver %d got %d cup(s) of coffee.\n-----\n", getDriverID(), getCupsOfCoffee());
			return true;
		} else{
			return false;
		}
	}
}
