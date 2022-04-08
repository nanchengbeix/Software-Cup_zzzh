using Autofac;
using Autofac.Extensions.DependencyInjection;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyModel;
using News.Manager.EntityFramework;
using System.Reflection;
using System.Runtime.Loader;

namespace News.Manager.Api.Startup
{
    public class Startup
    {
        public IConfiguration Configuration { get; private set; }
        public ILifetimeScope AutofacContainer { get; private set; }
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }
        
        // This is the default if you don't have an environment specific method.
        public void ConfigureServices(IServiceCollection services)
        {
            // Add things to the service collection.
            services.AddControllers().AddNewtonsoftJson().AddControllersAsServices(); 
            services.AddCors(options =>
            {
                options.AddPolicy("cors",
                    builder =>
                    {
                        builder.AllowAnyHeader()
                               .AllowAnyMethod()
                               .AllowAnyOrigin();
                    });
            });
            
            services.AddSwaggerGen();

            services.AddDbContext<NewsDbContext>(o =>
                o.UseMySql(Configuration.GetConnectionString("MySQL"), MySqlServerVersion.LatestSupportedServerVersion)
            );
        }

        // This is the default if you don't have an environment specific method.
        public void ConfigureContainer(ContainerBuilder builder)
        {
            // Add things to the Autofac ContainerBuilder.
            var libs = DependencyContext.Default.CompileLibraries.Where(lib => lib.Name.StartsWith("News")).ToList();
            foreach (var lib in libs)
            {
                builder.RegisterAssemblyModules(AssemblyLoadContext.Default.LoadFromAssemblyName(new AssemblyName(lib.Name)));
            };
        }

        // This is the default if you don't have an environment specific method.
        public void Configure(IApplicationBuilder app, ILoggerFactory loggerFactory)
        {
            AutofacContainer = app.ApplicationServices.GetAutofacRoot();

            app.UseCors("cors");
            app.UseSwagger();
            app.UseSwaggerUI();
            //app.MapControllers();
            app.UseRouting();
            app.UseAuthorization();
            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }

        // This only gets called if your environment is Staging. The
        // default Configure won't be automatically called if this one is called.
        public void ConfigureStaging(IApplicationBuilder app, ILoggerFactory loggerFactory)
        {
            
        }
    }
}
