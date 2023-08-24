# social-media-api

create a docker container using cmd that contains mysql so that the api runs without problems.
**To Launch MySQL as Docker Container:**
docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=social-media-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8-oracle

login credentials for api:
-u user
-p pass

use api clients to execute POST, DELETE requests (talend api tester) chrome extension.

