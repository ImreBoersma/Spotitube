USE [master]
GO
/****** Object:  Database [SpotitubeTest]    Script Date: 14/10/2021 11:02:33 ******/
CREATE DATABASE [SpotitubeTest]
    CONTAINMENT = NONE
    ON PRIMARY
    ( NAME = N'SpotitubeTest', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\SpotitubeTest.mdf' , SIZE = 8192 KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536 KB )
    LOG ON
    ( NAME = N'SpotitubeTest_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\SpotitubeTest_log.ldf' , SIZE = 8192 KB , MAXSIZE = 2048 GB , FILEGROWTH = 65536 KB )
WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [SpotitubeTest] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
    begin
        EXEC [SpotitubeTest].[dbo].[sp_fulltext_database] @action = 'enable'
    end
GO
ALTER DATABASE [SpotitubeTest] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [SpotitubeTest] SET ANSI_NULLS OFF
GO
ALTER DATABASE [SpotitubeTest] SET ANSI_PADDING OFF
GO
ALTER DATABASE [SpotitubeTest] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [SpotitubeTest] SET ARITHABORT OFF
GO
ALTER DATABASE [SpotitubeTest] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [SpotitubeTest] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [SpotitubeTest] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [SpotitubeTest] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [SpotitubeTest] SET CURSOR_DEFAULT GLOBAL
GO
ALTER DATABASE [SpotitubeTest] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [SpotitubeTest] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [SpotitubeTest] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [SpotitubeTest] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [SpotitubeTest] SET DISABLE_BROKER
GO
ALTER DATABASE [SpotitubeTest] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [SpotitubeTest] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [SpotitubeTest] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [SpotitubeTest] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [SpotitubeTest] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [SpotitubeTest] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [SpotitubeTest] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [SpotitubeTest] SET RECOVERY FULL
GO
ALTER DATABASE [SpotitubeTest] SET MULTI_USER
GO
ALTER DATABASE [SpotitubeTest] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [SpotitubeTest] SET DB_CHAINING OFF
GO
ALTER DATABASE [SpotitubeTest] SET FILESTREAM ( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [SpotitubeTest] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [SpotitubeTest] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [SpotitubeTest] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
EXEC sys.sp_db_vardecimal_storage_format N'SpotitubeTest', N'ON'
GO
ALTER DATABASE [SpotitubeTest] SET QUERY_STORE = OFF
GO
USE [SpotitubeTest]
GO
/****** Object:  Table [dbo].[playlist]    Script Date: 14/10/2021 11:02:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[playlist]
(
    [id]            [int] IDENTITY (1,1) NOT NULL,
    [owner_user_id] [int]                NOT NULL,
    [name]          [varchar](50)        NOT NULL,
    CONSTRAINT [PK_playlist] PRIMARY KEY CLUSTERED
        (
         [id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[playlist_tracks]    Script Date: 14/10/2021 11:02:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[playlist_tracks]
(
    [playlist_id]       [int]     NOT NULL,
    [track_id]          [int]     NOT NULL,
    [offline_available] [tinyint] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[track]    Script Date: 14/10/2021 11:02:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[track]
(
    [id]               [int] IDENTITY (1,1) NOT NULL,
    [title]            [varchar](50)        NOT NULL,
    [performer]        [varchar](50)        NOT NULL,
    [duration]         [int]                NOT NULL,
    [album]            [varchar](50)        NOT NULL,
    [play_count]       [int]                NOT NULL,
    [publication_date] [date]               NULL,
    [description]      [text]               NULL,
    CONSTRAINT [PK_track] PRIMARY KEY CLUSTERED
        (
         [id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user]    Script Date: 14/10/2021 11:02:33 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user]
(
    [id]         [int] IDENTITY (1,1) NOT NULL,
    [username]   [varchar](50)        NOT NULL,
    [password]   [varchar](128)       NOT NULL,
    [first_name] [varchar](50)        NOT NULL,
    [last_name]  [varchar](50)        NOT NULL,
    [token]      [varchar](128)       NULL,
    CONSTRAINT [PK_user] PRIMARY KEY CLUSTERED
        (
         [id] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[playlist] ON
GO
SET IDENTITY_INSERT [dbo].[playlist] ON
INSERT [dbo].[playlist] ([id], [owner_user_id], [name])
VALUES (1, 20, N'Top1000')
SET IDENTITY_INSERT [dbo].[playlist] OFF
GO
SET IDENTITY_INSERT [dbo].[playlist] ON
INSERT [dbo].[playlist] ([id], [owner_user_id], [name])
VALUES (2, 20, N'Deutsche schlager')
SET IDENTITY_INSERT [dbo].[playlist] OFF
GO
SET IDENTITY_INSERT [dbo].[playlist] ON
INSERT [dbo].[playlist] ([id], [owner_user_id], [name])
VALUES (7, 20, N'Rock')
SET IDENTITY_INSERT [dbo].[playlist] OFF
GO
INSERT [dbo].[playlist] ([id], [owner_user_id], [name])
VALUES (8, 21, N'Ska')
GO
SET IDENTITY_INSERT [dbo].[playlist] OFF
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 1, 0)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 1, 0)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (2, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 1, 0)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (2, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (2, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 1, 0)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (1, 2, 1)
GO
INSERT [dbo].[playlist_tracks] ([playlist_id], [track_id], [offline_available])
VALUES (2, 2, 1)
GO
SET IDENTITY_INSERT [dbo].[track] ON
GO
INSERT [dbo].[track] ([id], [title], [performer], [duration], [album], [play_count], [publication_date], [description])
VALUES (1, N'Superman', N'Goldfinger', 185, N'Hang-ups', 2, CAST(N'1998-08-06' AS Date), NULL)
GO
INSERT [dbo].[track] ([id], [title], [performer], [duration], [album], [play_count], [publication_date], [description])
VALUES (2, N'Goon Squad', N'Ripe', 316, N'Hey Hello', 5, CAST(N'2015-01-01' AS Date), NULL)
GO
SET IDENTITY_INSERT [dbo].[track] OFF
GO
SET IDENTITY_INSERT [dbo].[user] ON
GO
INSERT [dbo].[user] ([id], [username], [password], [first_name], [last_name], [token])
VALUES (20, N'imreboersma',
        N'0xB109F3BBBC244EB82441917ED06D618B9008DD09B3BEFD1B5E07394C706A8BB980B1D7785E5976EC049B46DF5F1326AF5A2EA6D103FD07C95385FFAB0CACBC',
        N'imre', N'boersma', N'53F8B70B-F0C2-4092-829A-65516AF24CCA')
GO
INSERT [dbo].[user] ([id], [username], [password], [first_name], [last_name], [token])
VALUES (21, N'testuser',
        N'0xB109F3BBBC244EB82441917ED06D618B9008DD09B3BEFD1B5E07394C706A8BB980B1D7785E5976EC049B46DF5F1326AF5A2EA6D103FD07C95385FFAB0CACBC',
        N'test', N'user', N'17D8814D-556F-41B0-9411-D9FBCF0DFB5B')
GO
SET IDENTITY_INSERT [dbo].[user] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [token_UNIQUE]    Script Date: 14/10/2021 11:02:33 ******/
CREATE UNIQUE NONCLUSTERED INDEX [token_UNIQUE] ON [dbo].[user]
    (
     [token] ASC
        ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [username_UNIQUE]    Script Date: 14/10/2021 11:02:33 ******/
CREATE UNIQUE NONCLUSTERED INDEX [username_UNIQUE] ON [dbo].[user]
    (
     [username] ASC
        ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[playlist_tracks]
    ADD CONSTRAINT [DF_Table1_offline_available] DEFAULT ((0)) FOR [offline_available]
GO
ALTER TABLE [dbo].[track]
    ADD CONSTRAINT [DF_track_play_count] DEFAULT ((0)) FOR [play_count]
GO
USE [master]
GO
ALTER DATABASE [SpotitubeTest] SET READ_WRITE
GO
