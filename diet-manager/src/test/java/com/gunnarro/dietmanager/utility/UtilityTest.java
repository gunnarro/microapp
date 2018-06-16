package com.gunnarro.dietmanager.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.gunnarro.dietmanager.domain.log.ImageResource;
import com.gunnarro.dietmanager.domain.statistic.MealStatistic;
import com.gunnarro.dietmanager.mvc.controller.FileUploadController;
import com.gunnarro.dietmanager.service.DietManagerService;

public class UtilityTest {

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//    @Test 
//    public void testing() {
//    	System.out.println(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "getImageAsResource", "23","/u01/gunnarro/test/myfile.jpg").build().toString());
//    }
    
//    @Ignore
    @Test
    public void path() throws IOException {
        Path rootDir = Paths.get("/home/mentos/code/github/microapp/diet-manager/target/classes/uploadedfiles");
        Path userDir = Paths.get(rootDir.toString() + "/412" );//.resolve("signin.jpg");
        System.out.println(userDir.toAbsolutePath().toString());
        System.out.println(userDir.toUri().toString());
        System.out.println("real: " + userDir.toRealPath( new LinkOption[] {}) );
        System.out.println(rootDir.relativize(userDir) );
        
        Stream<Path> images = Files.walk(userDir, 1).filter(path -> !path.equals(userDir)).map(path -> rootDir.relativize(path));
        List<ImageResource> collect = images
        .map(path -> new ImageResource(path.toString()))
        .collect(Collectors.toList());
        
        System.out.println(collect);

//        Path userDir = Paths.get(rootDir.toString() + "/2323");
//        if (!Files.exists(userDir, new LinkOption[] {})) {
//            Files.createDirectories(userDir);
//        }
//        
//        List<String> collect = Files.walk(rootDir, 1).map(path -> rootDir.relativize(path).toString()).collect(Collectors.toList());
//        System.out.println(collect);
//        System.out.println(userDir.resolve("pic.jpg").toString());
//        
//        System.out.println("ulr: " + MvcUriComponentsBuilder.fromMethodName(FileUploadController.class, "serveFile", userDir.getFileName().toString()).build().toString());
    }

    @Test
    public void locale() {
        Locale locale = Locale.ENGLISH;
        System.out.println(locale.toLanguageTag());
        System.out.println(locale.getISO3Country());
        System.out.println(locale.getISO3Language());
        String id = "2323";
        System.out.println("/dir/ddd/fileanme.png".replace(".", String.format("_%s.", id)));
    }

    @Test
    public void convertMarkdown() {
        String txt = "**Brev  fra Emilie 19.06.2017**"

                + "Hva vi kan gj��re for �� unng��:" + "1. Stress for deg p�� ferie med mamma" + "2. Unng�� vekttap" + "3. Unng�� bekymringer vedr��rende sniktrening"

                + "Noen ting vi kan gj��re for �� inng�� det som er nevnt over:"
                + "* Vi kan lage en liste over trening jeg for lov til �� gj��re i l��pet av reisen for �� unng�� at jeg finner p�� endre ting. I tillegg bestemmer du hvor mye jeg ta i meg etterp�� (f.eks 1 banan og 1 sjokomelk hvir jeg vil jogge rolig 5 km/30 min)"
                + "* Jeg f��lger kostplanen p�� tur (dette er noe du ikke trenger �� bekymre deg for hvis jeg har aktivitet pleier jeg ogs�� og spise mer, fordi jeg ikke vil ned i vekt. Jeg vil vise at jeg klarer �� v��re p�� ferie med mamma uten �� f�� drastiskt vekttap). I tillegg f��ler jeg meg frisk nok til �� dra p�� ferie."
                + "* Jeg skal si fra hvis jeg gj��r noe jeg ikek f��r lov til, eller noe ut over reglene v��re. I det siste har det blitt avsl��rt nye ting rundt meg, og n�� som du vet alt er jeg ikke lenger redd for �� si fra. Jeg ahr holdt p�� og sniktrent av og til ganske lenge, og har hatt skikkelig problemer med og si noe, s�� jeg ble egentlig litt glad n��r Andreas sa det til deg.Men inni meg m�� jeg forberede med mentalt f��r noen handling skjer. Jeg hadde nok ikke klart �� bare plutselig spise godteri hvis jeg ikke hadde spist det p�� 1 ��r. Jeg m�� tenke  inni meg at: OK, l��rdag om 3 uker skal jeg spise l��rdagsgodt p�� ordentlig. Ikke bare smake p�� en bit. S��nn var det n��r jeg begynte med godtepose, og s��nn var det med sniktreningen. Jeg hadde bestemt meg innvendign for �� slutte n��r sommerferien starta, men du rakk i finne ut av det f��r. jeg har begynt �� jobbe for �� bli kvitt sykdommen veldig n��, fordi jeg har funnet ut at jeg ikke vil ha det s��nn her lenger."

                + "Noe jeg har gjort er:" + "* Sitte p�� do (siden jeg kom ut av sykehuset har jeg aldri sittet p�� dosetet, men n�� er det ikke ett problem)"
                + "* Sitte �� sykle" + "* G�� ett og ett trappetrinn, istendefor 2 og 2" + "* Veie maten" + "* Lage lister for alt jeg spiser"

                + "Alle disse tvangstankene har jeg gjort tiltak for �� bli kvitt, nettopp ved �� g�� mot dem."
                + "Og n��r sommeren er over, da er jeg frisk. Det har jeg bestemt.";

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
