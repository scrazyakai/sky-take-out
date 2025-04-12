package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "菜品相关接口")
@RestController()
@RequestMapping("/admin/dish")
public class DishController {
    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @Autowired
    DishService dishService;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDto) {
        log.info("新增菜品:{}",dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return Result.success();
    }
}
