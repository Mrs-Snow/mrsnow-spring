package com.mrsnow.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrsnow.utils.JO;
import com.mrsnow.utils.PJO;
import com.mrsnow.utils.QueryWrap;
import com.mrsnow.utils.R;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author MrSnow *** dz
 * @CreateTime: 2023-04-05  14:55
 **/
@Data
@SuppressWarnings("all")
public class SuperController<Service extends IService<Entity>,Entity> {

    @Autowired
    protected Service superService;
    protected Entity entity;



    @PostMapping("/save")
    @ResponseBody
    public R<Entity> save(@RequestBody Entity entity){
        superService.save(entity);
        return R.success(entity);
    }

    @PostMapping("/update")
    @ResponseBody
    public R<Entity> update(@RequestBody Entity entity){
        superService.updateById(entity);
        return R.success(entity);
    }

    @PostMapping("/page")
    @ResponseBody
    public R page(@RequestBody PJO<Entity> pjo){
        Page<Entity> page = new Page<>(pjo.getCurrent(), pjo.getPageSize());
        QueryWrap<Entity> wrap = new QueryWrap<>(pjo.getData());
        Page<Entity> pageResult = superService.page(page, wrap);
        return R.success(pageResult);
    }

    @PostMapping("/delete")
    @ResponseBody
    public R remove(@RequestBody List<Long> ids){
        superService.removeByIds(ids);
        return R.success("删除完成！");
    }

    @PostMapping("/getById")
    @ResponseBody
    public R<Entity> page(@RequestBody JO<Long> jo){
        Entity result = superService.getById(jo.getData());
        return R.success(result);
    }
}
