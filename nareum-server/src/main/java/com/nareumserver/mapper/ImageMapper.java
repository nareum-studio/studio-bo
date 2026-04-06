package com.nareumserver.mapper;

import com.nareumserver.domain.Image;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    void upload(Image image);

    Image findById(Long ImageId);

    List<Image> findByIds(List<Long> ImageIds);

    void deleteByIds(List<Long> ImageIds);

    List<Image> getImageList(String category);
}
