# Application for creating an updated copy of a local NIST (https://nvd.nist.gov/developers/products) database with an API for accessing the this database

# Installation instructions
For Unix systems
* Install docker-compose: apt update && apt install docker-compose
* Create a folder 'demo' in the root directory
* Download the docker-compose.yml and demo.sql file from src/main/resources into the 'demo' folder
* In the docker-compose.yml file change the image 'ubuntu:20.04' to a docker image for your OS
* While in the 'demo' folder, run the command 'docker-compose up -d'

# API requests
After installing the application you can use GET requests to local DB
* Request for one record by UUID
  > `http://<your-ip-address>:8080/api/v1/uuid/?uuid=<UUID>`

  Example:    
  > `http://localhost:8080/api/v1/uuid/?uuid=12eddf29-b258-4834-a405-dd73cc071a0a`

* Request a list of records by a list of UUID

  > `http://<your-ip-address>:8080/api/v1/uuids/?uuids=<UUID_1>,<UUID_2>,...,<UUID_n>`

  Example:
  > `http://localhost:8080/api/v1/uuid/?uuid=12eddf29-b258-4834-a405-dd73cc071a0a,10eddf20-b258-4834-a405-dd73cc071a0a`

* Request a list of records by a list of names

  > `http://<your-ip-address>:8080/api/v1/uuids/?uuids=<cpeName_1>,<cpeName_2>,...,<cpeName_n>`

  Example:
  > `http://localhost:8080/api/v1/uuid/?uuid=cpe:2.3:a:ibm:lotus_domino:-:*:*:*:*:*:*:*,cpe:2.3:h:3com:3cp4144:-:*:*:*:*:*:*:*`

* Query N records with pagination and filtering by name (partial match)
  > `http://<your-ip-address>:8080/api/v1/partial/?cpeName=<your-search-term>&page=<page-number>&pageSize=<page-size>`

  Example:
  > `http://localhost:8080/api/v1/partial/?cpeName=3com&page=0&pageSize=10`

# Local database 
The local database is updated automatically every day for the period from 12:00 AM of the previous day to 12:00 AM of the current day
