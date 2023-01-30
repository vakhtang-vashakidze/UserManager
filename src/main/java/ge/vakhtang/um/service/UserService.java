package ge.vakhtang.um.service;


import ge.vakhtang.um.model.request.GeneralRequest;
import ge.vakhtang.um.model.request.RegisterVerifyRequest;
import ge.vakhtang.um.model.request.ResetPasswordPromptRequest;
import ge.vakhtang.um.model.request.ResetPasswordVerifyRequest;
import ge.vakhtang.um.service.exception.*;

import java.util.Map;

public interface UserService {

    void performRegistration(GeneralRequest request) throws UserAlreadyExistsException;

    void performRegisterVerification(RegisterVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, UserVerificationEntityNotFoundException;

    Map<String, String> performAuthentication(GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException;

    void startPasswordResetProcess(ResetPasswordPromptRequest request) throws UserNotFoundException;

    void finalizePasswordResetProcess(ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, PasswordResetEntityNotFoundException;
}
