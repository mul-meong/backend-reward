package com.mulmeong.reward.point.infrastructure;

import com.mulmeong.reward.point.domain.document.MemberPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberPointRepository extends MongoRepository<MemberPoint, String> {

    Optional<MemberPoint> findByMemberUuid(String memberUuid);
}
