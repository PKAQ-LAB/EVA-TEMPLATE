package com.template.web.demo.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import io.nerv.core.mvc.entity.PureBaseEntity;

/**
 * CRUD示例
 */
@Data
@ApiModel
@Alias("demo")
@TableName("Z_DEMO")
@EqualsAndHashCode(callSuper = true)
public class DemoEntity extends PureBaseEntity {
    @ApiModelProperty(value = "示例字段")
    @TableField(condition = SqlCondition.LIKE)
    private String demoText;
}