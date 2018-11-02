package com.gunnarro.dietmanager.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.dietmanager.utility.Utility;

public class PageMapExtractor implements ResultSetExtractor<Map<String, Page<?>>> {
    @Override
    public Map<String, Page<?>> extractData(ResultSet resultSet) throws SQLException {
        Map<String, Page<?>> pageMap = new HashMap<>();
        List<LogEntry> list = new ArrayList<>();
        while (resultSet.next()) {
            LogEntry log = new LogEntry();
            log.setId(resultSet.getInt("id"));
            log.setFkUserId(resultSet.getInt("fk_user_id"));
            log.setCreatedDate(new Date(resultSet.getTimestamp("created_date_time").getTime()));
            log.setLastModifiedTime(resultSet.getTimestamp("last_modified_date_time").getTime());
            log.setContent(resultSet.getString("content"));
            log.setContentHtml(Utility.convertMarkdownToHtml(resultSet.getString("content")));
            log.setLevel(resultSet.getString("level"));
            log.setTitle(resultSet.getString("title"));
            try {
                log.setNumberOfComments(resultSet.getInt("number_of_comments"));
            } catch (SQLException sqle) {
                // ignore, the column diden't exist
            }
            try {
                log.setCreatedByUser(resultSet.getString("username"));
            } catch (SQLException sqle) {
                // ignore, the column diden't exist
            }
            list.add(log);
        }
        Pageable pageSpecification = new PageRequest(25, 25, new Sort(Sort.Direction.ASC, "id"));
        pageMap.put("page", new PageImpl<LogEntry>(list,pageSpecification, 100));
        return pageMap;
    }

}
