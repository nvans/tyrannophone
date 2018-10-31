package com.nvans.tyrannophone.service;

public interface SessionService {

    /**
     * The method invalidates user's sessions.
     *
     * @param userId - user's id
     */
    void invalidateSession(Long userId);
}
