CREATE KEYSPACE logsdb WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1': 2};
use logsdb;
CREATE TABLE IF NOT EXISTS transaction (
    id BIGINT PRIMARY KEY,
    name TEXT,
    client BIGINT,
    price FLOAT,
    country TEXT,
    type TEXT,
    origine TEXT,
    tagetclient BIGINT
);
