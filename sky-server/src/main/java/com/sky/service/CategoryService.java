package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    /**
     * 新增分类
     * @param category
     */
    void addCategory(CategoryDTO category);

    /**
     * 分类分页查询
     * @param dto
     */
    PageResult page(CategoryPageQueryDTO dto);
}
