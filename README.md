# Game Store REST API

The Game Store REST API is a robust and flexible web service designed to facilitate seamless communication between game
developers, retailers, and gamers. This API enables developers to manage their game inventory, upload new game releases,
and retrieve essential information such as game details, pricing, and availability. Retailers can use the API to update
their product listings, track sales, and manage customer orders efficiently. Gamers benefit from features like searching
for games, viewing reviews, and making purchases securely. With support for standard RESTful endpoints, this API ensures
easy integration into various platforms, fostering a dynamic and interconnected gaming ecosystem.

## Authors

- [@Ivan Batiuk IH-01](https://github.com/ikjrjto)

## Tech Stack

**Server:** Java, Spring Boot 3, JWT

**Database:** MySQL

## Run Locally

Firstly, create database

```bash
    create database game_store;
```

Clone the project

```bash
    git clone https://github.com/ikjrjto/game-store-rest-api.git
```

Go to the project directory

```bash
    cd game-store-rest-api
```

Install dependencies

```bash
    ./mvnw clean install
```

Start the server

```bash
  ./mvnw spring-boot:run
```

## API Reference

### Auth

#### Login

```http
  POST /api/auth/login
  or
  POST /api/auth/signin
```

#### Register

```http
  POST /api/auth/register
  or
  POST /api/auth/signup
```

### Game

#### Create game object

```http
  POST /api/games
```

| Parameter | Type           | Description                     |
|:----------|:---------------|:--------------------------------|
| `gameDto` | `Request body` | **Required**. Game model object |

#### Get all games

```http
  GET /api/games
```

| Parameter  | Type      | Description                |
|:-----------|:----------|:---------------------------|
| `pageNo`   | `integer` | Page number                |
| `pageSize` | `integer` | Count of elements per page |
| `sortBy`   | `string`  | Sorting criteria           |
| `sortDir`  | `string`  | Sorting direction          |

#### Get specific game

```http
  GET /api/games/${gameId}
```

| Parameter | Type     | Description                       |
|:----------|:---------|:----------------------------------|
| `gameId`  | `string` | **Required**. Id of game to fetch |

#### Update specific game

```http
  PUT /api/games/${gameId}
```

| Parameter | Type           | Description                         |
|:----------|:---------------|:------------------------------------|
| `gameId`  | `string`       | **Required**. Id of game to update  |
| `gameDto` | `Request body` | **Required**. New game model object |

#### Delete specific game

```http
  DELETE /api/games/${gameId}
```

| Parameter | Type     | Description                        |
|:----------|:---------|:-----------------------------------|
| `gameId`  | `string` | **Required**. Id of game to delete |

### Game details

#### Create game details object

```http
  POST /api/games/${gameId}/details
```

| Parameter    | Type           | Description                             |
|:-------------|:---------------|:----------------------------------------|
| `gameId`     | `string`       | **Required**. Id of game to add details |
| `detailsDto` | `Request body` | **Required**. Details model object      |

#### Get all details for game

```http
  GET /api/games/${gameId}/details
```

| Parameter | Type     | Description                               |
|:----------|:---------|:------------------------------------------|
| `gameId`  | `string` | **Required**. Id of game to fetch details |

#### Get specific details for game

```http
  GET /api/games/${gameId}/details/${detailsId}
```

| Parameter   | Type     | Description                          |
|:------------|:---------|:-------------------------------------|
| `gameId`    | `string` | **Required**. Id of game to fetch    |
| `detailsId` | `string` | **Required**. Id of details to fetch |

#### Update specific details for game

```http
  PUT /api/games/${gameId}/details/${detailsId}
```

| Parameter    | Type           | Description                                |
|:-------------|:---------------|:-------------------------------------------|
| `gameId`     | `string`       | **Required**. Id of game to update details |
| `detailsId`  | `string`       | **Required**. Id of details to update      |
| `detailsDto` | `Request body` | **Required**. New details model object     |

#### Delete specific game

```http
  DELETE /api/games/${gameId}/details/${detailsId}
```

| Parameter   | Type     | Description                                |
|:------------|:---------|:-------------------------------------------|
| `gameId`    | `string` | **Required**. Id of game to delete details |
| `detailsId` | `string` | **Required**. Id of details to delete      |

