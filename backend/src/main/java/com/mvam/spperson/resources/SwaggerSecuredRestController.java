package com.mvam.spperson.resources;

import com.mvam.spperson.configs.SwaggerConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = SwaggerConfiguration.AUTH_SCHEME)
public interface SwaggerSecuredRestController {
}
