package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserFace;
import generator.service.UserFaceService;
import generator.mapper.UserFaceMapper;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【user_face(用户人脸表)】的数据库操作Service实现
* @createDate 2022-06-10 17:00:47
*/
@Service
public class UserFaceServiceImpl extends ServiceImpl<UserFaceMapper, UserFace>
    implements UserFaceService{

}




