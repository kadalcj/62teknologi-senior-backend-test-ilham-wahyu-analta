# 62 Backend Developer Test - Spring Boot
### Run with Docker
To run this project, run this command:
```bash
# Create Docker Image
$ sudo docker build -t sbe-springboot:latest .

# Run Docker Image
$ sudo docker-compose up
```

### Play with the Apps
This app will run on:
```bash
http://localhost:8080/
```
And I've already create Postman Collection for this app. 
The file was inside `Postman` folder. All routes ready with Templates, make it easier for testing. Please import it.
#### Routes
```bash
# Get All Business
@GET
http://localhost:8080/api/v1/business

# Create Business
@POST
http://localhost:8080/api/v1/business

# Update Business
@PUT
http://localhost:8080/api/v1/business/{id}

# Delete Business
@DELETE
http://localhost:8080/api/v1/business/{id}

# Search/Filter Business
@GET
http://localhost:8080/api/v1/business/search

# Batch Create / Populate Business 50 Data (Copied from API)
@POST
http://localhost:8080/api/v1/business/batch
```