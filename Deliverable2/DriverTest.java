import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;

public class DriverTest{
	//When a new driver is created, they should automatically start
	//	with 0 cups of coffee.
	//The expected behavior is 0 cups of coffee.
	//Note: the driver's initial location is not set until the
	//		setInitialLocation() method is called, so cups
	//		cannot be incremented yet.
	@Test
	public void IfNewDriverShouldShouldReturnZeroCupsOfCoffee(){
		Driver d = new Driver();
		int cupsOfCoffee = d.getCupsOfCoffee();
		assertEquals(0, cupsOfCoffee);
	}

	//When five separated drivers are created, their IDs should
	//	be incremented automatically (1, 2, 3, 4, 5).
	//Note: the IDs are based at 1, not 0.
	//Note: for this test case, the IDs are [6, 7, 8, 9, 10]
	//		because a previous tests already incremented the static
	//		ID variable.
	@Test
	public void IfFiveDriverObjectsShouldIncrementIDs(){
		Driver one = new Driver();
		Driver two = new Driver();
		Driver three = new Driver();
		Driver four = new Driver();
		Driver five = new Driver();

		assertEquals(6, one.getDriverID());
		assertEquals(7, two.getDriverID());
		assertEquals(8, three.getDriverID());
		assertEquals(9, four.getDriverID());
		assertEquals(10, five.getDriverID());
	}

	//When INT_MAX+1 drivers are created, the IDs should loop
	//	back to INT_MIN, and start incrementing from there.
	//Note: this test begins at i = 17 because 16 other drivers
	//		have been created at this point from other unit tests
	//		and the ID counter is a static variable.
	@Test
	public void IfNumDriversExceedsIntMaxIDShouldTurnNegative(){
		for(int i = 17; i < Integer.MAX_VALUE; i++){
			Driver d = new Driver();
			assertEquals(i, d.getDriverID());
		}

		Driver d1 = new Driver();
		assertEquals(Integer.MAX_VALUE, d1.getDriverID());
		Driver d2 = new Driver();
		assertEquals(Integer.MIN_VALUE, d2.getDriverID());
	}

