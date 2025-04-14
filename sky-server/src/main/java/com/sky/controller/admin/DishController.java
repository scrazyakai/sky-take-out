package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDto) {
        log.info("新增菜品:{}",dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:{}",dishPageQueryDTO);
        PageResult pageResult =  dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @ApiOperation("批量删除菜品信息")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品信息:{}",ids);
        dishService.deleteByIds(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("通过Id查询餐品信息:{}",id);
        DishVO dishVo =  dishService.getByIdWithFlavor(id);
        return Result.success(dishVo);
    }
    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result update(@RequestBody DishDTO dishDto) {
        log.info("修改菜品信息:{}",dishDto.toString());
        dishService.update(dishDto);
        return Result.success();
    }

}
