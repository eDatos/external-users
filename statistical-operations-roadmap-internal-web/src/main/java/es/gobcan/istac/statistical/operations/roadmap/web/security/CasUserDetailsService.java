package es.gobcan.istac.statistical.operations.roadmap.web.security;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class CasUserDetailsService extends AbstractCasAssertionUserDetailsService {

    private final static String ACL_KEY = "acl";

    @Override
    @SuppressWarnings("unchecked")
    protected UserDetails loadUserDetails(Assertion assertion) {

        Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
        LinkedList<String> acls = (LinkedList<String>) attributes.get(ACL_KEY);
        List<GrantedAuthority> grantedAuthorities = acls.stream().map(acl -> new SimpleGrantedAuthority(acl)).collect(Collectors.toList());

        return new User(assertion.getPrincipal().getName(), StringUtils.EMPTY, grantedAuthorities);
    }

}
