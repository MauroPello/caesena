using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace riusco_mvc.Models
{
    public class UserDTO
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        // chiave primaria con autoincrement
        public int UserID { get; set; }
        public string Name { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public string City { get; set; }
        public string Image { get; set; }
        public string Salt { get; set; }
        public string ApiKey { get; set; }
        public int Balance { get; set; }

        // metodi costruttori scritti con overloading
        public UserDTO(string name, string password, string email, string image, string salt, string apiKey, int balance, string city)
        {
            Name = name;
            Password = password;
            Email = email;
            Image = image;
            Salt = salt;
            ApiKey = apiKey;
            Balance = balance;
            City = city;
        }

        public UserDTO() { }
    }
}