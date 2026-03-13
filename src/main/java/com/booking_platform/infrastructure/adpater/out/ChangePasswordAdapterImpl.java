package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.in.userUseCase.ChangePasswordUseCase;
import com.booking_platform.application.port.in.userUseCase.RequestPasswordResetUseCase;
import com.booking_platform.application.port.out.ChangePasswordRepository;
import com.booking_platform.domain.model.ChangeEmailCodeModel;
import com.booking_platform.infrastructure.persistence.Mapper.ChangePasswordMapper;
import com.booking_platform.infrastructure.persistence.repositoryJpa.ChangePasswordCodeJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ChangePasswordAdapterImpl implements ChangePasswordRepository {


    private final ChangePasswordMapper changePasswordMapper;
    private final ChangePasswordCodeJpaRepository changePasswordCodeJpaRepository;

    public ChangePasswordAdapterImpl(ChangePasswordMapper changePasswordMapper, ChangePasswordCodeJpaRepository changePasswordCodeJpaRepository) {
        this.changePasswordMapper = changePasswordMapper;
        this.changePasswordCodeJpaRepository = changePasswordCodeJpaRepository;
    }


    @Override
    public Optional<ChangeEmailCodeModel> findByCode(Integer code) {
        return this.changePasswordCodeJpaRepository.findByCode(code).map(this.changePasswordMapper::toModel);
    }

    @Override
    public  Optional<ChangeEmailCodeModel> findByEmail(String email) {
        return this.changePasswordCodeJpaRepository.findByEmail(email).map(this.changePasswordMapper::toModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.changePasswordCodeJpaRepository.existsByEmail(email);
    }

    @Override
    public ChangeEmailCodeModel save(ChangeEmailCodeModel changeEmailCodeModel) {
        return this.changePasswordMapper.toModel(
                this.changePasswordCodeJpaRepository.save(
                        this.changePasswordMapper.toEntity(changeEmailCodeModel))
        );
    }

    @Override
    public void delete(Long id) {
        this.changePasswordCodeJpaRepository.deleteById(id);
    }

    @Override
    public boolean existCode(Integer code) {
        return this.changePasswordCodeJpaRepository.existsByCode(code);
    }

    @Override
    public void deleteAllExpirated() {
        LocalDateTime now = LocalDateTime.now();
        this.changePasswordCodeJpaRepository.deleteAllExpired(now);
    }
}
