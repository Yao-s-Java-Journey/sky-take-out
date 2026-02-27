package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    /**
     * 新增菜品分类
     * @param category
     */
    void add(Category category);

    /**
     * 菜品分类分页查询
     * @param dto
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO dto);

    /**
     * 删除菜品分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 编辑菜品分类
     * @param category
     */
    void update(Category category);
}
