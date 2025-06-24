package com.springBoot.reactive.repositories;

import com.springBoot.reactive.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveUserRepository extends ReactiveCrudRepository<User,Integer> {

    //custom methods

    Mono<User> findByEmail(String email);
}
