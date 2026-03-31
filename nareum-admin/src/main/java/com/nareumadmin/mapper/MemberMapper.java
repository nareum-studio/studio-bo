package com.nareumadmin.mapper;

import com.nareumadmin.domain.Member;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    void signup(Member member);

    Optional<Member> findById(String id);
}
