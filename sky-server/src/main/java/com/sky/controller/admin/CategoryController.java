package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 菜品分类管理
 */
@Api(tags = "菜品分类管理")
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @ApiOperation("新增菜品分类")
    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增菜品分类：{}", categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     */
    @ApiOperation("新增菜品分类")
    @GetMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO dto) {
        log.info("分页查询");
        PageResult pageResult = categoryService.page(dto);
        return Result.success(pageResult);
    }
}
