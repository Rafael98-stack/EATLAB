package it.be.epicode.EATLAB.services;



import it.be.epicode.EATLAB.entities.Type;
import it.be.epicode.EATLAB.exceptions.UnauthorizedException;
import it.be.epicode.EATLAB.payloads.owners.SignUpOwnerDTO;
import it.be.epicode.EATLAB.payloads.users.LoginUserDTO;
import it.be.epicode.EATLAB.payloads.users.SignUpUserDTO;
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


    public String authenticateUserAndGenerateToken(LoginUserDTO payload) {
        User user = usersService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createTokenUser(user);
        } else {
            throw new UnauthorizedException("Credenziali sbagliate!");
        }
    }

    public Enum<Type> authenticatedUserType (LoginUserDTO payload) {
        User user = usersService.findByEmail(payload.email());
        if(user!= null){
            return user.getType();
        } else {
            return null;
        }
    }

    public User saveUser(SignUpUserDTO payload) {


        User newUser = new User(payload.name(), payload.surname(),
                payload.email(), bcrypt.encode(payload.password()),
                "https://ui-avatars.com/api/?name" + payload.name() + "+" + payload.surname());

        //        mailgunSender.sendRegistrationEmail(newUser);
        return usersDAO.save(newUser);
    }

    public User saveOwner(SignUpOwnerDTO payload) {

        User newOwner = new User(payload.name(), payload.surname(),
                payload.email(), bcrypt.encode(payload.password()),
                "https://ui-avatars.com/api/?name" + payload.name() + "+" + payload.surname());

        newOwner.setType(Type.OWNER);

        //        mailgunSender.sendRegistrationEmail(newUser);
        return usersDAO.save(newOwner);
    }

}