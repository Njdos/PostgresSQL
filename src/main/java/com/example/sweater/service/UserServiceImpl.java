package com.example.sweater.service;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    @Override
    public boolean searchUserName(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) { return false; }
        return true;
    }

    @Override
    public boolean searchUserNameWithoutUser(String username, Long id) {
        User userFromDb = userRepo.findByUsernameAndIdNot(username,id);
        if (userFromDb != null) { return false; }
        return true;
    }

    @Override
    public boolean searchPhoneWithoutUser(String phone, Long id) {
        User userFromDb = userRepo.findByPhoneAndIdNot(phone,id);
        if (userFromDb != null) { return false; }
        return true;
    }

    @Override
    public boolean searchEmailWithoutUser(String email, Long id) {
        User userFromDb = userRepo.findByEmailAndIdNot(email,id);
        if (userFromDb != null) { return false; }
        return true;
    }

    @Override
    public boolean searchEmail(User user) {
        User userFromDb = userRepo.findByEmail(user.getEmail());
        if (userFromDb != null) { return false; }
        return true;
    }

    @Override
    public boolean searchPhone(User user) {
        User userFromDb = userRepo.findByPhone(user.getPhone());
        if (userFromDb != null) { return false; }
        return true;
    }

    @Override
    public void save(User user) {
            userRepo.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public Set<User> findById(long id) {
        return userRepo.findById(id);
    }

    @Override
    public void update(User user)  {
        userRepo.save(user);
    }

}


