package sit707_week5;

import java.util.Date;

public class CustomDateImpl implements CustomDate {
	
	@Override
    public Date getDate() {
       return new Date();
    }
}
