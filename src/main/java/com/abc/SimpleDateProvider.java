package com.abc;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class SimpleDateProvider implements DateProvider { 
	
	private static DateProvider instance = null; 
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new SimpleDateProvider();
        return instance;
    }

    /* (non-Javadoc)
	 * @see com.abc.DateProvider#now()
	 */
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
