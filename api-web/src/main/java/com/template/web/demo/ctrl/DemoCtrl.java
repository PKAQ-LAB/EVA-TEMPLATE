package com.template.web.demo.ctrl;

import com.template.web.demo.entity.DemoEntity;
import com.template.web.demo.service.DemoService;
import io.nerv.core.mvc.ctrl.BaseCtrl;
import io.nerv.core.mvc.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例CRUD
 */
@Api(description = "CRUD示例",tags = "示例")
@RestController
@RequestMapping("/demo")
public class DemoCtrl extends BaseCtrl<DemoService, DemoEntity> {

    @PostMapping("/aes")
    @ApiOperation(value = "AES加密",response = Response.class)
    public Response aes(@ApiParam(name ="demoEntity", value = "要进行校验的参数")
                                @RequestBody DemoEntity demoEntity){
        String aesStr = this.service.aes(demoEntity.getDemoText());
        return success(aesStr);
    }
}
