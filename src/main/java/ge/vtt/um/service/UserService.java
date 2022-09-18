package ge.vtt.um.service;


import ge.vtt.um.model.request.RegisterVerifyRequest;
import ge.vtt.um.model.request.ResetPasswordPromptRequest;
import ge.vtt.um.model.request.ResetPasswordVerifyRequest;
import ge.vtt.um.service.exception.*;
import ge.vtt.um.model.request.GeneralRequest;

import java.util.Map;

public interface UserService {

    void performRegistration(GeneralRequest request) throws UserAlreadyExistsException;

    void performRegisterVerification(RegisterVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, UserVerificationEntityNotFoundException;

    Map<String, String> performAuthentication(GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException;

    void startPasswordResetProcess(ResetPasswordPromptRequest request) throws UserNotFoundException;

    void finalizePasswordResetProcess(ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, PasswordResetEntityNotFoundException;
}
