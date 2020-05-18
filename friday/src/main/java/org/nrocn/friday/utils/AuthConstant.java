package org.nrocn.friday.utils;

public class AuthConstant {

    /**
     * sign token
     */
    public static final String SIGN_TOKEN = "NROCNfriday123456*!@#$%";

    public static final String COOKIE_NAME = "NROCN-friday";

    public static final String SESSION_NAME = "friday-user";
    // 用户信息唯一id
    public static final String CURRENT_USER_HEADER = "friday-current-user-id";
    // 用户邮件
    public static final String CURRENT_USER_EMAIL = "friday-current-user-email";
    // AUTHORIZATION_HEADER is the http request header
    // key used for accessing the internal authorization.
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // AUTHORIZATION_ANONYMOUS_WEB is set as the Authorization header to denote that
    // a request is being made bu an unauthenticated web user
    public static final String AUTHORIZATION_ANONYMOUS_WEB = "faraday-anonymous";
    // AUTHORIZATION_COMPANY_SERVICE is set as the Authorization header to denote
    // that a request is being made by the company service
    public static final String AUTHORIZATION_COMPANY_SERVICE = "company-service";
    // AUTHORIZATION_BOT_SERVICE is set as the Authorization header to denote that
    // a request is being made by the bot microservice
    public static final String AUTHORIZATION_BOT_SERVICE = "bot-service";
    // AUTHORIZATION_ACCOUNT_SERVICE is set as the Authorization header to denote that
    // a request is being made by the account service
    public static final String AUTHORIZATION_ACCOUNT_SERVICE = "account-service";
    // AUTHORIZATION_SUPPORT_USER is set as the Authorization header to denote that
    // a request is being made by a Staffjoy team member
    public static final String AUTHORIZATION_SUPPORT_USER = "friday-support";
    // AUTHORIZATION_SUPERPOWERS_SERVICE is set as the Authorization header to
    // denote that a request is being made by the dev-only superpowers service
    public static final String AUTHORIZATION_SUPERPOWERS_SERVICE = "superpowers-service";
    // AUTHORIZATION_WWW_SERVICE is set as the Authorization header to denote that
    // a request is being made by the www login / signup system
    public static final String AUTHORIZATION_WWW_SERVICE = "www-service";
    // AUTH_WHOAMI_SERVICE is set as the Authorization heade to denote that
    // a request is being made by the whoami microservice
    public static final String AUTHORIZATION_WHOAMI_SERVICE = "whoami-service";
    // AUTHORIZATION_AUTHENTICATED_USER is set as the Authorization header to denote that
    // a request is being made by an authenticated we6b user
    public static final String AUTHORIZATION_AUTHENTICATED_USER = "faraday-authenticated";
    // AUTHORIZATION_ICAL_SERVICE is set as the Authorization header to denote that
    // a request is being made by the ical service
    public static final String AUTHORIZATION_ICAL_SERVICE = "ical-service";
    // AUTH ERROR Messages
    public static final String ERROR_MSG_DO_NOT_HAVE_ACCESS = "You do not have access to this service";
    public static final String ERROR_MSG_MISSING_AUTH_HEADER = "Missing Authorization http header";
}
