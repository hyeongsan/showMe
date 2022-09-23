package com.cos.showme.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.showme.domain.image.Image;

//JpaRepository를 상속했으면 어노테이션이 없어도 Ioc등록이 자동으로된다.
public interface UserRepository extends JpaRepository<User,Integer> {
	// JPA query method
	User findByUsername(String username);
	
	@Query(value="select * from user where username=:username",nativeQuery=true)
	User mfindByUsername(String username);
	
	@Query(value="select * from user where username!='admin'",nativeQuery=true)
	List<User> mUserList();
	
	@Query(value="UPDATE user SET isAuth=true WHERE id=:userId",nativeQuery=true)
	void mUserAuthSign(int userId);

}
