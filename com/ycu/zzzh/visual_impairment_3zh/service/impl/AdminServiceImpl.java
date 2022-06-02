package visual_impairment_3zh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import visual_impairment_3zh.model.Admin;
import visual_impairment_3zh.service.AdminService;
import visual_impairment_3zh.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【admin(管理员信息)】的数据库操作Service实现
* @createDate 2022-04-09 16:28:04
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




