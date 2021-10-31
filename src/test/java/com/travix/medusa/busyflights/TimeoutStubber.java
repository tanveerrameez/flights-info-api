package com.travix.medusa.busyflights;

import static org.mockito.Mockito.doAnswer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author tanveerrameez, 30 Oct 2021
 */

public class TimeoutStubber {

	public static org.mockito.stubbing.Stubber doSleep(Duration timeUnit) {
		return doAnswer(invocationOnMock -> {
			TimeUnit.MILLISECONDS.sleep(timeUnit.toMillis());
			return null;
		});
	}

}