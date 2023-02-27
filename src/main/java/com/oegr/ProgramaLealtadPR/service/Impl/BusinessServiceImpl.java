package com.oegr.ProgramaLealtadPR.service.Impl;

import com.oegr.ProgramaLealtadPR.dto.BusinessDTO;
import com.oegr.ProgramaLealtadPR.entity.Business;
import com.oegr.ProgramaLealtadPR.entity.Transaction;
import com.oegr.ProgramaLealtadPR.entity.User;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.BadRequestExceptionMapper;
import com.oegr.ProgramaLealtadPR.exceptionMapper.exeptions.NotFoundExceptionMapper;
import com.oegr.ProgramaLealtadPR.mapper.BusinessMapper;
import com.oegr.ProgramaLealtadPR.repository.BusinessRepository;
import com.oegr.ProgramaLealtadPR.repository.UserRepository;
import com.oegr.ProgramaLealtadPR.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {
    private final BusinessMapper mapper;
    private final BusinessRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public BusinessServiceImpl(BusinessMapper mapper, BusinessRepository repository, UserRepository userRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public List<BusinessDTO> findAll() {
        List<Business> businesses = repository.findAll();
        return businesses.stream().map(mapper::toDTO).toList();
    }

    @Override
    public BusinessDTO findById(long id) {
        Business result = isExistId(id);
        return mapper.toDTO(result);
    }

    @Override
    public BusinessDTO save(BusinessDTO data) {
        User user = userRepository.findOneByEmail(data.getEmail_user())
                .orElseThrow(()-> new BadRequestExceptionMapper("Email no registrado"));
        if (!Objects.equals(user.getRole(), "BUSINESS")){
            throw new BadRequestExceptionMapper("El usuario no es del rol BUSINESS");
        }
        Business business = mapper.toEntity(data);
        return mapper.toDTO(repository.save(business));
    }

    @Override
    public BusinessDTO update(long id, BusinessDTO data) {
        return null;
    }

    @Override
    public void delete(long id) {
        isExistId(id);
        repository.deleteById(id);
    }

    public Business isExistId(long id){
        Optional<Business> result = repository.findById(id);
        return result.orElseThrow(()->new NotFoundExceptionMapper("No existe el Id: " + id));
    }

    public Business findFirstByUserId(long id) {
        Optional<Business> optionalBusiness = repository.findFirstByUserId(id);
        return optionalBusiness.orElseThrow(()->new NotFoundExceptionMapper("No existe el Id: " + id));
    }
}
