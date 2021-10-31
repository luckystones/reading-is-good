FROM mongo:4.0.3

#COPY book.json /book.json
#COPY customer.json /customer.json
#COPY order.json /order.json
#CMD mongoimport --host mongodb --db reading_is_good --authenticationDatabase admin --username simpleuser --password simplepass --collection customer --type json --file /customer.json --jsonArray
#CMD mongoimport --host mongodb --db reading_is_good --authenticationDatabase admin --username simpleuser --password simplepass --collection book --type json --file /book.json --jsonArray
#CMD mongoimport --host mongodb --db reading_is_good --authenticationDatabase admin --username simpleuser --password simplepass --collection order --type json --file /order.json --jsonArray
ENV MONGO_INITDB_ROOT_USERNAME root
ENV MONGO_INITDB_ROOT_PASSWORD root
ENV MONGO_INITDB_DATABASE admin

ADD ./init.js /docker-entrypoint-initdb.d/