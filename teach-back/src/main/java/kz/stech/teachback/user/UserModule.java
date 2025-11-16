package kz.stech.teachback.user;


import org.springframework.modulith.ApplicationModule;

@ApplicationModule(
        type = ApplicationModule.Type.OPEN,
        allowedDependencies = { "common" }
)
public class UserModule { }
