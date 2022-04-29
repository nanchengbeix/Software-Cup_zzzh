package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.NewsAuthor;
import generator.service.NewsAuthorService;
import generator.mapper.NewsAuthorMapper;
import org.springframework.stereotype.Service;

/**
* @author 胡富国
* @description 针对表【news_author(新闻作者表)】的数据库操作Service实现
* @createDate 2022-04-29 12:32:55
*/
@Service
public class NewsAuthorServiceImpl extends ServiceImpl<NewsAuthorMapper, NewsAuthor>
    implements NewsAuthorService{

}




