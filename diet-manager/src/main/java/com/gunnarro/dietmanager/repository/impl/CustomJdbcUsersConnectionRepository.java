package com.gunnarro.dietmanager.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * {@link UsersConnectionRepository} that uses the JDBC API to persist
 * connection data to a relational database. The supporting schema is defined in
 * JdbcUsersConnectionRepository.sql.
 * 
 * @author Keith Donald
 */
// @Repository
public class CustomJdbcUsersConnectionRepository implements UsersConnectionRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CustomJdbcUsersConnectionRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;

    private String tablePrefix = "";

    public CustomJdbcUsersConnectionRepository(DataSource dataSource, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    // @Override
    // public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
    // this.connectionSignUp = connectionSignUp;
    // }

    /**
     * Sets a table name prefix. This will be prefixed to all the table names
     * before queries are executed. Defaults to "". This is can be used to
     * qualify the table name with a schema or to distinguish Spring Social
     * tables from other application tables.
     * 
     * @param tablePrefix the tablePrefix to set
     */
    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        if (LOG.isDebugEnabled()) {
            LOG.debug("providerUrl: " + key.getProviderId() + ", providerUserId: " + key.getProviderUserId());
        }
        List<String> localUserIds = jdbcTemplate.queryForList(
                "select userId from " + tablePrefix + "UserConnection where providerId = ? and providerUserId = ?", String.class, key.getProviderId(),
                key.getProviderUserId());
        if (localUserIds.size() == 0 && connectionSignUp != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("local user id not found, direct to signup!");
            }
            String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null) {
                createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("localUserIds: " + localUserIds);
        }
        return localUserIds;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("providerId", providerId);
        parameters.addValue("providerUserIds", providerUserIds);
        if (LOG.isDebugEnabled()) {
            LOG.debug("providerUrl: " + providerId + ", providerUserId: " + providerUserIds);
        }
        final Set<String> localUserIds = new HashSet<String>();
        return new NamedParameterJdbcTemplate(jdbcTemplate).query(
                "select userId from " + tablePrefix + "UserConnection where providerId = :providerId and providerUserId in (:providerUserIds)", parameters,
                new ResultSetExtractor<Set<String>>() {
                    @Override
                    public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            localUserIds.add(rs.getString("userId"));
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("localUserIds: " + localUserIds);
                        }
                        return localUserIds;
                    }
                });
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return null;// new JdbcConnectionRepository(userId, jdbcTemplate,
                    // connectionFactoryLocator, textEncryptor, tablePrefix);
    }

    @Override
    public void setConnectionSignUp(ConnectionSignUp arg0) {
        // TODO Auto-generated method stub

    }

}