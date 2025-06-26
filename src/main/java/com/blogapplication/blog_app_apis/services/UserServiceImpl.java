package com.blogapplication.blog_app_apis.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapplication.blog_app_apis.config.AppConstants;
import com.blogapplication.blog_app_apis.entities.Role;
import com.blogapplication.blog_app_apis.entities.User;
import com.blogapplication.blog_app_apis.exceptions.ResourceNotFoundException;
import com.blogapplication.blog_app_apis.payloads.UserDto;
import com.blogapplication.blog_app_apis.repositories.RoleRepo;
import com.blogapplication.blog_app_apis.repositories.UserRepo;


@Service
public class UserServiceImpl implements UserService {

    @Autowired  //this userRepo is autowired and userRepo is interface so how object created--- its proxy class's object i.e proxy class of the userRepo interface 
    private UserRepo userRepo; //autowired create object at runtime

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser=this.userRepo.save(user);
        return this.userToDto(savedUser);
        
    }


    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User nUser=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        
        nUser.setName(userDto.getName());
        nUser.setEmail(userDto.getEmail());
        nUser.setAbout(userDto.getAbout());
        nUser.setPassword(userDto.getPassword());

        User updatedUser=this.userRepo.save(nUser);
        UserDto Dto1=this.userToDto(updatedUser);
        return Dto1;
        

    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        return this.userToDto(user);
         
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepo.findAll();  
        List<UserDto> userDtos=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
        return userDtos; 
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
        this.userRepo.delete(user);
    }
    


    // public User dtoToUser(UserDto userDto) {
    //     User user = new User();
    //     user.setId(userDto.getId());
    //     user.setName(userDto.getName());
    //     user.setEmail(userDto.getEmail());
    //     user.setPassword(userDto.getPassword());
    //     user.setAbout(userDto.getAbout());
    //     return user;
    // }
    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    
    //these conversion methods can be directly used from MODELMAPPERS library
    // public UserDto userToDto(User user) {
    //     UserDto userDto = new UserDto();
    //     userDto.setId(user.getId());
    //     userDto.setName(user.getName());
    //     userDto.setEmail(user.getEmail());
    //     userDto.setPassword(user.getPassword());
    //     userDto.setAbout(user.getAbout());

    //     return userDto;
        
    // }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);

        return userDto;
        
    }


    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user=this.modelMapper.map(userDto, User.class);

        // Encoded the password 
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        //roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();//.orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstants.NORMAL_USER));
        
        user.getRoles().add(role); // Add the role to the user's roles

        User newUser = this.userRepo.save(user);


        return this.modelMapper.map(newUser, UserDto.class);
        
    
    }


}
