using Microsoft.Extensions.Options;
using Minio;

namespace News.Manager.Minio.Core
{
    public class MinioClientFactory : IMinioClientFactory
    {
        private readonly IOptionsMonitor<MinioOptions> optionsMonitor;

        public MinioClientFactory(IOptionsMonitor<MinioOptions> optionsMonitor)
        {
            this.optionsMonitor = optionsMonitor;
        }

        public MinioClient CreateClient(string name)
        {
            var options = optionsMonitor.Get(name);

            var client = new MinioClient()
                .WithEndpoint(options.Endpoint)
                .WithRegion(options.Region)
                .WithCredentials(options.AccessKey, options.SecretKey)
                .WithSessionToken(options.SessionToken)
                .Build();

            options.OnClientConfiguration?.Invoke(client);

            return client;
        }
    }
}
