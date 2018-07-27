using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Library.API.Models
{
    //DTO for creating resource
    public class ScoreForCreationDto
    {
        //[Required]
        public DateTimeOffset Timestamp { get; set; }
        public int InstanceScore { get; set; }
        public string IMEI { get; set; }
        public int ScreenLock { get; set; }
        public int OS { get; set; }
        public int UnknownSources { get; set; }
        public int HarmfulApps { get; set; }
        public int DevOpt { get; set; }
        public int Integrity { get; set; }
        public int Compatibility { get; set; }
    }
}
