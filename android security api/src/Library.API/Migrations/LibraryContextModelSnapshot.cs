﻿using Library.API.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.AspNet.Identity.EntityFramework; 
using System;

namespace Library.API.Migrations
{
    [DbContext(typeof(LibraryContext))]
    partial class LibraryContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "1.0.1")
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("Library.API.Entities.User", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("FirstName")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 50);

                    b.Property<string>("LastName")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 50);

                    b.Property<string>("UserName")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 50);

                    b.Property<string>("Password")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 50);

                    b.Property<string>("IMEI")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 50);

                    b.Property<Boolean>("UserType")
                        .IsRequired();

                    b.HasKey("Id");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("Library.API.Entities.Score", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<Guid>("UserId");

                    b.Property<DateTime>("TimeStamp")
                        .IsRequired();

                    b.Property<int>("InstanceScore")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 10);

                    b.Property<string>("ScoreComparison")
                        .HasAnnotation("MaxLength", 200);

                    b.Property<Boolean>("LatestScore");                        

                    b.Property<string>("IMEI")
                        .IsRequired()
                        .HasAnnotation("MaxLength", 50);

                    b.Property<string>("ScreenLock")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.Property<string>("OS")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.Property<string>("UnknownSources")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.Property<string>("HarmfulApps")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.Property<string>("DevOpt")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.Property<string>("Integrity")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.Property<string>("Compatibility")
                       .IsRequired()
                       .HasAnnotation("MaxLength", 1);

                    b.HasKey("Id");

                    b.HasIndex("UserId");

                    b.ToTable("Scores");
                });

            modelBuilder.Entity("Library.API.Entities.Score", b =>
                {
                    b.HasOne("Library.API.Entities.User", "User")
                        .WithMany("Scores")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });
        }
    }
}
