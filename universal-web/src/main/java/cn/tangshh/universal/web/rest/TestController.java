package cn.tangshh.universal.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tang
 * @version v1.0
 */
@RestController
@RequestMapping("/test")
@Tag(name = "测试控制器", description = "测试控制器......")
public class TestController {

    @GetMapping("/1")
    @Operation(summary = "测试接口1")
    public String test01() {
        return "success";
    }
}