	//When a new driver is created and its initial location is set,
	//	there should only be 4 possible options (Hotel, Diner, Library, Coffee).
	//In this test, a 5th driver is created to test when the index 4 (5th element)
	//	is accessed.
	//Five drivers are created, the nextInt() Random method is stubbed,
	//	and each driver's initial location is set.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void InitialLocationShouldHaveOnlyFourPossibilities(){
		Driver[] drivers = { new Driver(), new Driver(), new Driver(), new Driver(), new Driver() };
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4);
		Driver.setRandom(rand);

		for(Driver d: drivers){
			d.setInitialLocation();
		}

		assertEquals("Hotel", drivers[0].getLocation());
		assertEquals("Diner", drivers[1].getLocation());
		assertEquals("Library", drivers[2].getLocation());
		assertEquals("Coffee", drivers[3].getLocation());
		assertEquals("Hotel", drivers[4].getLocation());
	}

	//When the random seed is set for all drivers, the static
	//	object is stored in the drivers.  So, when the random
	//	object reference is grabbed, it should be the same.
	//Assert that the reference for the Random object is the same
	//	as what was fed into the program.
	//Random class is mocked in this test.
	@Test
	public void SettingRandomSeedShouldReturnSameObjectReference(){
		Random rand = Mockito.mock(Random.class);
		Driver.setRandom(rand);
		assertEquals(rand, Driver.getRandom());
	}

	//When setting the random object for the Driver class, if
	//	a null reference was passed, a new random Random object
	//	is created to make sure all other methods can be run fine.
	@Test
	public void SettingRandomSeedToNullShouldCreateANewRandomObject(){
		Driver.setRandom(null);
		assertNotNull(Driver.getRandom());
	}

	//When the constructor for a driver is called, an ID should
	//	immediately be assigned without having to call another
	//	function.
	//Note: this driver's ID is -2147483647 because other tests have
	//		increased the static ID counter variable previously.
	@Test
	public void CreatingNewDriverShouldAutomaticallyAssignAnID(){
		Driver d = new Driver();
		assertEquals(-2147483647, d.getDriverID());
	}

	//When passing in a valid location from [Hotel, Library, Diner, Coffee],
	//	the location of the driver should be set to that location.
	@Test
	public void SettingValidLocationShouldSetLocationOfDriver(){
		Driver d = new Driver();
		d.setLocation("Hotel");
		assertEquals("Hotel", d.getLocation());
	}

	//When any string besides [Hotel, Library, Diner, Coffee] is passed
	//	into the setLocation() function, the location should not change.
	@Test
	public void SettingInvalidLocationShouldNotChangeCurrentLocation(){
		Driver d = new Driver();
		d.setLocation("Banana Factory");
		assertEquals("", d.getLocation());
	}

	//When a null String object is passed into the setLocation()
	//	function, the driver's current location should not change.
	@Test
	public void SettingNullLocationShouldNotChangeCurrentLocation(){
		Driver d = new Driver();
		d.setLocation(null);
		assertEquals("", d.getLocation());
	}

	//When a driver moves from their current location to the Coffee
	//	location, their number of cups of coffee should increment.
	//Note: this test case is not covering when a driver's initial
	//		location is set.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void IfDriverVisitsCoffeeShopShouldIncrementCupsOfCoffee(){
		Driver d = new Driver();
		Random oldRand = d.getRandom(); //Placeholder for the Random object that was already there from other tests.
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(1);
		Driver.setRandom(rand); //Set up stubbed Random
		d.setInitialLocation();
		d.moveDriver();
		Driver.setRandom(oldRand); //Restore old Random
		assertEquals(1, d.getCupsOfCoffee());
	}

	//When a driver's initial location is set to Coffee, their
	//	number of cups of coffee should be set to 1.
	//Note: this test case only covers when the initial location
	//		is set, not when a driver travels from one location
	//		to Coffee.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void IfInitialLocationIsCoffeeShouldSetCupsOfCoffeeToOne(){
		Driver d = new Driver();
		Random oldRand = d.getRandom(); //Placeholder for the Random object that was already there from other tests.
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(3);
		Driver.setRandom(rand); //Set up stubbed Random
		d.setInitialLocation();
		Driver.setRandom(oldRand); //Restore old Random
		assertEquals(1, d.getCupsOfCoffee());
	}

	//When a driver is moved from Hotel along Fourth Ave., their
	//	new location should be the Diner.
	//This test mocks the Driver and sets its initial location to
	//	the Hotel.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void IfCurrentLocationIsHotelAndMoveDriverAlongFourthAveShouldGoToDiner(){
		Driver d = new Driver();
		Random oldRand = d.getRandom();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(0).thenReturn(0);
		Driver.setRandom(rand);

		assertTrue( d.setInitialLocation() ); //Mock the driver's current scenario of being at Hotel
		assertEquals("Hotel", d.getLocation()); //Make sure the initial location is Hotel
		assertTrue( d.moveDriver() );
		assertEquals("Diner", d.getLocation());

		Driver.setRandom(oldRand);
	}

	//When a driver is moved from Hotel along Fourth Ave., their
	//	new location should be the Diner.  Also, if another driver
	//	begins at Hotel and moves along Bill St, their new
	//	location should be the Library.
	//This test mocks the Driver and sets its initial location to
	//	the Hotel.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void IfCurrentLocationIsHotelAndMoveDriverShouldNotGoToAnyLocationBesidesDinerAndLibrary(){
		Driver d = new Driver();
		Random oldRand = d.getRandom();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(0).thenReturn(0).thenReturn(0).thenReturn(1);
		Driver.setRandom(rand);

		assertTrue( d.setInitialLocation() ); //Mock the driver's current scenario of being at Hotel
		assertEquals("Hotel", d.getLocation()); //Make sure the initial location is Hotel
		assertTrue( d.moveDriver() );
		assertEquals("Diner", d.getLocation()); //End up at Library after moving on Fourth Ave.

		Driver d1 = new Driver();

		assertTrue( d1.setInitialLocation() ); //Mock a second driver and place their initial location at Hotel
		assertEquals("Hotel", d1.getLocation()); //Make sure the initial location is Hotel
		assertTrue( d1.moveDriver() );
		assertEquals("Library", d1.getLocation()); //End up at Library after moving on Bill St.

		Driver.setRandom(oldRand);
	}

	//When a driver's current location is Diner and after moving,
	//	their new location is Outside City, that should signal to
	//	the program that the driver went to Philadelphia.
	//The method returns a boolean, so it should return true.
	@Test
	public void IfCurrentLocationIsDinerAndNewLocationIsOutsideCityThenOutsideCityCheckShouldReturnTrue(){
		Driver d = new Driver();
		assertTrue( d.checkIfDriverWentToPhiladelphia("Diner", "Outside City") );
	}

	//When a driver's current location is Diner and after moving,
	//	their new location is Coffee, that should signal to
	//	the program that the driver did not go to Philadelphia.
	//The method returns a boolean, so it should return false.
	@Test
	public void IfCurrentLocationIsDinerAndNewLocationIsCoffeeThenOutsideCityCheckShouldReturnFalse(){
		Driver d = new Driver();
		assertFalse( d.checkIfDriverWentToPhiladelphia("Diner", "Coffee") );
	}

	//When a driver's current location is Library and after moving,
	//	their new location is Outside City, that should signal to
	//	the program that the driver went to Cleveland.
	//The method returns a boolean, so it should return true.
	@Test
	public void IfCurrentLocationIsLibraryAndNewLocationIsOutsideCityThenOutsideCityCheckShouldReturnTrue(){
		Driver d = new Driver();
		assertTrue( d.checkIfDriverWentToCleveland("Library", "Outside City") );
	}

	//When a driver's current location is Library and after moving,
	//	their new location is Hotel, that should signal to
	//	the program that the driver did not go to Cleveland.
	//The method returns a boolean, so it should return false.
	@Test
	public void IfCurrentLocationIsLibraryAndNewLocationIsHotelThenOutsideCityCheckShouldReturnFalse(){
		Driver d = new Driver();
		assertFalse( d.checkIfDriverWentToCleveland("Library", "Hotel") );
	}
}
