using Microsoft.EntityFrameworkCore.Migrations;
using System;

namespace Library.API.Migrations
{
    public partial class InitialMigration : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    FirstName = table.Column<string>(maxLength: 50, nullable: false),
                    LastName  = table.Column<string>(maxLength: 50, nullable: false),
                    UserName  = table.Column<string>(maxLength: 50, nullable: false),
                    Password  = table.Column<string>(maxLength: 50, nullable: false),
                    IMEI      = table.Column<string>(maxLength: 50, nullable: false),
                    UserType  = table.Column<Boolean>(nullable: false),
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Scores",
                columns: table => new
                {
                    Id              = table.Column<Guid>(nullable: false),
                    UserId          = table.Column<Guid>(nullable: false),
                    TimeStamp       = table.Column<DateTime>(nullable: true),
                    InstanceScore   = table.Column<int> (maxLength: 10,nullable: true),
                    ScoreComparison = table.Column<string> (maxLength: 200, nullable: true),
                    LatestScore     = table.Column<Boolean>(nullable: true),
                    IMEI            = table.Column<string>(maxLength: 50, nullable: false),
                    ScreenLock      = table.Column<string>(maxLength: 1, nullable: true),
                    OS              = table.Column<string>(maxLength: 1, nullable: true),
                    UnknownSources  = table.Column<string>(maxLength: 1, nullable: true),
                    HarmfulApps     = table.Column<string>(maxLength: 1, nullable: true),
                    DevOpt          = table.Column<string>(maxLength: 1, nullable: true),
                    Integrity       = table.Column<string>(maxLength: 1, nullable: true),
                    Compatibility   = table.Column<string>(maxLength: 1, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Scores", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Scores_Users_UserId",
                        column: x => x.UserId,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Scores_UserId",
                table: "Scores",
                column: "UserId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Scores");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
