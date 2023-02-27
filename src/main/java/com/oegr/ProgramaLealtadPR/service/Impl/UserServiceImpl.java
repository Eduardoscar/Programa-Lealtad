package com.oegr.ProgramaLealtadPR.service.Impl;

import com.oegr.ProgramaLealtadPR.dto.UserDTO;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.NotFoundExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.UserMapper;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import com.oegr.ProgramaLealtadPR.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final List<String> Roles = new ArrayList<>(Arrays.asList("USER", "ADMIN", "BUSINESS"));
    private final Pattern pattern_email = Pattern.compile("^[\\S]+@[\\S]+\\.[\\S]+$");
    private final Pattern pattern_password =Pattern.compile("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$");
    private final UserMapper mapper;
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users;
        if (Objects.equals(userRoleToken(), "ROLE_ADMIN")){
            users = repository.findAll();
        }else {
            users = repository.findUsersByRole();
        }

        return users.stream().map(mapper::toDTO).toList();
    }

    @Override
    public UserDTO findById(long id) {
        User result = isExistId(id);
        return mapper.toDTO(result);
    }

    @Override
    public UserDTO save(UserDTO data) {

        User entity = mapper.toEntity(data);
        Optional<User> user = repository.findOneByEmail(data.getEmail());
        if (user.isPresent()){
            log.warn("Email ya registardo");
            throw new BadRequestExceptionMapper("Email ya registardo");
        }

        Matcher matcher = pattern_password.matcher(data.getPassword());
        if (!matcher.find()){
            throw new BadRequestExceptionMapper("La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula.");}

        if (Objects.equals(userRoleToken(), "ROLE_ADMIN")){
            if (Objects.isNull(data.getRole())) {
                entity.setRole("USER");
            } else if (!Roles.contains(data.getRole())) {
                throw new BadRequestExceptionMapper("El Rol solo puede ser USER, ADMIN o BUSINESS");
            }
        } else {
            entity.setRole("USER");
        }

        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public UserDTO update(long id, UserDTO data) {
        User user = isExistId(id);

        if (!Objects.isNull(data.getName())) {
            user.setName(data.getName());
        }
        if (!Objects.isNull(data.getPaternalSurname())) {
            user.setPaternalSurname(data.getPaternalSurname());
        }
        if (!Objects.isNull(data.getMaternalSurname())) {
            user.setMaternalSurname(data.getMaternalSurname());
        }
        if (!Objects.isNull(data.getEmail())) {
            Matcher matcher = pattern_email.matcher(data.getEmail());
            if (!matcher.find()){
                throw new BadRequestExceptionMapper("Email inválido");}
            user.setEmail(data.getEmail());
        }

        return mapper.toDTO(repository.save(user));
    }

    @Override
    public void delete(long id) {
        isExistId(id);
        repository.deleteById(id);
    }

    public User isExistId(long id){
        Optional<User> result = repository.findById(id);
        return result.orElseThrow(()->new NotFoundExceptionMapper("No existe el Id: " +id));
    }

    public String userRoleToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        return authorities.get(0).getAuthority();
    }
}
