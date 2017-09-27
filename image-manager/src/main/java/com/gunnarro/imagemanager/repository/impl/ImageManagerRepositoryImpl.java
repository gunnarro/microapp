package com.gunnarro.imagemanager.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.gunnarro.imagemanager.domain.ImageDetail;
import com.gunnarro.imagemanager.domain.party.User;
import com.gunnarro.imagemanager.repository.ImageManagerRepository;
import com.gunnarro.imagemanager.repository.table.ImageDetailsTable;

/**
 * Database: jbossews User: admincnVhNH8 Password: suSNhqkXILV-
 * 
 * @author admin
 * 
 */
@Repository
// @Transactional
public class ImageManagerRepositoryImpl extends BaseJdbcRepository implements ImageManagerRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ImageManagerRepositoryImpl.class);

    private final static String WILD_CARD = "%";

    public ImageManagerRepositoryImpl() {
        super(null);
    }

    public ImageManagerRepositoryImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
	public User getUser(String userName) {
    	 try {
             User user = getJdbcTemplate().queryForObject("SELECT * FROM users WHERE username = ?", new Object[] { userName }, ImageManagerRowMapper.mapToUserRM());
             user.setRoles(getUserRoles(user.getUserName()));
             return user;
         } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
             if (LOG.isWarnEnabled()) {
                 LOG.warn(null, erae);
             }
             return null;
         }
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(Integer userId) {
        try {
            User user = getJdbcTemplate().queryForObject("SELECT * FROM users WHERE id = ?", new Object[] { userId }, ImageManagerRowMapper.mapToUserRM());
            user.setRoles(getUserRoles(user.getUserName()));
            return user;
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return null;
        }
    }

    private List<String> getUserRoles(String userName) {
        try {
            return getJdbcTemplate().query("SELECT * FROM roles WHERE username = ?", new Object[] { userName }, ImageManagerRowMapper.mapToRoleRM());
        } catch (org.springframework.dao.EmptyResultDataAccessException erae) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(null, erae);
            }
            return new ArrayList<String>();
        }
    }

    
    @Override
    public List<ImageDetail> getImages(Integer userId) {
        return getJdbcTemplate().query("SELECT * FROM image_details ORDER BY created_date_time DESC", new Object[] {}, ImageManagerRowMapper.mapToImageDetailRM());
    }

    @Override
    public ImageDetail getImage(Integer id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM image_details WHERE id = ?", new Object[] { id }, ImageManagerRowMapper.mapToImageDetailRM());
    }

    @Override
    public int createImage(ImageDetail imageDetail) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(ImageDetailsTable.createInsertPreparedStatement(imageDetail), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateImage(ImageDetail imageDetail) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(imageDetail.toString());
        }
        return getJdbcTemplate().update(ImageDetailsTable.createUpdateQuery(), ImageDetailsTable.createUpdateParam(imageDetail));
    }
    
    @Override
    public boolean deleteImage(Integer imageId) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM image_details");
        query.append(" WHERE id = ?");
        if (LOG.isDebugEnabled()) {
            LOG.debug("query=" + query.toString());
        }
        return getJdbcTemplate().update(query.toString(), new Object[] { imageId }) != 0;
    }

}
