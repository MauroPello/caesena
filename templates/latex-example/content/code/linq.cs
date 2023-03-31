using Microsoft.Linq;
using Microsoft.EntityFrameworkCore;
using riusco_mvc.Models;

// query che ottiene il prodotto con chiave primaria uguale a 2
List<ProductDTO> products = _context.Products.ToList();
ProductDTO product = (from product in products
                        where product.ProductID == 2
                        select product).First();

// equivalente SQLite3
// SELECT * FROM Products WHERE Products.ProductID = 2 LIMIT 1;