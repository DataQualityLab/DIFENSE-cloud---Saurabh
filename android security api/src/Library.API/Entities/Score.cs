using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Library.API.Entities
{
    public class Score
    {
        [Key]
        public Guid Id { get; set; }

        [Required]
        public DateTime TimeStamp { get; set; }

        [Required]
        [MaxLength(10)]
        public int InstanceScore { get; set; }

        [MaxLength(200)]
        public string ScoreComparison { get; set; }

        public Boolean LatestScore { get; set; }

        [ForeignKey("UserId")]
        public Guid UserId { get; set; }
        //public User User { get; set; }

        [Required]
        [MaxLength(50)]
        public string IMEI { get; set; }

        [MaxLength(1)]
        public int ScreenLock { get; set; }

        [MaxLength(1)]
        public int OS { get; set; }

        [MaxLength(1)]
        public int UnknownSources { get; set; }

        [MaxLength(1)]
        public int HarmfulApps { get; set; }

        [MaxLength(1)]
        public int DevOpt { get; set; }

        [MaxLength(1)]
        public int Integrity { get; set; }

        [MaxLength(1)]
        public int Compatibility { get; set; }

    }
}
