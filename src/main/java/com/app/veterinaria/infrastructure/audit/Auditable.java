package com.app.veterinaria.infrastructure.audit;

import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.emuns.EntityEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

    AccionEnum action();
    EntityEnum entity();
}
