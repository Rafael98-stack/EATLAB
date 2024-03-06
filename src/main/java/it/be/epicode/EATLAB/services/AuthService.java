package it.be.epicode.EATLAB.services;


import it.be.epicode.EATLAB.entities.Owner;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.users.LoginUserDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
import it.be.epicode.EATLAB.repositories.OwnersDAO;
import it.be.epicode.EATLAB.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.be.epicode.EATLAB.entities.User;
import it.be.epicode.EATLAB.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private OwnersDAO ownersDAO;

    public String authenticateUserAndGenerateToken(LoginUserDTO payload) {
        User user = usersService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

    public User saveUser(SignUpUserDTO payload) {

        User newUser = new User(payload.name(), payload.surname(),
                payload.email(), bcrypt.encode(payload.password()),
                "https://ui-avatars.com/api/?name" + payload.name() + "+" + payload.surname());

        //        mailgunSender.sendRegistrationEmail(newUser);
        return usersDAO.save(newUser);
    }

    public Owner saveOwner(SignUpUserDTO payload) {

        Owner newOwner = new Owner(payload.name(), payload.surname(),
                payload.email(), bcrypt.encode(payload.password()),
                "https://ui-avatars.com/api/?name" + payload.name() + "+" + payload.surname());

        //        mailgunSender.sendRegistrationEmail(newUser);
        return ownersDAO.save(newOwner);
    }


}