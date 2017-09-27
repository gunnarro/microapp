package com.gunnarro.tournament.endpoint.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gunnarro.tournament.service.TournamentPlannerService;

@RestController
@RequestMapping("/rest/dietmanager")
public class TournamentRestEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(TournamentRestEndpoint.class);

    @Autowired
    @Qualifier("tournamentPlannerService")
    private TournamentPlannerService tournamentPlannerService;

    /**
     * default constructor, used by spring
     */
    public TournamentRestEndpoint() {
    }

    /**
     * For unit testing only
     * 
     * @param sportsTeamService - inject as mock
     */
	public TournamentRestEndpoint(TournamentPlannerService tournamentPlannerService) {
		super();
		this.tournamentPlannerService = tournamentPlannerService;
	}

    
    

}
