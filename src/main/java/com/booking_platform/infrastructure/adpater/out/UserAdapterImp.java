package com.booking_platform.infrastructure.adpater.out;

import com.booking_platform.application.port.out.UserRepository;
import com.booking_platform.domain.model.User;
import com.booking_platform.infrastructure.persistence.Mapper.UserMapper;
import com.booking_platform.infrastructure.persistence.entity.UserEntity;
import com.booking_platform.infrastructure.persistence.repositoryJpa.UserJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAdapterImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserAdapterImp(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userSaved = this.userJpaRepository.save(this.userMapper.toEntity(user));
        return this.userMapper.toModel(userSaved);

    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(this.userMapper::toModel);
    }

    @Override
    public boolean existByEmail(String email) {
        return this.userJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userJpaRepository.findByUsername(username).map(this.userMapper::toModel);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return this.userJpaRepository.findByEmail(email).map(this.userMapper::toModel);
    }
}
