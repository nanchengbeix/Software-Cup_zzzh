package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.InfoLog;
import generator.service.InfoLogService;
import generator.mapper.InfoLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【info_log】的数据库操作Service实现
* @createDate 2022-04-13 15:02:59
*/
@Service
public class InfoLogServiceImpl extends ServiceImpl<InfoLogMapper, InfoLog>
    implements InfoLogService{

}




