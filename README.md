# indexer
JAVA based blockchain txn indexer


Introduction:
It's a basic ETL program where we are requesting data from a blockchain node and then store it in a database. Making it more accessible to use. 

This is an event based system
The event module enables us to listen to specific events. We have an Ethereum publisher, to which we can subscribe to a topic. All sorts of events get broadcasted to the network by the publisher. 
As a subscriber, we can choose which topic to subscribe to. 

Steps to run the code:
1. To get access to a test Sepolia Network, an ethereum node is required, as we cannot mock actual data at scale, hence this adaptor was used. You will need a project id to access this node.
2. Once the node is setup. Use the node url to start your subscription to listen to events.
3. PostgresDB is being used, please setup a local Postgres and provide the same in the application.properties file.
3. You need to setup AWS SQS, there are 2 queues in action in the above mode. Please see to the producer and consumer class to get details of the queues.
Once the queue is setup.
4. Run the application and use the endpoints to start indexing transactions and blocks.


