using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Library.API.Entities
{
    //This class sends user information back to the client, once the credentials have been validated. 
    public class Uid
    {
        // Constructor
        public Uid(string id, Boolean uType)
        {
            uid = id;
            userType = (uType == false) ? "regular" : "admin";           
        }

        // Shall contain the user id
        public string uid;
        // Shall contain the type of user (Admin/Regular)
        public string userType;
    }
}
