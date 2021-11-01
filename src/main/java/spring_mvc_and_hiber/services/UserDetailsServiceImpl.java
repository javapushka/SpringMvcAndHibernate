package spring_mvc_and_hiber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_mvc_and_hiber.dao.UserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import spring_mvc_and_hiber.models.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    // «Пользователь» – это просто Object. В большинстве случаев он может быть
    //  приведен к классу UserDetails.
    // Для создания UserDetails используется интерфейс UserDetailsService, с единственным методом:
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserByName(s);
        if (user == null){
            throw new UsernameNotFoundException(String.format("Username '%s' not found", s));
        }
        return user;
    }
}
