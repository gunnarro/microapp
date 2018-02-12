package com.gunnarro.dietmanager.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author admin
 *
 */
@Service
public class LoginAttemptServiceImpl {

    // private final int MAX_ATTEMPT = 10;
    // private LoadingCache<String, Integer> attemptsCache;
    //
    // public LoginAttemptServiceImpl() {
    // super();
    // attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1,
    // TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
    // @Override
    // public Integer load(final String key) {
    // return 0;
    // }
    // });
    // }
    //
    // public void loginSucceeded(final String key) {
    // attemptsCache.invalidate(key);
    // }
    //
    // public void loginFailed(final String key) {
    // int attempts = 0;
    // try {
    // attempts = attemptsCache.get(key);
    // } catch (final ExecutionException e) {
    // attempts = 0;
    // }
    // attempts++;
    // attemptsCache.put(key, attempts);
    // }
    //
    // public boolean isBlocked(final String key) {
    // try {
    // return attemptsCache.get(key) >= MAX_ATTEMPT;
    // } catch (final ExecutionException e) {
    // return false;
    // }
    // }
}
