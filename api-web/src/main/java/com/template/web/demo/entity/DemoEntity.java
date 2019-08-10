package com.template.web.demo.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.nerv.core.mvc.entity.mybatis.StdBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * CRUD示例
 */
@Data
@ApiModel
@Alias("demo")
@TableName("Z_DEMO")
@EqualsAndHashCode(callSuper = true)
public class DemoEntity extends StdBaseEntity {
    @ApiModelProperty(value = "示例字段")
    @TableField(condition = SqlCondition.LIKE)
    private String demoText;
}