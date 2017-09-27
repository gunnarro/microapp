package com.gunnarro.sportsteam.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.gunnarro.sportsteam.domain.Club;
import com.gunnarro.sportsteam.domain.Team;
import com.gunnarro.sportsteam.domain.activity.Match;
import com.gunnarro.sportsteam.domain.activity.MatchStatus;
import com.gunnarro.sportsteam.domain.activity.Season;
import com.gunnarro.sportsteam.domain.league.League;
import com.gunnarro.sportsteam.domain.league.LeagueCategory;
import com.gunnarro.sportsteam.service.exception.ApplicationException;

public class ExcelReader {
    private static final Logger LOG = LoggerFactory.getLogger(ExcelReader.class);

    private ExcelReader() {
    }

    public static List<Match> mapNBFLeagueMatches(String filePath, Season season) {
        List<Match> matches = new ArrayList<Match>();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // skip the first 2 lines, which contains document header text
                if (row.getRowNum() > 2) {
                    // For each row, iterate through all the columns
                    // Date, time, venue, league, home team, away team, main
                    // referee, referee, referee
                    Match match = mapRowToMatch(row);
                    if (match != null) {
                        match.setSeason(season);
                        match.setFkSeasonId(season.getId());
                        matches.add(match);
                    }
                }
                // printCellvalues(row);
            }
            file.close();
            return matches;
        } catch (Exception e) {
            LOG.error("failed to load data from: " + filePath);
            throw new ApplicationException(e.getMessage());
        }
    }

    private static void printCellvalues(Row row) {
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            // Check the cell type and format accordingly
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                // System.out.print(cell.getDateCellValue() + "\t");
                System.out.print(cell.getNumericCellValue() + "\t");
                break;
            case Cell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue() + "\t");
                break;
            default:
                break;
            // case Cell.CELL_TYPE_BOOLEAN:
            // break;
            }
            System.out.print("");
        }
    }

    private static Match mapRowToMatch(Row row) {
        try {
            Date startDate = row.getCell(0).getDateCellValue();
            Double startTime = row.getCell(1).getNumericCellValue();
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
            cal.setTime(startDate);
            cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(startTime.toString().substring(0, 2)).intValue());
            cal.set(Calendar.MINUTE, Integer.valueOf(startTime.toString().substring(2, 4)).intValue());
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            String venue = row.getCell(2).getStringCellValue().trim();
            String leagueName = row.getCell(3).getStringCellValue().trim();
            String homeTeamName = row.getCell(5).getStringCellValue().trim();
            String awayTeamName = row.getCell(6).getStringCellValue().trim();
            // String refereeFullName =
            // row.getCell(7).getStringCellValue().trim();
            if (StringUtils.isEmpty(leagueName)) {
                LOG.error("league name not set!" + row.toString());
                return null;
            }
            if (StringUtils.isEmpty(homeTeamName)) {
                LOG.error("Home Team Name name not set!" + row.toString());
                return null;
            }
            if (StringUtils.isEmpty(awayTeamName)) {
                LOG.error("Away Team Name name not set!" + row.toString());
                return null;
            }
            Match match = new Match();
            match.setStartDate(cal.getTime());
            League league = new League();
            league.setName(leagueName);
            LeagueCategory leagueCategory = new LeagueCategory();
            leagueCategory.setSportType("BANDY");
            league.setLeagueCategory(leagueCategory);
            match.setMatchStatus(MatchStatus.createDefault());
            match.setFkStatusId(1);
            match.setLeague(league);
            match.setVenue(venue);
            Team homeTeam = new Team(null, homeTeamName);
            homeTeam.setClub(new Club(extractClubName(homeTeamName), "Bandy"));
            match.setTeam(homeTeam);
            match.setHomeTeam(homeTeam);
            Team awayTeam = new Team(null, awayTeamName);
            awayTeam.setClub(new Club(extractClubName(awayTeamName), "Bandy"));
            match.setAwayTeam(awayTeam);
            return match;
        } catch (java.lang.IllegalStateException ise) {
            LOG.error(null, ise);
            return null;
        }
    }

    private static String extractClubName(final String name) {
        try {
            return name.split("\\s+")[0];
        } catch (Exception e) {
            LOG.error(null, e);
            return null;
        }
    }
}
