using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyModel;
using News.Manager.Minio;

namespace News.Manager.Api.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class HomeController : ControllerBase
    {
        [HttpGet]
        public IActionResult Home()
        {
            return Ok();
        }
    }
}