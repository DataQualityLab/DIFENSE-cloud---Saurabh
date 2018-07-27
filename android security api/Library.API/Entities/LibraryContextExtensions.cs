using System;
using System.Collections.Generic;

namespace Library.API.Entities
{
    // Add testing data
    public static class LibraryContextExtensions
    {
        public static void EnsureSeedDataForContext(this LibraryContext context)
        {
            // first, clear the database.  This ensures we can always start 
            // fresh with each demo.  Not advised for production environments
            /*
             context.Users.RemoveRange(context.Users);
             context.SaveChanges();

             // init seed data
             var users = new List<User>()
             {
                 new User()
                 {
                      Id = new Guid("25320c5e-f58a-4b1f-b63a-8ee07a840bdf"),
                      FirstName = "Stephen",
                      LastName = "King",
                      UserName = "one",
                      Password = Helpers.GenerateHash.encryptPassword("Horror"),
                      IMEI = "1234566554798120244",
                      UserType = false,
                      Scores = new List<Score>()
                      {
                          new Score()
                          {
                              Id = new Guid("c7ba6add-09c4-45f8-8dd0-eaca221e5d93"),
                              TimeStamp = (new DateTime(2000, 9, 21)),
                              InstanceScore = 4,
                              IMEI = "123456",
                              ScoreComparison = "1/2",
                              LatestScore = true,
                              ScreenLock = 1,
                              OS = 2,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 0,
                              Integrity= 0,
                              Compatibility =0

                          },
                          new Score()
                          {
                              Id = new Guid("a3749477-f823-4124-aa4a-fc9ad5e79cd6"),
                              TimeStamp = (new DateTime(2010, 1, 3)),
                              InstanceScore = 3,
                              IMEI = "123456",
                              ScreenLock = 1,
                              OS = 0,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 1,
                              Integrity= 0,
                              Compatibility =0
                          }
                      }
                 },
                 new User()
                 {
                      Id = new Guid("76053df4-6687-4353-8937-b45556748abe"),
                      FirstName = "George",
                      LastName = "RR Martin",
                      UserName = "two",
                      Password =  Helpers.GenerateHash.encryptPassword("Fantasy"),
                      IMEI = "One Plus 5",
                      UserType = false,
                      Scores = new List<Score>()
                      {
                          new Score()
                          {
                              Id = new Guid("447eb762-95e9-4c31-95e1-b20053fbe215"),
                              TimeStamp = (new DateTime(1948, 9, 20)),
                              InstanceScore = 7,
                              IMEI = "One Plus 5",
                              LatestScore = true,
                              ScreenLock = 1,
                              OS = 2,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 0,
                              Integrity= 0,
                              Compatibility =0
                          }
                      }
                 },
                 new User()
                 {
                      Id = new Guid("412c3012-d891-4f5e-9613-ff7aa63e6bb3"),
                      FirstName = "Neil",
                      LastName = "Gaiman",
                      UserName = "three",
                      Password = Helpers.GenerateHash.encryptPassword("Fantasy"),
                      IMEI = "Moto X",
                      UserType = false,
                      Scores = new List<Score>()
                      {
                          new Score()
                          {
                              Id = new Guid("9edf91ee-ab77-4521-a402-5f188bc0c577"),
                              TimeStamp = (new DateTime(1960, 11, 10)),
                              InstanceScore = 6,
                              IMEI = "Moto X",
                              LatestScore = true,
                              ScreenLock = 1,
                              OS = 2,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 0,
                              Integrity= 0,
                              Compatibility =0
                          }
                      }
                 },
                 new User()
                 {
                      Id = new Guid("578359b7-1967-41d6-8b87-64ab7605587e"),
                      FirstName = "Tom",
                      LastName = "Lanoye",
                      UserName = "four",
                      Password = Helpers.GenerateHash.encryptPassword("Various"),
                      IMEI = "Micromax A1",
                      UserType = false,
                      Scores = new List<Score>()
                      {
                          new Score()
                          {
                              Id = new Guid("01457142-358f-495f-aafa-fb23de3d67e9"),
                              TimeStamp = (new DateTime(1958, 8, 27)),
                              InstanceScore = 5,
                              IMEI = "Micromax A1",
                              LatestScore = true,
                              ScreenLock = 1,
                              OS = 2,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 0,
                              Integrity= 0,
                              Compatibility =0
                          }
                      }
                 },
                 new User()
                 {
                      Id = new Guid("f74d6899-9ed2-4137-9876-66b070553f8f"),
                      FirstName = "Douglas",
                      LastName = "Adams",
                      UserName = "five",
                      Password = Helpers.GenerateHash.encryptPassword("Science fiction"),
                      IMEI = "Dabba phone",
                      UserType = false,
                      Scores = new List<Score>()
                      {
                          new Score()
                          {
                              Id = new Guid("e57b605f-8b3c-4089-b672-6ce9e6d6c23f"),  
                              TimeStamp = (new DateTime(1952, 3, 11)),
                              InstanceScore = 1,
                              IMEI = "Dabba phone",
                              LatestScore = true,
                              ScreenLock = 1,
                              OS = 2,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 0,
                              Integrity= 0,
                              Compatibility =0
                          }
                      }
                 },
                 new User()
                 {
                      Id = new Guid("a1da1d8e-1988-4634-b538-a01709477b77"),
                      FirstName = "Jens",
                      LastName = "Lapidus",
                      UserName = "six",
                      Password = Helpers.GenerateHash.encryptPassword("Thriller"),
                      IMEI = "Samsung S8",
                      UserType = true,
                      Scores = new List<Score>()
                      {
                          new Score()
                          {
                              Id = new Guid("1325360c-8253-473a-a20f-55c269c20407"),
                              TimeStamp = (new DateTime(1974, 5, 24)),
                              InstanceScore = 4,
                              IMEI = "Samsung S8",
                              LatestScore = true,
                              ScreenLock = 1,
                              OS = 2,
                              UnknownSources = 0,
                              HarmfulApps = 1,
                              DevOpt = 0,
                              Integrity= 0,
                              Compatibility =0
                          }
                      }
                 }
             };

             context.Users.AddRange(users);
             context.SaveChanges();*/
        }
    }
}
