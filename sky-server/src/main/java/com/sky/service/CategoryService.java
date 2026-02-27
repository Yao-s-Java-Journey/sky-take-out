package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 新增分类
     *
     * @param category
     */
    void addCategory(CategoryDTO category);

    /**
     * 分类分页查询
     *
     * @param dto
     */
    PageResult page(CategoryPageQueryDTO dto);

    /**
     * 删除分类
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 编辑分类
     *
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用、禁用菜品分类
     *
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据 type 查询启用的分类
     *
     * @param type
     * @return
     */
    List<Category> getEnableCategoriesByType(Integer type);
}
