package com.gunnarro.sportsteam.utility;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.Season;

@Ignore
public class ExcelReaderTest {

    @Ignore
    @Test
    public void manualReadFile() {
        try {
            //                                                     C:\code\openshift\git\jbossews\data\nbf\allekamper_21_10_14_pr_bane.xlsx
            //                                                     C:\\code\\openshift\\git\\jbossews\\data\\nbf\\allekamper_21_10_14_pr_bane.xlsx
            List<Match> matches = ExcelReader.mapNBFLeagueMatches("C:\\code\\openshift\\git\\jbossews\\data\\nbf\\allekamper_21_10_14_pr_bane.xlsx", new Season(System.currentTimeMillis(),System.currentTimeMillis()));
            Collections.sort(matches, new Comparator<Match>() {
                @Override
                public int compare(Match o1, Match o2) {
                    return o1.getStartDate().compareTo(o2.getStartDate());
                }
            });

            for (Match match : matches) {
                if (match.getLeague().getName().equalsIgnoreCase("Lillegutt Oslo 03")
                        && (match.getHomeTeam().getName().equalsIgnoreCase("Ullevål 1") || match.getAwayTeam().getName().equalsIgnoreCase("Ullevål 1"))) {
                    System.out.println(match.getMatchInfo());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
