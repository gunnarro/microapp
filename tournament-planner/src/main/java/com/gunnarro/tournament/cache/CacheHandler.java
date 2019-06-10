package com.gunnarro.tournament.cache;

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gunnarro.tournament.domain.activity.Tournament;

@Component
public class CacheHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CacheHandler.class);

    private CacheManager cacheManager;
    private boolean cacheEnabled = true;

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public List<Tournament> getAllTournamentFromCache(String id) {
        List<Tournament> list = new ArrayList<Tournament>();
        if (cacheEnabled) {
            for (Object key : getTournamentCache().getKeys()) {
                list.add((Tournament) getTournamentCache().get(key).getObjectValue());
            }
        }
        return list;
    }

    public Tournament getTournamentFromCache(String key) {
        if (cacheEnabled) {
            Element element = getTournamentCache().get(key);
            if (element != null) {
                return (Tournament) element.getObjectValue();
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No hit for key=" + key);
                }
            }
        }
        return null;
    }

    public void putTournamentIntoCache(String key, Tournament tournament) {
        if (cacheEnabled) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Updated cache, key=" + key + ", Tournament: " + tournament.getName());
            }
            getTournamentCache().put(new Element(key, tournament));
        }
    }

    public void invalidateTournamentCacheElement(String key) {
        if (getTournamentCache().get(key) != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("removed from cache, key=" + key);
            }
            if (!getTournamentCache().remove(key)) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Failed to invalidate cahce element with key: " + key);
                }
            }
        }
    }

    private Cache getTournamentCache() {
        return cacheManager.getCache("tournamentCache");
    }

    public void cacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

}
