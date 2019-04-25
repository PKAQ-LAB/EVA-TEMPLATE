package com.template.web.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.template.web.demo.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * CRUD示例
 */
@Mapper
@Repository
public interface DemoMapper extends BaseMapper<DemoEntity> {
}
