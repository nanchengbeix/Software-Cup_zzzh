using Microsoft.EntityFrameworkCore;
using System.Diagnostics.CodeAnalysis;

namespace News.Manager.EntityFramework
{
    public class NewsDbContext : DbContext
    {
        public NewsDbContext([NotNull] DbContextOptions<NewsDbContext> options) 
            : base(options)
        {
        }
    }
}