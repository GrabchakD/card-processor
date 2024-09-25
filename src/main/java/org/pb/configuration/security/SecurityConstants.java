package org.pb.configuration.security;

public interface SecurityConstants {
    String SECRET = "SecretKeyToGenJWTs";
    long EXPIRATION_TIME = 86_400;
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
    String OPERATOR_NAME_STRING = "Operator-Name";
    String ALLOW_TOKEN_ACCESS_HEADER = "Access-Control-Expose-Headers";
}
