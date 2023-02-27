package com.oegr.ProgramaLealtadPR.security;

import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.NotFoundExceptionMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("El correo no se encontro"));
        Business business = businessRepository.findFirstByUserId(user.getId())
                .orElse(new Business());
        return new UserDetailsImpl(user,business);
    }
}

