package sit707_week5;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.BeforeClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.AfterClass;

public class WeatherControllerTest {
	
	private static WeatherController wController;
	private static double[] temperatureHours;
	private static int nHours;
	private static CustomDate dt;
	
	@BeforeClass
    public static void setUpBeforeClass() {
		// Initialise controller
		final Date date = Mockito.mock(Date.class);
		Mockito.when(date.getTime()).thenReturn(30L);

		dt = Mockito.mock(CustomDate.class);
        Mockito.when(dt.getDate()).thenReturn(date);
        
		wController = WeatherController.getInstance(dt);
		
		nHours = wController.getTotalHours();
		temperatureHours = new double[nHours];
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			temperatureHours[i] = wController.getTemperatureForHour(i+1);
		}
    }

    @AfterClass
    public static void tearDownAfterClass() {
    	// Shutdown controller
    	wController.close();	
    }

	@Test
	public void testStudentIdentity() {
		String studentId = "223995281";
		Assert.assertNotNull("Student ID is null", studentId);
	}

	@Test
	public void testStudentName() {
		String studentName = "Siddhant Gupta";
		Assert.assertNotNull("Student name is null", studentName);
	}

	@Test
	public void testTemperatureMin() {
		System.out.println("+++ testTemperatureMin +++");
		
		// Retrieve all the hours temperatures recorded as for today
		double minTemperature = 1000;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = temperatureHours[i];
			if (minTemperature > temperatureVal) {
				minTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMinFromCache() == minTemperature);	
	}
	
	@Test
	public void testTemperatureMax() {
		System.out.println("+++ testTemperatureMax +++");
		// Retrieve all the hours temperatures recorded as for today
		double maxTemperature = -1;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = temperatureHours[i];
			if (maxTemperature < temperatureVal) {
				maxTemperature = temperatureVal;
			}
		}
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureMaxFromCache() == maxTemperature);
	}

	@Test
	public void testTemperatureAverage() {
		System.out.println("+++ testTemperatureAverage +++");

		// Retrieve all the hours temperatures recorded as for today
		double sumTemp = 0;
		for (int i=0; i<nHours; i++) {
			// Hour range: 1 to nHours
			double temperatureVal = temperatureHours[i];
			sumTemp += temperatureVal;
		}
		double averageTemp = sumTemp / nHours;
		
		// Should be equal to the min value that is cached in the controller.
		Assert.assertTrue(wController.getTemperatureAverageFromCache() == averageTemp);
	}
	
	@Test
	public void testTemperaturePersist() {
		/*
		 * Remove below comments ONLY for 5.3C task.
		 */
		System.out.println("+++ testTemperaturePersist +++");
		
		String persistTime = wController.persistTemperature(10, 19.5);
		String now = new SimpleDateFormat("H:m:s").format(dt.getDate());
		System.out.println("Persist time: " + persistTime + ", now: " + now);
		
		Assert.assertTrue(persistTime.equals(now));
		
		wController.close();
	}
}
