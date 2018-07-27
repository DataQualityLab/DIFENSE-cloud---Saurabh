using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Library.API.Models
{
    public class ScoreDto : LinkedResourceBaseDto
    {
        public Guid Id { get; set; }
        public DateTime Timestamp { get; set; }
        public int InstanceScore { get; set; }
        public Guid UserId { get; set; }
        public string ScoreComparison { get;  set; }
        public Boolean LatestScore { get; set; }
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
