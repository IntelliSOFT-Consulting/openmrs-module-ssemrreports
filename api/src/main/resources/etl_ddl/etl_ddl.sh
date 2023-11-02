#!/bin/bash

# Verify command line arguments or set defaults
if [ "$#" -lt 4 ]; then
    echo "Usage: $0 <host> <db_name> <username> <password>"
    echo "Using default values: host=localhost, db_name=ssemr_etl, username=root, password=root"
    host="localhost"
    db_name="ssemr_etl"
    username="root"
    password="root"
else
    host="$1"
    db_name="$2"
    username="$3"
    password="$4"
fi

# Check if the database exists; if not, create it
echo "Creating or using database: $db_name"
mysql -h "$host" -u "$username" -p"$password" -e "CREATE DATABASE IF NOT EXISTS $db_name;"

# Find and execute all .sql files in current and subdirectories
echo "Executing SQL files in $db_name database"
find . -type f -name "*.sql" | while read -r file; do
    echo "Executing $file"
    mysql -h "$host" -u "$username" -p"$password" "$db_name" < "$file"
done
