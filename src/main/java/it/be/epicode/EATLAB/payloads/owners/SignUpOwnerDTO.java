package it.be.epicode.EATLAB.payloads.owners;

public record SignUpOwnerDTO(String name,
                            String surname,
                            String email,
                            String password) {
}
