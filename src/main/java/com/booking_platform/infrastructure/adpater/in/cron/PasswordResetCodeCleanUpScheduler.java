package com.booking_platform.infrastructure.adpater.in.cron;

import org.springframework.stereotype.Component;

import com.booking_platform.application.port.out.ChangePasswordRepository;

@Component
public class PasswordResetCodeCleanUpScheduler {

    private final ChangePasswordRepository changePasswordRepository;

    public PasswordResetCodeCleanUpScheduler(ChangePasswordRepository changePasswordRepository) {
        this.changePasswordRepository = changePasswordRepository;
    }

    // @Scheduled(cron = "0 */2 * * * *")
    public void deleteAllCodeExpired() {
        System.out.println("Se ejecuto ejejej");
        this.changePasswordRepository.deleteAllExpirated();
    }
}
