using Microsoft.EntityFrameworkCore;
using riusco_mvc.Models;

// query asincrona che ottiene il prodotto con chiave primaria uguale a 2
ProductDTO product = await _context.Products.FindAsync(2);

// equivalente SQLite3
// SELECT * FROM Products WHERE Products.ProductID = 2 LIMIT 1;


// query che ottiene l'utente con email uguale mauro.pellonara@gmail.com, nel caso piu' utenti abbiano la stessa email ritorna un errore
UserDTO user = _context.Users.Single(x => x.Email == "mauro.pellonara@gmail.com");

// equivalente SQLite3
// SELECT * FROM Users WHERE Users.Email = "mauro.pellonara@gmail.com" LIMIT 1;