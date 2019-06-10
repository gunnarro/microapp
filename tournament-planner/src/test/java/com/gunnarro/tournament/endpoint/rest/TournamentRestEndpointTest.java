package com.gunnarro.tournament.endpoint.rest;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gunnarro.tournament.endpoint.rest.TournamentRestEndpoint;
import com.gunnarro.tournament.mvc.controller.SpringTestSetup;
import com.gunnarro.tournament.service.TournamentPlannerService;

public class TournamentRestEndpointTest extends SpringTestSetup {

    private TournamentRestEndpoint restEndpoint;

    @Mock
    private TournamentPlannerService TournamentPlannerServiceMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        restEndpoint = new TournamentRestEndpoint(TournamentPlannerServiceMock);
    }

  
}

