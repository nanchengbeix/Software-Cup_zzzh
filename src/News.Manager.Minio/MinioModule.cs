using Autofac;
using News.Manager.Minio.Core;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace News.Manager.Minio
{
    public class MinioModule : Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            builder.RegisterType<MinioOptions>().SingleInstance();
            builder.RegisterType<MinioClientFactory>().As<IMinioClientFactory>().SingleInstance();
            builder.RegisterAssemblyTypes(ThisAssembly)
                .Where(manager => manager.Name.EndsWith("Manager"))
                .InstancePerRequest();
        }
    }
}
