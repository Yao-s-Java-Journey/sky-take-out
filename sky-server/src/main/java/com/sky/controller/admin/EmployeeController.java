package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "员工管理")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return EmployeeLoginVO
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        // 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     */
    @ApiOperation("员工添加")
    @PostMapping
    public Result addEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工，员工数据：{}", employeeDTO);
        employeeService.addEmp(employeeDTO);
        return Result.success();
    }

    /**
     * 查询员工分页列表
     */
    @ApiOperation("员工分页列表查询")
    @GetMapping("/page")
    public Result<PageResult> page(EmployeePageQueryDTO dto) {
        log.info("查询员工分页列表：{}", dto);
        PageResult pageResult = employeeService.page(dto);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用员工账号
     */
    @ApiOperation("员工账号启用禁用")
    @PostMapping("/status/{status}")
    public Result enableOrDisable(@PathVariable Integer status, Long id) {
        log.info("员工账号启用禁用：状态={}, id={}", status, id);
        employeeService.enableOrDisable(status, id);
        return Result.success();
    }

    /**
     * 根据 id 查询员工详情
     */
    @ApiOperation("员工详情查询")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("查询员工详情：id={}", id);
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }
}
