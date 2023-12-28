import psycopg2
from configparser import ConfigParser
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy import create_engine, Column, String, Float, Enum
from sqlalchemy.orm import Session

Base = declarative_base()
class DB_proxy():
    def __init__(self):
        print("DB Proxy Init")

    def get_transactions(self):
        # Remplacez les valeurs de connexion par les vôtres
        conn = psycopg2.connect(
            dbname="marketing-db",
            user="root",
            password="root",
            host="marketing-db-slave",
            port="5432"
        )

        cur = conn.cursor()
        cur.execute("SELECT * FROM transaction;")
        rows = cur.fetchall()

        #for row in rows:
        #    print(row)

        cur.close()
        conn.close()

        return rows