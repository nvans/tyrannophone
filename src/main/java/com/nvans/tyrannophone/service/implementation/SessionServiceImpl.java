package com.nvans.tyrannophone.service.implementation;

import com.nvans.tyrannophone.model.security.CustomUserPrinciple;
import com.nvans.tyrannophone.service.SessionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger log = Logger.getLogger(SessionServiceImpl.class);

    @Autowired
    private SessionRegistry sessionRegistry;

    @Async
    @Override
    public void invalidateSession(Long userId) {

        log.info("Performing session invalidation for user {" + userId + "}");

        List<Object> principals = sessionRegistry.getAllPrincipals();

        CustomUserPrinciple principal = null;

        // Find blocked customer's principal
        for(Object o : principals) {
            if (o instanceof CustomUserPrinciple) {
                if (userId.equals(((CustomUserPrinciple) o).getUserId())) {
                    principal = (CustomUserPrinciple) o;
                }
            }
        }

        // Invalidate all user's sessions
        if (principal != null) {

            List<SessionInformation> sessionInformation = sessionRegistry.getAllSessions(principal, true);

            for (SessionInformation si : sessionInformation) {
                si.expireNow();
            }
        }

        log.info("Sessions for user {" + userId + "} have been invalidated.");
    }
}
