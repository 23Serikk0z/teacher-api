package kz.stech.teachback.security;


import org.springframework.modulith.ApplicationModule;

@ApplicationModule(
        type = ApplicationModule.Type.OPEN,
        allowedDependencies = { "common" }
)
public class SecurityModule { }
