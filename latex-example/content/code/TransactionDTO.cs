using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json;

namespace riusco_mvc.Models
{
    public class TransactionDTO
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        // chiave primaria con autoincrement
        public int TransactionID { get; set; }
        [ForeignKey("Product")]
        public int ProductID { get; set; }
        public ProductDTO Product { get; set; }
        [ForeignKey("Owner")]
        public int OwnerID { get; set; }
        public UserDTO Owner { get; set; }
        [ForeignKey("Buyer")]
        public int BuyerID { get; set; }
        public UserDTO Buyer { get; set; }
        public DateTime LastUpdate { get; set; }
        public string State { get; set; }

        // metodi costruttori scritti con overloading 
        public TransactionDTO(int productId, int ownerID, int buyerId, DateTime lastUpdate, string state)
        {
            ProductID = productId;
            OwnerID = ownerID;
            BuyerID = buyerId;
            LastUpdate = lastUpdate;
            State = state;
        }
        
        public TransactionDTO() { }

        // metodo override ToString()
        public override string ToString()
        {
            return JsonSerializer.Serialize(this);
        }
    }
}