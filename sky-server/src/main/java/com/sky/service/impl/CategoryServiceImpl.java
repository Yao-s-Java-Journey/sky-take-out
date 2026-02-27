package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 新增菜品分类
     *
     * @param dto
     */
    @Override
    public void addCategory(CategoryDTO dto) {
        // 创建一个Category对象，用于封装数据
        Category category = new Category();

        // 将 dto 对象属性拷贝给 category 对象
        BeanUtils.copyProperties(dto, category);

        // 补全其他属性
        category.setStatus(StatusConstant.ENABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        // 从上下文（threadLocal）获取当前登录用户的 id
        Long currUserId = BaseContext.getCurrentId();
        category.setCreateUser(currUserId);
        category.setUpdateUser(currUserId);

        // 插入数据库
        categoryMapper.add(category);
    }

    /**
     * 菜品分类分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO dto) {
        // 1. 使用 PageHelper 插件开启分页，配置分页参数
        PageHelper.startPage(dto.getPage(), dto.getPageSize());

        // 2. 调用 mapper 列表查询
        Page<Category> page = categoryMapper.pageQuery(dto);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除菜品分类
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        // 1. 判断当前分类是否关联了菜品，如果关联且菜品数 > 0，则无法删除
        Integer dishCount = dishMapper.countByCategoryId(id);
        if (dishCount > 0) {
            // 当前分类关联了菜品，无法删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        // 2. 判断当前分类是否关联了套餐，如果关联且套餐数 > 0，则无法删除
        Integer setmealCount = setmealMapper.countByCategoryId(id);
        if (setmealCount > 0) {
            // 当前分类关联了菜品，无法删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        // 3. 删除分类
        categoryMapper.deleteById(id);
    }

    /**
     * 编辑菜品分类
     *
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        // 申明 category 对象，拷贝 dto 属性
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // 设置修改时间、修改人
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }
}
