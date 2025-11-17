package com.app.veterinaria.application.service.admin;

import com.app.veterinaria.application.mapper.AdminDtoMapper;
import com.app.veterinaria.domain.emuns.RolAdmin;
import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.domain.repository.AdminRepository;
import com.app.veterinaria.infrastructure.web.dto.request.AdminNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.admin.AdminCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAdminService {

    private final AdminRepository adminRepository;
    private final AdminDtoMapper adminDtoMapper;

    public Mono<OperationResponseStatus> execute(AdminNewRequest request){
        return Mono.fromCallable(() -> {
            Admin admin = adminDtoMapper.toDomain(request);
            admin.setPasswordHash(admin.getPasswordHash());
            admin.setRol(asignarRol());
            return admin;
        })
                .flatMap(this::validateAndSave)
                .map(saved -> {
                    log.info("Administrador creado correctamente con id: {}", saved.getId());
                    return OperationResponseStatus.ok("Administrador creado correctamente");
                });
    }

    private Mono<Admin> validateAndSave(Admin admin){
        return adminRepository.findByCorreo(admin.getCorreo())
                .flatMap(existing -> Mono.<Admin>error(new AdminCreateException("El email ya esta registrado")))
                .switchIfEmpty(adminRepository.save(admin));
    }
//
//     contraseña en texo plano a encriptada
//    private String encriptPassword(String password){
//        return new BCryptPasswordEncoder().encode(password);
//    }

    //metodo auxiliar para asignar el rol de administrador - veterinaria por defecto
    private RolAdmin asignarRol(){return RolAdmin.VETERINARIA;}

}
