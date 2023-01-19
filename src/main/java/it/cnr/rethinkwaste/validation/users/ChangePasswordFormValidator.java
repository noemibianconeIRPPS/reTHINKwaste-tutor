package it.cnr.rethinkwaste.validation.users;

import it.cnr.rethinkwaste.service.UserService;
import it.cnr.rethinkwaste.web.registration.form.ChangePasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordFormValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class clazz) {
        return ChangePasswordForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangePasswordForm changePasswordForm = (ChangePasswordForm) target;
        if (changePasswordForm.getCurrentPassword().isEmpty()) {
            errors.rejectValue("currentPassword", "changePasswordModal.error.fieldEmpty");
        }
        if (changePasswordForm.getNewPassword().isEmpty()) {
            errors.rejectValue("newPassword", "changePasswordModal.error.fieldEmpty");
        }
        if (changePasswordForm.getRetypeNewPassword().isEmpty()) {
            errors.rejectValue("retypeNewPassword", "changePasswordModal.error.fieldEmpty");
        }
        if (!changePasswordForm.getNewPassword().equals(changePasswordForm.getRetypeNewPassword())) {
            errors.rejectValue("retypeNewPassword", "changePasswordModal.error.passwordMismatch");
        }
    }
}
