# RESTful Export Import API

## Overview

RESTful Export Import API provides endpoints for managing users and handling Excel file exports and imports.
It includes functionality for creating, retrieving, updating, and deleting user records (known as CRUD operations), as well as exporting and importing user data from/to Excel files.

## What's Used in this Project?

- Java 21
- Spring Boot 3.3.2
- Maven (for building)
- Docker (for dockerizing the project, and managing MySQL or PostgreSQL databases) 
- H2, PostgreSQL, MySQL (Database profiling)
- Log4J (for logging)
- Spring HATEOAS for API links
- I18N for internationalization with using `Bundle.properties` 

## User Management API

### Base URL

`/api/users`

### Endpoints

#### 1. Get All Users

- **Endpoint:** `GET /api/users`
- **Description:** Retrieve a list of all users.
- **Responses:**
  - `200 OK`: List of users successfully retrieved.
  - `500 Internal Server Error`: Error occurred while retrieving users.

**Example Request:**

```http
GET /api/users
```

**Example Response:**

```json
{
    "_embedded": {
        "userViewDTOList": [
            {
                "id": 120633,
                "name": "string",
                "surname": "string",
                "age": 61,
                "height": 273,
                "weight": 635.00,
                "birthdate": "1963-05-10",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "update": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "patch": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "delete": {
                        "href": "http://localhost:8080/api/users/120633"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users"
        },
        "export": {
            "href": "http://localhost:8080/api/export/users?lang="
        }
    }
}
```

#### 2. Get User by ID

- **Endpoint:** `GET /api/users/{id}`
- **Description:** Retrieve a particular user by their ID.
- **Responses:**
  - `200 OK`: User successfully retrieved.
  - `404 Not Found`: User not found.
  - `500 Internal Server Error`: Error occurred while retrieving the user.

**Example Request:**

```http
GET /api/users/120633
```

**Example Response:**

```json
{
    "id": 120633,
    "name": "string",
    "surname": "string",
    "age": 61,
    "height": 273,
    "weight": 635.00,
    "birthdate": "1963-05-10",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "update": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "patch": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "delete": {
            "href": "http://localhost:8080/api/users/120633"
        }
    }
}
```

#### 3. Create User

- **Endpoint:** `POST /api/users`
- **Description:** Create a new user with the provided details.
- **Request Body:** `UserCreateDTO`
- **Responses:**
  - `201 Created`: User successfully created.
  - `400 Bad Request`: Invalid input.
  - `500 Internal Server Error`: Error occurred while creating the user.

**Example Request:**

```http
POST /api/users
Content-Type: application/json

{
    "name": "string",
    "surname": "string",
    "age": 61,
    "height": 273,
    "weight": 635.00,
    "birthdate": "1963-05-10"
}
```

**Example Response:**

```json
{
    "id": 120633,
    "name": "string",
    "surname": "string",
    "age": 61,
    "height": 273,
    "weight": 635.00,
    "birthdate": "1963-05-10",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "update": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "patch": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "delete": {
            "href": "http://localhost:8080/api/users/120633"
        }
    }
}
```

#### 4. Create Multiple Users

- **Endpoint:** `POST /api/users/list`
- **Description:** Create multiple users from a given list of user details.
- **Request Body:** `List<UserCreateDTO>`
- **Responses:**
  - `201 Created`: Users successfully created.
  - `400 Bad Request`: Invalid input.
  - `500 Internal Server Error`: Error occurred while creating users.

**Example Request:**

```http
POST /api/users/list
Content-Type: application/json

[
    {
        "name": "string",
        "surname": "string",
        "age": 61,
        "height": 273,
        "weight": 635.00,
        "birthdate": "1963-05-10"
    }
]
```

**Example Response:**

```json
{
    "_embedded": {
        "userViewDTOList": [
            {
                "id": 120633,
                "name": "string",
                "surname": "string",
                "age": 61,
                "height": 273,
                "weight": 635.00,
                "birthdate": "1963-05-10",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "update": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "patch": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "delete": {
                        "href": "http://localhost:8080/api/users/120633"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users"
        }
    }
}
```

#### 5. Update User by ID

- **Endpoint:** `PUT /api/users/{id}`
- **Description:** Update details of a particular user by their ID.
- **Request Body:** `UserUpdateDTO`
- **Responses:**
  - `200 OK`: User successfully updated.
  - `400 Bad Request`: Invalid input.
  - `404 Not Found`: User not found.

**Example Request:**

```http
PUT /api/users/120633
Content-Type: application/json

{
    "name": "string",
    "surname": "string",
    "age": 61,
    "height": 273,
    "weight": 635.00,
    "birthdate": "1963-05-10"
}
```

**Example Response:**

```json
{
    "id": 120633,
    "name": "string",
    "surname": "string",
    "age": 61,
    "height": 273,
    "weight": 635.00,
    "birthdate": "1963-05-10",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "patch": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "delete": {
            "href": "http://localhost:8080/api/users/120633"
        }
    }
}
```

#### 6. Partially Update User by ID

- **Endpoint:** `PATCH /api/users/{id}`
- **Description:** Update specific details of a user by their ID.
- **Request Body:** `UserUpdateDTO`
- **Responses:**
  - `200 OK`: User successfully updated.
  - `400 Bad Request`: Invalid input.
  - `404 Not Found`: User not found.
  - `500 Internal Server Error`: Error occurred while updating the user.

**Example Request:**

```http
PATCH /api/users/120633
Content-Type: application/json

{
    "name": "string",
    "surname": "string"
}
```

