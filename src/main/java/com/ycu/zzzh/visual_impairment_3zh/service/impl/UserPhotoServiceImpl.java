package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserPhoto;
import generator.service.UserPhotoService;
import generator.mapper.UserPhotoMapper;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【user_photo】的数据库操作Service实现
* @createDate 2022-05-30 17:54:40
*/
@Service
public class UserPhotoServiceImpl extends ServiceImpl<UserPhotoMapper, UserPhoto>
    implements UserPhotoService{

}




