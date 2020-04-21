package com.kunal.digib.app.api.users.data;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUserName(String userId);
}
