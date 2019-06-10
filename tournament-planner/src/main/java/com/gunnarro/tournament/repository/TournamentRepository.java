package com.gunnarro.tournament.repository;

import org.springframework.stereotype.Repository;

import com.gunnarro.tournament.domain.party.User;

public interface TournamentRepository {

	public User getUser(String userName);


}
