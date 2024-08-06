package com.lollipop.jpaboard.user.service;

import com.lollipop.jpaboard.user.dto.UserDTO;
import com.lollipop.jpaboard.user.dto.UserSearchCriteria;
import com.lollipop.jpaboard.user.entity.User;
import com.lollipop.jpaboard.user.repository.UserRepository;
import com.lollipop.jpaboard.user.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers(UserSearchCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());
        Specification<User> spec = Specification.where(null);

        if(StringUtils.hasText(criteria.getUsername())) {
            spec = spec.and(UserSpecification.usernameContains(criteria.getUsername()));
        }

        if(StringUtils.hasText(criteria.getEmail())) {
            spec = spec.and(UserSpecification.emailContains(criteria.getEmail()));
        }
        return userRepository.findAll(spec, pageable).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertEntityToDto);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User savedUser = userRepository.save(user);
        return convertEntityToDto(savedUser);
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            User updatedUser = userRepository.save(user);
            return convertEntityToDto(updatedUser);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO convertEntityToDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}
