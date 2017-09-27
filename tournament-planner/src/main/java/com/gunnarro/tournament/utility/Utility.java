package com.gunnarro.tournament.utility;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
//import org.passay.CharacterRule;
//import org.passay.EnglishCharacterData;
//import org.passay.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

public class Utility {

    private static final Logger LOG = LoggerFactory.getLogger(Utility.class);

    private static final String TIME24HOURS_REGEXP_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String DATE_EEE_PATTERN = "EEEE, dd. MMM yyyy";

    public static final String DATE_DEFAULT_PATTERN = DATE_TIME_PATTERN;
    public static final String MYSQL_DATE_PATTERN = "yyyy-MM-dd";

    public static final String VALIDATOR_PATTERN_STRING = "[\\wæøå\\WÆØÅ0-9-\\_ \\]*";
    public static final String VALIDATOR_PATTERN_EMAIL = "[\\w-]+@([\\w-]+\\.)+[\\w-]+";
    public static final String REGEXP_DATE = "";
    public static final String REGEXP_TIME_24 = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static final String REGEXP_DATE_TIME = REGEXP_DATE + " " + REGEXP_TIME_24;

    private static final SimpleDateFormat dateFormatter;
    static {
        dateFormatter = new SimpleDateFormat(DATE_DEFAULT_PATTERN, Locale.UK);
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
    }

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    public static boolean validateValue(final String value, final String regexpPattern) {
        if (StringUtils.isEmpty(value) || StringUtils.isEmpty(regexpPattern)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regexpPattern);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public String getAPIVersion() {
        String path = "/version.prop";
        InputStream stream = getClass().getResourceAsStream(path);
        if (stream == null) {
            return "UNKNOWN";
        }
        Properties props = new Properties();
        try {
            props.load(stream);
            stream.close();
            return (String) props.get("version");
        } catch (IOException e) {
            LOG.error(null, e);
            return "UNKNOWN";
        }
    }

    public static SimpleDateFormat getDateFormatter() {
        return dateFormatter;
    }

    public static String capitalizationWord(String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String w : s.toLowerCase().split(" ")) {
            sb.append(w.substring(0, 1).toUpperCase()).append(w.substring(1, w.length())).append(" ");
        }
        return sb.toString().trim();
    }

    public static String formatTime(int hour, int minute) {
        StringBuilder time = new StringBuilder();
        time.append(padTime(hour)).append(":").append(padTime(minute));
        return time.toString();
    }

    /** Add padding to numbers less than ten */
    private static String padTime(int n) {
        if (n >= 10) {
            return String.valueOf(n);
        } else {
            return "0" + n;
        }
    }

    /**
     * Extract hour part from time pattern HH:mm
     * 
     * @param time
     * @return
     */
    public static int getHour(String time) {
        if (time.isEmpty() || time.split(":").length != 2) {
            return 0;
        }
        return Integer.valueOf(time.split(":")[0]);
    }

    /**
     * Extract hour part from time pattern HH:mm
     * 
     * @param time
     * @return
     */
    public static int getMinute(String time) {
        if (time.isEmpty() || time.split(":").length != 2) {
            return 0;
        }
        return Integer.valueOf(time.split(":")[1]);
    }

    public static String formatTime(long time, String pattern) {
        String p = pattern;
        if (p == null) {
            p = DATE_DEFAULT_PATTERN;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(p, Locale.UK);
        try {
            return sdf.format(new Date(time));
        } catch (Exception e) {
            LOG.error(null, e);
            return "";
        }
    }

    public static Date timeToDate(String time, String pattern) {
        try {
            if (StringUtils.isEmpty(time)) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_DEFAULT_PATTERN, Locale.UK);
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
            if (!StringUtils.isEmpty(pattern)) {
                sdf = new SimpleDateFormat(pattern, Locale.UK);
            }
            return sdf.parse(time);
        } catch (Exception e) {
            LOG.error("time: " + time + ", pattern: " + pattern, e);
            return null;
        }
    }

    public static Date roundToClosestHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (cal.get(Calendar.MINUTE) > 29) {
            // round up to next hour
            hour++;
        }
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY) + minutes / 60;
        cal.set(Calendar.HOUR_OF_DAY, hour);
        int minReminder = minutes % 60;
        cal.set(Calendar.MINUTE, minReminder);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date resetTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }
    
  

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.equals(" ");
    }

    public static boolean isSameDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return formatTime(date1.getTime(), "dd.MM.yyyy").equals(formatTime(date2.getTime(), "dd.MM.yyyy"));
    }

    public static String getWeekInfo(Date date) {
        DateTimeZone timeZone = DateTimeZone.forID("Europe/Paris");
        DateTime now = new DateTime(date, timeZone);
        DateTime weekStart = now.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay();
        DateTime weekEnd = now.withDayOfWeek(DateTimeConstants.SUNDAY).withTimeAtStartOfDay();
        return "Week " + now.getWeekOfWeekyear() + ", " + Utility.formatTime(weekStart.getMillis(), "EEE d") + " - " + Utility.formatTime(weekEnd.getMillis(), "EEE d MMM yyyy");
    }

    public static boolean filter(String name, String[] filters) {
        if (filters == null || filters.length == 0) {
            return true;
        }

        if (filters[0].equalsIgnoreCase("all")) {
            return true;
        }
        for (String filter : filters) {
            if (name.toLowerCase().contains(filter.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String getPeriodInfo(String period, Integer amount) {
        if (period.equalsIgnoreCase("week")) {
            return getWeekInfo(DateUtils.addWeeks(new Date(), amount));
        } else if (period.equalsIgnoreCase("month")) {
            return formatTime(DateUtils.addMonths(new Date(), amount).getTime(), "MMMM yyyy");
        } else if (period.equalsIgnoreCase("year")) {
            return formatTime(DateUtils.addYears(new Date(), amount).getTime(), "yyyy");
        }
        // return day as default
        return formatTime(DateUtils.addDays(new Date(), amount).getTime(), "EEEE d MMM yyyy");
    }

    /**
     * Validate time in 24 hours format with regular expression
     * 
     * @param time time address for validation
     * @return true valid time fromat, false invalid time format
     */
    public static boolean validateTime24H(final String time) {
        if (time == null || time.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(TIME24HOURS_REGEXP_PATTERN);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    public static String hashPassword(String pwd) {
        String cryptedPassword = passwordEncoder.encode(pwd);
        System.out.println(cryptedPassword);
        return cryptedPassword;
    }

    /**
    public static String generatePassword() {
        List<CharacterRule> rules = Arrays.asList(
        // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // at least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // at least one symbol (special character)
                new CharacterRule(EnglishCharacterData.Special, 1));
        PasswordGenerator generator = new PasswordGenerator();
        // Generated password is 12 characters long, which complies with policy
        return generator.generatePassword(12, rules);
    }
    **/
}
