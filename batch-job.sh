#!/bin/bash

# List of URLs to execute
urls=("http://localhost:8000/marketing/analysis" "http://localhost:8083/stats/start-calculations")

# Function to execute HTTP requests
execute_requests() {
  url="$1"
  if curl -s -o /dev/null --head --fail "$url"; then
    echo "Request to $url was successful"
  else
    echo "Request to $url failed"
  fi
}

# Iterate through the list of URLs and execute the requests
for url in "${urls[@]}"; do
  execute_requests "$url"
done
