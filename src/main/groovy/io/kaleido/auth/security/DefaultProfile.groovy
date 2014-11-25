package io.kaleido.auth.security

import org.pac4j.http.profile.HttpProfile

class DefaultProfile extends HttpProfile {

    private static final long serialVersionUID = -1;

    public transient static final String USER_GUID = 'userGuid'

    String getUserGuid() {
        return (String) getAttribute(USER_GUID)
    }

}
