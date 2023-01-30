package ge.vtt.um.component.utils;

public class Constants {

    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String REGISTRATION_GREETING_MESSAGE = "Registration completed successfully!";
    public static final String AUTHENTICATION_GREETING_MESSAGE = "Authentication was successful!";
    public static final String REGISTER_VERIFICATION_GREETING_MESSAGE = "Register verification was successful!";
    public static final String PASSWORD_RESET_START_GREETING_MESSAGE = "Password reset started successfully!";
    public static final String PASSWORD_RESET_COMPLETION_GREETING_MESSAGE = "Password reset completed successfully!";
    public static final String HEARTBEAT_GREETING_MESSAGE = "I am working!";

    //Templates
    public static final String PASSWORD_RESET_PROMPT_EMAIL_TEMPLATE = "Password reset code is : %s";
    public static final String USER_VERIFICATION_EMAIL_TEMPLATE = "User verification code is : %s";
    public static final String USER_VERIFICATION_EMAIL_SUBJECT = "User verification";
    public static final String PASSWORD_RESET_EMAIL_SUBJECT = "Password reset";

    public static final String REQUEST_BODY_LOGGER_TEMPLATE = "Request body : {}";

    private Constants() {

    }
}
