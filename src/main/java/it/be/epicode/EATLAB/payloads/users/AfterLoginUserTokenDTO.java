package it.be.epicode.EATLAB.payloads.users;

import jakarta.persistence.EnumType;

public record AfterLoginUserTokenDTO(String accessToken,
                                     Enum userType) {
}
