package com.gunnarro.imagemanager.utility;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String hashPassword(String pwd) {
        String cryptedPassword = passwordEncoder.encode(pwd);
        return cryptedPassword;
    }
}
