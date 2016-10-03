import java.util.*;
import org.junit.runner.*;
import org.junit.runner.notification.*;

public class TestRunner{
	public static void main(String[] args){
		ArrayList<Class> classesToTest = new ArrayList<Class>();
		boolean anyFailures = false;

		classesToTest.add(DriverTest.class);
		classesToTest.add(CityTest.class);
		//classesToTest.add(CitySim9003Test.class);

		for(Class c: classesToTest){
			Result r = JUnitCore.runClasses(c);

		    // Print out any failures for this class.

		    for (Failure f: r.getFailures()) {
				System.out.println(f.toString());
		    }

		    // If r is not successful, there was at least one
		    // failure.  Thus, set anyFailures to true - this
		    // can never be set back to false (no amount of
		    // successes will ever eclipse the fact that there
		    // was at least one failure.

		    if (!r.wasSuccessful()) {
				anyFailures = true;
		    }
		}

		if (anyFailures) {
		    System.out.println("\n!!! - At least one failure, see above.");
		} else {
		    System.out.println("\nALL TESTS PASSED");
		}
	}
}
