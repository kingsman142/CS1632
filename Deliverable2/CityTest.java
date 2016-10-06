import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.*;

public class CityTest{
	//When a null value is passed into the getNewLocation() method,
	//	a random location from [Hotel, Library, Coffee, Diner] should be chosen.
	//This test stubs a Random object to make sure all output is possible.
	//The expected/observed arrays are in the format [old location, new location, direction taken].
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void NullDirectionShouldReturnRandomLocation(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(1);
		String[] expected = { "Library", "Diner", null };
		String[] observed = city.getNewLocation("Library", null, rand);

		assertArrayEquals(null, expected, observed);
	}

	//When a null Random object is passed into the getNewLocation()
	//	method, the first 2 elements in a 3 element array representing
	//	the old driver location and new driver location respectively
	//	should be the same; the driver did not move at all.  Also,
	//	a null direction is returned because the driver did not move.
	@Test
	public void NullRandomObjectShouldReturnExistingLocationData(){
		City city = new City();
		String[] observed = city.getNewLocation("Hotel", "Bill St.", null);
		String[] expected = new String[] { "Hotel", "Hotel", null };

		assertArrayEquals(null, expected, observed);
	}

	//When a null String object is passed as the current location
	//	in getNewLocation(), a String[] of size 3 should be returned
	//	containing all null values.
	//Random class is mocked in this test.
	@Test
	public void NullCurrentLocationShouldReturnNullLocationData(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		String[] observed = city.getNewLocation(null, "Bill St.", rand);
		String[] expected = new String[] { null, null, null };

		assertArrayEquals(null, expected, observed);
	}

	//When an invalid current location is passed to getNewLocation(),
	//	a String array should be returned where the old location and
	//	new location are the same values; the direction is null.
	//An invalid location means the driver should not move.
	//Random class is mocked in this test.
	@Test
	public void InvalidCurrentLocationShouldReturnExistingLocationData(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		String[] observed = city.getNewLocation("Banana Factory", "Bill St.", rand);
		String[] expected = new String[] { "Banana Factory", "Banana Factory", null };

		assertArrayEquals(null, expected, observed);
	}

	//When a new direction is chosen from the City, the Random object should
	//	take into account that Random.nextInt() can return negative values.
	//This test stubs a Random object to make sure the negatively random numbers are generated.
	//The observed behavior should treat the random number -1 as +1 and access the respective
	//	direction.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void NewDirectionShouldHandleNegativeRandomValues(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(-1);
		String expected = "Bill St.";
		String observed = city.getNewDirection("Library", rand);

		assertEquals(expected, observed);
	}

	//When a valid location from [Hotel, Library, Coffee, Diner] is passed in,
	//	there are only 2 possible streets each location can travel on.
	//Return one of the 2 possible streets.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void ValidCurrentLocationShouldReturnValidDirectionToTravel(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(1);
		String observed = city.getNewDirection("Hotel", rand);
		String expected = "Bill St.";

		assertEquals(expected, observed);
	}

	//This test is to make sure the 4 possible locations [Hotel, Coffee, Diner, Library]
	//	are considered valid to currently be in.
	@Test
	public void ValidLocationShouldReturnTrueForValidLocationCheck(){
		City city = new City();
		String hotel = "Hotel";
		String library = "Library";
		String diner = "Diner";
		String coffee = "Coffee";

		assertTrue(city.isValidLocation(hotel));
		assertTrue(city.isValidLocation(library));
		assertTrue(city.isValidLocation(diner));
		assertTrue(city.isValidLocation(coffee));
	}

	//This test is to make sure any location besides the 4 possible
	//	locations [Hotel, Coffee, Diner, Library] are considered invalid
	//	to currently be in.
	@Test
	public void InvalidLocationShouldReturnFalseForValidLocationCheck(){
		City city = new City();
		String bananaFactory = "Banana Factory";

		assertFalse(city.isValidLocation(bananaFactory));
	}

	//This test makes sure no matter what random number is generated
	//	for the new direction when the driver's current location is
	//	Hotel, the only two directions possible are Fourth Ave.
	//	and Bill St.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void CurrentLocationIsHotelShouldReturnFourthAveAndBillStAsTwoValidDirections(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(0).thenReturn(1).thenReturn(2);

		assertEquals("Fourth Ave.", city.getNewDirection("Hotel", rand));
		assertEquals("Bill St.", city.getNewDirection("Hotel", rand));
		assertEquals("Fourth Ave.", city.getNewDirection("Hotel", rand));
	}

	//When a null current location String object is passed into the
	//	getNewDirection() method, it should return a null String object
	//	representing the new direction.
	//Mocking and stubbing of the Random class are used in this test.
	@Test
	public void NullCurrentLocationShouldReturnNullDirection(){
		City city = new City();
		Random rand = Mockito.mock(Random.class);
		Mockito.when(rand.nextInt()).thenReturn(0);

		assertNull(city.getNewDirection(null, rand));
	}

	//When a null Random object is passed into the getNewDirection()
	//	method, it should return a null String object because
	//	a new random direction cannot be chosen.
	@Test
	public void NullRandomObjectShouldReturnNullDirection(){
		City city = new City();

		assertNull(city.getNewDirection("Hotel", null));
	}
}
