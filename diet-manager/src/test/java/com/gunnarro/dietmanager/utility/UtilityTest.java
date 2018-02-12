package com.gunnarro.dietmanager.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.service.DietManagerService;

import junit.framework.Assert;

public class UtilityTest {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void locale() {
        Locale locale = Locale.ENGLISH;
        System.out.println(locale.toLanguageTag());
        System.out.println(locale.getISO3Country());
        System.out.println(locale.getISO3Language());
    }

    @Test
    public void convertMarkdown() {
        String txt = "**Brev  fra Emilie 19.06.2017**"

                + "Hva vi kan gjøre for å unngå:" + "1. Stress for deg på ferie med mamma" + "2. Unngå vekttap" + "3. Unngå bekymringer vedrørende sniktrening"

                + "Noen ting vi kan gjøre for å inngå det som er nevnt over:"
                + "* Vi kan lage en liste over trening jeg for lov til å gjøre i løpet av reisen for å unngå at jeg finner på endre ting. I tillegg bestemmer du hvor mye jeg ta i meg etterpå (f.eks 1 banan og 1 sjokomelk hvir jeg vil jogge rolig 5 km/30 min)"
                + "* Jeg følger kostplanen på tur (dette er noe du ikke trenger å bekymre deg for hvis jeg har aktivitet pleier jeg også og spise mer, fordi jeg ikke vil ned i vekt. Jeg vil vise at jeg klarer å være på ferie med mamma uten å få drastiskt vekttap). I tillegg føler jeg meg frisk nok til å dra på ferie."
                + "* Jeg skal si fra hvis jeg gjør noe jeg ikek får lov til, eller noe ut over reglene våre. I det siste har det blitt avslørt nye ting rundt meg, og nå som du vet alt er jeg ikke lenger redd for å si fra. Jeg ahr holdt på og sniktrent av og til ganske lenge, og har hatt skikkelig problemer med og si noe, så jeg ble egentlig litt glad når Andreas sa det til deg.Men inni meg må jeg forberede med mentalt før noen handling skjer. Jeg hadde nok ikke klart å bare plutselig spise godteri hvis jeg ikke hadde spist det på 1 år. Jeg må tenke  inni meg at: OK, lørdag om 3 uker skal jeg spise lørdagsgodt på ordentlig. Ikke bare smake på en bit. Sånn var det når jeg begynte med godtepose, og sånn var det med sniktreningen. Jeg hadde bestemt meg innvendign for å slutte når sommerferien starta, men du rakk i finne ut av det før. jeg har begynt å jobbe for å bli kvitt sykdommen veldig nå, fordi jeg har funnet ut at jeg ikke vil ha det sånn her lenger."

                + "Noe jeg har gjort er:" + "* Sitte på do (siden jeg kom ut av sykehuset har jeg aldri sittet på dosetet, men nå er det ikke ett problem)"
                + "* Sitte å sykle" + "* Gå ett og ett trappetrinn, istendefor 2 og 2" + "* Veie maten" + "* Lage lister for alt jeg spiser"

                + "Alle disse tvangstankene har jeg gjort tiltak for å bli kvitt, nettopp ved å gå mot dem."
                + "Og når sommeren er over, da er jeg frisk. Det har jeg bestemt.";

        System.out.println(Utility.convertMarkdownToHtml(txt));
        String html = Utility.convertMarkdownToHtml("*test*");
        Assert.assertEquals("<p><em>test</em></p>\n", html);
    }