**Example Response:**

```json
{
    "id": 120633,
    "name": "string",
    "surname": "string",
    "age": 61,
    "height": 273,
    "weight": 635.00,
    "birthdate": "1963-05-10",
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "update": {
            "href": "http://localhost:8080/api/users/120633"
        },
        "delete": {
            "href": "http://localhost:8080/api/users/120633"
        }
    }
}
```

#### 7. Delete User by ID

- **Endpoint:** `DELETE /api/users/{id}`
- **Description:** Delete a particular user by their ID.
- **Responses:**
  - `202 Accepted`: User successfully deleted.
  - `404 Not Found`: User not found.

**Example Request:**

```http
DELETE /api/users/120633
```

**Example Response:**

```json
{
    "message": "User deleted successfully"
}
```

#### 8. Get Users with Pagination

- **Endpoint:** `GET /api/users/slice`
- **Description:** Retrieve a paged list of users.
- **Request Parameters:**
  - `page` (int): Page number.
  - `size` (int): Number of users per page.
  - `sort` (optional, String): Sort property.
- **Responses:**
  - `200 OK`: List of users successfully retrieved.
  - `400 Bad Request`: Invalid parameters.
  - `404 Not Found`: Page not found or no content available.

**Example Request:**

```http
GET /api/users/slice?page=0&size=10&sort=name
```

**Example Response:**

```json
{
    "_embedded": {
        "userViewDTOList": [
            {
                "id": 120633,
                "name": "string",
                "surname": "string",
                "age": 61,
                "height": 273,
                "weight": 635.00,
                "birthdate": "1963-05-10",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "update": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "patch": {
                        "href": "http://localhost:8080/api/users/120633"
                    },
                    "delete": {
                        "href": "http://localhost:8080/api/users/120633"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/users/slice?page=0&size=10"
        },
        "allUsers": {
            "href": "http://localhost:8080/api/users"
        }
    }
}
```

## Excel Export/Import API

### Base URL

`/api/export`

### Endpoints

#### 1. Export Users to Excel

- **Endpoint:** `POST /api/export/users`
- **Description:** Export a list of users, retrieved from a database, to an Excel file.
- **Request Param:** `String lang`
- **Responses:**
  - `200 OK`: Excel file successfully generated and downloaded.
  - `404 Not Found`: Provided language not supported by this application... yet
  - `500 Internal Server Error`: Error occurred while exporting

 data.

**Example Request:**

```http
POST /api/export/users?lang=en
```

**Example Response:**

The response will be an Excel file with the data in tabular format.

#### 2. Import Users from Excel

- **Endpoint:** `POST /api/import/users`
- **Description:** Import users from an Excel file.
- **Request Parameters:**
  - `file` (MultipartFile): The Excel file to be imported.
- **Responses:**
  - `200 OK`: Users successfully imported.
  - `400 Bad Request`: Invalid file or format.
  - `415 Unsupported Media Type`: Unsupported media type. The uploaded file is not in a supported format.
  - `500 Internal Server Error`: Error occurred while importing data.

**Example Request:**

```http
POST /api/import/users
Content-Type: multipart/form-data

file: [Excel file]
```

**Example Response:**

```json
{
  "_embedded": {
    "userViewDTOList": [
      {
        "id": 121234,
        "name": "Mx. Jonathan Humphrey",
        "surname": "Brown Jr.",
        "age": 97,
        "height": 207,
        "weight": 125.2,
        "birthdate": "1926-12-14",
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/users/121234"
          },
          "update": {
            "href": "http://localhost:8080/api/users/121234"
          },
          "patch": {
            "href": "http://localhost:8080/api/users/121234"
          },
          "delete": {
            "href": "http://localhost:8080/api/users/121234"
          }
        }
      }]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/users"
    }
  }
}
```

## Localization (i18n)

The API supports internationalization for Excel export headers. Use `lang` parameter in requesting URL/URI to specify the desired language (default is set to English).

### Docker Configuration

The `docker-compose.yml` and `Dockerfile` files are already present and configured to run the `restful-export-import-api` service with either MySQL or PostgreSQL database.
This initial commit has active profile set to MySQL and commented out PostgreSQL to not create and run unnecessary container however you can change the active profile and
comment out unused image at any time for `application.properties` and `docker-compose.yml` files.

### Building and Running the Containers

1. **Build and Start Containers**

   Run the following command to build and start the containers:

   ```bash
   docker-compose up --build
   ```

   This command will build the Docker image for your application (if not already built) and start the services defined in the `docker-compose.yml` file.

2. **Stopping the Containers**

   To stop and remove the containers, run:

   ```bash
   docker-compose down
   ```

   This command will stop the containers and remove them along with the networks and volumes created.

3. **Viewing Logs**

   To view the logs of the running containers, use:

   ```bash
   docker-compose logs
   ```

   For logs of a specific service, use:

   ```bash
   docker-compose logs restful-export-import-api
   ```

4. **Customizing Docker Setup**

- **Switching Database Profiles**: The `SPRING_PROFILES_ACTIVE` environment variable is set to `mysql` in the Docker Compose file. Change this to `postgresql` if you want to use PostgreSQL. Uncomment and configure the PostgreSQL service in the `docker-compose.yml` file accordingly. You may have to change the profile from `application.properties` as well if you want to use IntelliJ's Persistence feature, and manage the database from here as well.
- **Data Persistence**: Volumes are defined for persisting data. Adjust volume configurations as needed based on your application requirements.
