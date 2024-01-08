package com.mno.business.user.repo;

import com.mno.business.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);

    Optional<User> findByGmail(String gmail);



    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.user_img = ?2 WHERE u.id= ?1",nativeQuery = true)
    void updateImage(Long id,String user_img);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.gmail = ?2 WHERE u.id= ?1",nativeQuery = true)
    void changeEmail(Long id,String gmail);

    Optional<User> findByNameOrGmail(String name,String gmail);


    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.name = ?2 WHERE u.id= ?1",nativeQuery = true)
    void changeName(Long id,String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.password = ?2 WHERE u.id= ?1",nativeQuery = true)
    void changePassword(Long id,String password);



    @Modifying
    @Transactional
    @Query(value = "UPDATE user u SET u.address = ?2 WHERE u.id= ?1",nativeQuery = true)
    void changeAddress(Long id,String address);


}
