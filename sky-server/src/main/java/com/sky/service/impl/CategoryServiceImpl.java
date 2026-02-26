package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
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

    /**
     * 新增分类
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

    @Override
    public PageResult page(CategoryPageQueryDTO dto) {
        // 1. 使用 PageHelper 插件开启分页，配置分页参数
        PageHelper.startPage(dto.getPage(), dto.getPageSize());

        // 2. 调用 mapper 列表查询
        Page<Category> page= categoryMapper.pageQuery(dto);

        return new PageResult(page.getTotal(), page.getResult());
    }
}
