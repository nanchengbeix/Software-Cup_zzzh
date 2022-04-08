using News.Manager.Minio.Core;

namespace News.Manager.Minio
{
    public class MinioManager
    {
        private static readonly string BucketName = "news.images";
        private readonly MinioOptions _minioOptions;

        public MinioManager(MinioOptions minioOptions)
        {
            _minioOptions = minioOptions;
        }


        public string GetFileUrl(string fileFullName)
        {
            return "http://" + _minioOptions.Endpoint + "/" + BucketName + "/" + fileFullName;
        }
    }
}