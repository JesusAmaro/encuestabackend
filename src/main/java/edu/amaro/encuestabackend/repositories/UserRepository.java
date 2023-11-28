package edu.amaro.encuestabackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.amaro.encuestabackend.entities.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
    
    public UserEntity findByEmail(String email);

    public UserEntity findById(long id);
}
