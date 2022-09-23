package com.cos.showme.domain.image;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image,Integer> {
	
	@Query(value="select * from image where userId IN (select toUserId from subscribe where fromUserI"
			+ "d= :principalId) ORDER BY id DESC", nativeQuery=true)
	Page<Image> mStory(int principalId,Pageable pageable); // 이 mStory를 가져올 때, 3건씩 정렬해서 가져옴 
	
	@Query(value="select i.* from image i inner join (select imageId, count(imageId) likeCount from likes group by imageId) c on i.id = c.imageId order by likecount DESC",nativeQuery=true)
	List<Image> mPopular();
	
	@Query(value="select * from image",nativeQuery=true)
	List<Image> mRecent();
	
	@Query(value="SELECT o.* FROM image o,(SELECT DISTINCT c.imageId FROM image i INNER JOIN comment c ON i.userId != :pageUserId AND c.userId=:pageUserId) a WHERE o.id=a.imageId AND o.userId!=:pageUserId",nativeQuery=true)
	List<Image> mAnswer(int pageUserId);
	
	
	
}
