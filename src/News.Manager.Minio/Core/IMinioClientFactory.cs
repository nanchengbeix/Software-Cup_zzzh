using Minio;

namespace News.Manager.Minio.Core
{
    public interface IMinioClientFactory
    {
        MinioClient CreateClient(string name);
    }
}
