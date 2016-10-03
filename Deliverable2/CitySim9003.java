import java.util.*;

public class CitySim9003{
	public static void main(String[] args){
		Driver[] drivers = { new Driver(), new Driver(), new Driver(), new Driver(), new Driver() };
		Random rand = null;

		try{
			if(args.length > 1) throw new Exception("Too many arguments passed in.");
			Long seedArgument = new Long(args[0]).longValue();
			rand = new Random(seedArgument);
		} catch(InputMismatchException e){
			System.out.println("Invalid command-line argument type.  Integer is required for random seed.  Exiting...");
			System.exit(1);
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("No command-line arguments were passed to the program for the random seed.  Exiting...");
			System.exit(1);
		} catch(Exception e){
			System.out.println("Too many arguments were passed to the program.  Only one integer should be passed.  Exiting...");
			System.exit(1);
		}

		Driver.setRandom(rand);
		for(int i = 0; i < drivers.length; i++){
			drivers[i].setInitialLocation();
			while(drivers[i].moveDriver());
		}
	}
}
