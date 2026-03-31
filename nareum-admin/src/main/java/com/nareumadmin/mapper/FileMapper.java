package com.nareumadmin.mapper;

import com.nareumadmin.domain.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {

    void upload(File file);
}
