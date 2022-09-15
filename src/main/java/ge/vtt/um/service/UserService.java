package ge.vtt.um.service;


import ge.vtt.um.model.request.ResetPasswordPromptRequest;
import ge.vtt.um.model.request.ResetPasswordVerifyRequest;
import ge.vtt.um.service.exception.UserAlreadyExistsException;
import ge.vtt.um.service.exception.UserNotFoundException;
import ge.vtt.um.model.request.GeneralRequest;
import ge.vtt.um.service.exception.UserPasswordIsNotMatchedException;
import ge.vtt.um.service.exception.VerificationCodeIsNotMatchedException;

import java.util.Map;

public interface UserService {

    void performRegistration(GeneralRequest request) throws UserAlreadyExistsException;

    Map<String, String> performAuthentication(GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException;

    void startPasswordResetProcess(ResetPasswordPromptRequest request) throws UserNotFoundException;

    void finalizePasswordResetProcess(ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException;
}
