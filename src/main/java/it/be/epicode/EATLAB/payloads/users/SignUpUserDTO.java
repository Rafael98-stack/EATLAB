package it.be.epicode.EATLAB.payloads.users;

public record SignUpUserDTO(String name,
                            String surname,
                            String email,
                            String password) {
}
