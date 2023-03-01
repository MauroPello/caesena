using riusco_mvc.Models;
using Microsoft.EntityFrameworkCore;

namespace riusco_mvc.Data
{
    public class MainDbContext : DbContext
    {
        public DbSet<UserDTO> Users { get; set; }
        public DbSet<ProductDTO> Products { get; set; }
        public DbSet<TransactionDTO> Transactions { get; set; }
        public MainDbContext(DbContextOptions<MainDbContext> options) : base(options) { }
    }
}