    @Test
    public void convertBirthDateToLivedMonths() {
        DateTime birthDate = new DateTime().withDayOfMonth(22).withMonthOfYear(1).withYear(2002);// withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillis(0);
        System.out.println(birthDate);
        System.out.println(birthDate.getMonthOfYear() + " " + birthDate.getYear());
        DateTime today = new DateTime();
        System.out.println(today.getMonthOfYear() + " " + today.getYear());
        int yearsOld = today.getYear() - birthDate.getYear();
        int monthsOld = today.getMonthOfYear() - birthDate.getMonthOfYear();
        System.out.println("Old: " + yearsOld + " years " + monthsOld + " months");
        System.out.println("montos old: " + (yearsOld * 12 + monthsOld));
        Date currentDate = new Date();
        Long diff = currentDate.getTime() - 0L;
        // System.out.println("Days Old: " + diff / (1000 * 60 * 60 * 26));
        // Calendar birthDateCal = Calendar.getInstance();
        // birthDateCal.set(Calendar.YEAR, 2002);
        // birthDateCal.set(Calendar.MONTH, 1);
        // birthDateCal.set(Calendar.DAY_OF_MONTH, 22);
        // System.out.println("Days Old: " + getTimeDiff(new Date(),
        // birthDateCal.getTime()));
        // System.out.println("Years Old: " +
        // (Calendar.getInstance().get(Calendar.YEAR) -
        // birthDateCal.get(Calendar.YEAR)));
        // System.out.println("Years + mounths Old: " +
        // Calendar.getInstance().get(Calendar.MONTH) + " - " +
        // birthDateCal.get(Calendar.MONTH));
        // System.out.println("Days: " + Days.daysBetween(new DateTime(),
        // birthDate));
    }

    @Test
    public void convertWeekNumberToDates() {
        System.out.println(Utility.getWeekInfo(35));
        int weekNumber = 10;
        DateTime weekStartDate = new DateTime().withWeekOfWeekyear(weekNumber);
        System.out.println(weekStartDate);

        System.out.println(Utility.getWeekInfo(34));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2016);
        System.out.println(Utility.getWeekInfo(cal.getTime()));
        // System.out.println(Utility.formatTime(System.currentTimeMillis(),
        // "ww, dd.MM.yyyy"));

        // with pure java 8
        // final long calendarWeek = 10;
        // LocalDate desiredDate = LocalDate.now()
        // .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
        // .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // System.out.println(desiredDate);
    }

    @Test
    public void generatePassword() {
        String pwd = "";
        String cryptedPassword = passwordEncoder.encode(pwd);
        System.out.println("CryptedPwd: " + cryptedPassword);
        System.out.println("equal     : " + passwordEncoder.matches(pwd, cryptedPassword));
        //
        // StandardPBEStringEncryptor crypt = new StandardPBEStringEncryptor();
        // crypt.setPassword("duMMY-enCrypT-pwd-x3");
        // crypt.setAlgorithm("PBEWithMD5AndTripleDES");
        // System.out.println("CryptedPwd: " + crypt.encrypt(pwd));
    }

    public String getTimeDiff(Date dateOne, Date dateTwo) {
        String diff = "";
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff = String.format("%d days(s)", TimeUnit.MILLISECONDS.toDays(timeDiff));
        return diff;
    }

    @Ignore
    @Test
    public void readHttp() throws IOException {
        InputStream in = new URL("http://www.who.int/growthref/bmi_girls_perc_WHO2007_exp.txt").openStream();
        try {
            InputStreamReader inR = new InputStreamReader(in);
            BufferedReader buf = new BufferedReader(inR);
            String line;
            while ((line = buf.readLine()) != null) {
                String l = line.replaceAll("\\s+", ",");
                String[] split = l.split(",");
                System.out.println("[" + split[0] + "," + split[10] + "," + split[11] + "," + split[12] + ", null],");
            }
        } finally {
            in.close();
        }
    }

    @Test
    public void testannotations() {
        Class<?> aClass = DietManagerService.class;
        for (Method m : aClass.getMethods()) {
            System.out.println(m.getName());
            for (Annotation annotation : m.getAnnotations()) {
                System.out.println("		" + annotation.toString());
            }
        }
    }

    @Test
    public void sort() {
        List<MealStatistic> list = new ArrayList<MealStatistic>();
        list.add(new MealStatistic(2016, 52));
        list.add(new MealStatistic(2016, 7));
        list.add(new MealStatistic(2016, 1));
        list.add(new MealStatistic(2017, 2));
        list.add(new MealStatistic(2017, 43));
        list.add(new MealStatistic(2017, 52));

        Collections.sort(list);
        for (MealStatistic m : list) {
            System.out.println(m.sortBy());
        }

    }
}
