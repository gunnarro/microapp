package com.gunnarro.tournament.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gunnarro.tournament.domain.party.User;
import com.gunnarro.tournament.repository.TournamentRepository;

/**
 * Database: jbossews User: admincnVhNH8 Password: suSNhqkXILV-
 * 
 * @author admin
 * 
 */
@Repository
// @Transactional
public class TournamentRepositoryImpl extends BaseJdbcRepository implements
		TournamentRepository {

	private static final Logger LOG = LoggerFactory
			.getLogger(TournamentRepositoryImpl.class);

	public TournamentRepositoryImpl() {
		super(null);
	}

	public TournamentRepositoryImpl(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	// FIXME not implemented
	@Override
	public User getUser(String userName) {
		return null;
	}

	
}
