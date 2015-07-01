package eu.execom.toolbox1sugarorm.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import eu.execom.toolbox1sugarorm.model.Message;
import eu.execom.toolbox1sugarorm.model.User;

public class DatabaseUtil {

    private static final int NUMBER_OF_USERS = 20;
    private static final int NUMBER_OF_MESSAGES_PER_USER = 5;

    public static void generateTestData(){
        for (int i = 1; i <= NUMBER_OF_USERS; i++){
            final User user = new User();
            user.setName("FirstName LastName " + i);
            user.setEmail("user" + i + "@test.com");
            user.setPassword("test");
            user.save();

            for (int j = 1; j <= NUMBER_OF_MESSAGES_PER_USER; j++) {
                final Message message = new Message();
                message.setAuthor(user);
                final String randomText = Long.toHexString(Double.doubleToLongBits(Math.random()));
                message.setText(randomText);
                message.setDateTime(getRandomDate());
                message.save();
            }
        }
    }

    public static long getRandomDate(){
        final GregorianCalendar calendar = new GregorianCalendar();
        final int year = getRandomBetween(1900, 2010);
        calendar.set(Calendar.YEAR, year);
        final int dayOfYear = getRandomBetween(1, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTimeInMillis();
    }

    public static int getRandomBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
