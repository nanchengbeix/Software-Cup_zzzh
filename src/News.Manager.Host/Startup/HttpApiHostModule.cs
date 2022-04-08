using Autofac;
using Microsoft.Extensions.DependencyModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.Loader;
using System.Text;
using System.Threading.Tasks;

namespace News.Manager.Api.Startup
{
    public class HttpApiHostModule : Autofac.Module
    {
        protected override void Load(ContainerBuilder builder)
        {
            // 注入所有模块
            var libs = DependencyContext.Default
                .CompileLibraries
                .Where(lib => lib.Name.StartsWith("News"))
                .ToList();
            foreach (var lib in libs)
            {
                builder.RegisterAssemblyModules(AssemblyLoadContext.Default.LoadFromAssemblyName(new System.Reflection.AssemblyName(lib.Name)));
            }

            // 自动注入 Repository
            var iRepositories = Assembly.Load("News.Manager.Domain");
            var repositories = Assembly.Load("News.Manager.EntityFramework");
            builder.RegisterAssemblyTypes(iRepositories, repositories)
                .Where(repo => repo.Name.EndsWith("Repository"))
                .AsImplementedInterfaces();

            // 自动注入 Service
            var iServices = Assembly.Load("News.Manager.Application.Contracts");
            var services = Assembly.Load("News.Manager.Application");
            builder.RegisterAssemblyTypes(iServices, services)
                .Where(service => service.Name.EndsWith("Service"))
                .AsImplementedInterfaces();
            
            // 自动注入 Manager
        }
    }
}
